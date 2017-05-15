package gamingplatform.controller;

import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.implementation.GameDaoImpl;
import gamingplatform.dao.implementation.ReviewDaoImpl;
import gamingplatform.dao.implementation.UserDaoImpl;
import gamingplatform.dao.interfaces.GameDao;
import gamingplatform.dao.interfaces.ReviewDao;
import gamingplatform.dao.interfaces.UserDao;
import gamingplatform.model.Review;
import gamingplatform.model.User;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static gamingplatform.controller.utils.SecurityLayer.redirect;
import static gamingplatform.controller.utils.SessionManager.*;
import static gamingplatform.controller.utils.Utils.getLastBitFromUrl;
import static gamingplatform.view.FreemarkerHelper.process;
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
        if(isNull(verifySession(request))){
            Logger.getAnonymousLogger().log(Level.WARNING,"[Game] user non loggato");
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
            gameDao.destroy();

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
                int tempId=r.getIdGame();
                User tempUser = userDao.getUser(tempId);
                gamingplatform.model.Level tempLevel = userDao.getLevelByUserId(tempId);
                users.add(tempUser);
                levels.add(tempLevel);
            }

            userDao.destroy();

            data.put("users",users);
            data.put("levels",levels);


            //int average = reviewDao.getAverageByGameId(gameId);
            double average = 3.33;
            data.put("average", average);
            reviewDao.destroy();



        }catch (DaoException e){
            Logger.getAnonymousLogger().log(Level.WARNING, "[Game] DaoException: "+e.getMessage());
            redirect("/", "KO", response, request);
            return;
        }

        //processo template
        process("game.ftl", data, response, getServletContext());
    }

}