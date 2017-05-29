package gamingplatform.controller;

import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.implementation.GameDaoImpl;
import gamingplatform.dao.implementation.ReviewDaoImpl;
import gamingplatform.dao.implementation.UserDaoImpl;
import gamingplatform.dao.implementation.UserGameDaoImpl;
import gamingplatform.dao.interfaces.GameDao;
import gamingplatform.dao.interfaces.ReviewDao;
import gamingplatform.dao.interfaces.UserDao;
import gamingplatform.dao.interfaces.UserGameDao;
import gamingplatform.model.Review;
import gamingplatform.model.User;
import gamingplatform.model.UserGame;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static gamingplatform.controller.utils.SecurityLayer.checkAuth;
import static gamingplatform.controller.utils.SecurityLayer.redirect;
import static gamingplatform.controller.utils.SessionManager.*;
import static gamingplatform.controller.utils.Utils.checkLevel;
import static gamingplatform.controller.utils.Utils.getLastBitFromUrl;
import static gamingplatform.controller.utils.Utils.checkLevel;
import static gamingplatform.view.FreemarkerHelper.process;
import static java.util.Objects.*;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNumeric;


public class Game extends HttpServlet {

    @Resource(name = "jdbc/gamingplatform")
    private static DataSource ds;

    //container dati che sarà processato da freemarker
    private Map<String, Object> data = new HashMap<>();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //carico l'user nella Map prelevandolo dalla sessione se verificata
        data.put("user", getUser(request));

        //carico i servizi
        data.put("services", getServices(request));

        //se l'ultimo elemento dopo "/" non è numerico oppure se l'utente non è loggato
        if(verifySession(request)==null){
            Logger.getAnonymousLogger().log(java.util.logging.Level.WARNING,"[Game] user non loggato");
            redirect("/", "KO-not-logged", response, request);
            return;
        }

        String id= getLastBitFromUrl(request.getRequestURI());

        if(isNull(id) || id.equals("") || !isNumeric(id)){
            Logger.getAnonymousLogger().log(Level.WARNING,"[Game] gameId non valido.");
            redirect("/", "KO", response, request);
            return;
        }

        int gameId=Integer.parseInt(id);

        try{
            //recupero dato sul gioco
            GameDao gameDao=new GameDaoImpl(ds);
            gameDao.init();
            gamingplatform.model.Game game = gameDao.getGameById(gameId);
            data.put("game",game);

            //recupero recensioni relative a quel gioco
            ReviewDao reviewDao=new ReviewDaoImpl(ds);
            reviewDao.init();
            List<Review> reviews = reviewDao.getReviewsByGame(gameId);

            data.put("reviews", reviews);

            UserDao userDao = new UserDaoImpl(ds);
            userDao.init();
            List<User> users = new ArrayList<>(reviews.size());
            List<gamingplatform.model.Level> levels = new ArrayList<>(reviews.size());

            //genero una lista di utenti dove utente[i] è l'autore della review i
            //e una lista di livelli dove livello[i] corrisponde a utente[i]
            //posso usare il foreach in quanto negli arraylist l'ordine è mantenuto
            for(Review r : reviews){

                int tempId=r.getIdUser();

                User tempUser = userDao.getUser(tempId);

                gamingplatform.model.Level tempLevel = userDao.getLevelByUserId(tempId);
                users.add(tempUser);
                levels.add(tempLevel);
            }


            data.put("users",users);
            data.put("levels",levels);

            double average = gameDao.getAverageVote(game);
            data.put("average", average);

            reviewDao.destroy();
            userDao.destroy();
            gameDao.destroy();



        }catch (DaoException e){
            Logger.getAnonymousLogger().log(Level.WARNING, "[Game] DaoException: "+e.getMessage());
            redirect("/", "KO", response, request);
            return;
        }

        //processo template
        process("game.ftl", data, response, getServletContext());
    }

    // Metodo doPost
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        //se non è una chiamata ajax
        if (!"XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            Logger.getAnonymousLogger().log(java.util.logging.Level.WARNING, "[doInsert] non è una chiamata ajax");
            //torno KO alla chiamata servlet
            response.getWriter().write("KO");
            return;
        }

        String id= getLastBitFromUrl(request.getRequestURI());

        if(isNull(id) || id.equals("") || !isNumeric(id)){
            Logger.getAnonymousLogger().log(Level.WARNING,"[Game] gameId non valido.");
            response.getWriter().write("KO");
            return;
        }
        // Verifica la sessione
        HttpSession session = verifySession(request);

        if(isNull(session)){
            Logger.getAnonymousLogger().log(Level.WARNING,"[Game] Sessione non valida." );
            response.getWriter().write("KO");
            return;
        }
        User user = (User)session.getAttribute("user");
        String responseStr ="OK";

        try{
            //recuper l'exp data da gioco
            GameDao gameDao = new GameDaoImpl(ds);
            gameDao.init();
            gamingplatform.model.Game game = gameDao.getGameById(Integer.parseInt(id));
            gameDao.destroy();

            int exp = game.getExp();

            //aggiungo exp all'user
            user.setExp(user.getExp()+exp);

            UserDao userDao = new UserDaoImpl(ds);
            userDao.init();
            userDao.updateUser(user);
            userDao.destroy();

            //aggiorno il livello
            int esit = checkLevel(user);


            //controllo esito aggiornamento livello
            switch(esit){
                case 0:
                    responseStr+="_0";
                    break;
                case 1:
                    responseStr+="_1";
                    break;
                case -1:
                    responseStr+="_-1";
                    break;
                default:
                    responseStr="KO";
                    break;
            }

            UserGameDao userGameDao = new UserGameDaoImpl(ds);
            userGameDao.init();

            UserGame userGame = userGameDao.getUserGame();
            userGame.setUserId(user.getId());
            userGame.setGameId(Integer.parseInt(id));
            userGame.setDate(new Timestamp(System.currentTimeMillis()));
            userGameDao.insertUserGame(userGame);

            userGameDao.destroy();
        }catch(Exception e){
            Logger.getAnonymousLogger().log(Level.WARNING,"[Game] Eccezione " +e.getMessage());
            response.getWriter().write("KO");
        }

        //torno la risposta
        response.getWriter().write(responseStr);

    }


}