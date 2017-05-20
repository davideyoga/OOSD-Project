package gamingplatform.controller;

import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.implementation.*;
import gamingplatform.dao.interfaces.*;
import gamingplatform.model.Game;
import gamingplatform.model.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.logging.Logger;

import static gamingplatform.controller.utils.SecurityLayer.checkAuth;
import static gamingplatform.controller.utils.SecurityLayer.sha1Encrypt;
import static gamingplatform.controller.utils.Utils.fileUpload;
import static gamingplatform.controller.utils.Utils.getLastBitFromUrl;
import static java.util.Objects.isNull;


/**
 * classe atta al processamento di richieste ajax post per l'inserimento di elementi nel db
 * risponde a url del tipo /doInser/tabella/
 */
@MultipartConfig(
        fileSizeThreshold=1024*1024,    // 1 MB
        maxFileSize=1024*1024*5,        // 5 MB
        maxRequestSize=1024*1024*5*5    // 25 MB
)
public class doInsert extends HttpServlet {

    @Resource(name = "jdbc/gamingplatform")
    private static DataSource ds;


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        //se non è una chiamata ajax
        if (!"XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            Logger.getAnonymousLogger().log(java.util.logging.Level.WARNING, "[doInsert] non è una chiamata ajax");
            //torno KO alla chiamata servlet
            response.getWriter().write("KO");
            return;
        }

        //carico la tabella in cui si vuole aggiungere la tupla (la url è della forma /doInsert/tabella
        String item = getLastBitFromUrl(request.getRequestURI());

        //controllo quì se l'utente è loggato e ha acesso a quella determinata tabella
        if (!checkAuth(request, item)) {
            //se l'ultimo elemento dopo lo "/" (ovvero il servizio a cui si sta provando ad accedere)
            //non è un servizio a cui l'utente ha accesso
            response.getWriter().write("KO");
            return;
        }

        try {

            switch (item) {

                //caso inserimento user
                case "user":
                    //prelevo parametri POST per l'user
                    String usernameUser = request.getParameter("username");
                    String nameUser = request.getParameter("name");
                    String surnameUser = request.getParameter("surname");
                    String emailUser = request.getParameter("email");
                    String passwordUser = sha1Encrypt(request.getParameter("password"));
                    Part avatarUser = request.getPart("avatar"); // recupera <input type="file" name="avatar">

                    //se i parametri in input non sono validi
                    if (isNull(usernameUser) || isNull(nameUser) || isNull(surnameUser) || isNull(emailUser) || isNull(passwordUser) ||
                            usernameUser.equals("") || nameUser.equals("") || surnameUser.equals("") || emailUser.equals("") || passwordUser.equals("")) {

                        Logger.getAnonymousLogger().log(java.util.logging.Level.WARNING, "[doInsert: "+item+"] Parametri POST non validi ");
                        //torno KO alla chiamata servlet
                        response.getWriter().write("KO");
                        return;
                    }
                    //provo ad effettuare l'upload del file
                    String avatarName = fileUpload(avatarUser, "avatars", getServletContext());
                    if (isNull(avatarName)) {

                        Logger.getAnonymousLogger().log(java.util.logging.Level.WARNING, "[doInsert: "+item+"] Upload file fallito");
                        //torno KO alla chiamata servlet
                        response.getWriter().write("KO");
                        return;
                    }

                    UserDao userDao = new UserDaoImpl(ds);

                    userDao.init();
                    //provo ad inserire l'utente

                    User user = userDao.getUser();
                    user.setUsername(usernameUser);
                    user.setName(nameUser);
                    user.setSurname(surnameUser);
                    user.setEmail(emailUser);
                    user.setPassword(passwordUser);
                    user.setExp(0);
                    user.setAvatar(avatarName);

                    userDao.insertUser(user);

                    userDao.destroy();

                    //TODO nell'iserimento dell'utente (da fare anche in signup) bisogna aggiungere na tupla dentro userlevel che indica che al momento della registrazione l'utente è al livello 0

                    break;

                case"game":

                    //prelevo parametri POST per game
                    String nameGame = request.getParameter("name");
                    int expGame = Integer.parseInt(request.getParameter("exp"));
                    String imageGame = request.getParameter("image");
                    String descriptionGame = request.getParameter("description");

                    //se i parametri in input non sono validi
                    if (isNull(nameGame) || isNull(expGame) || isNull(imageGame) ||
                            nameGame.equals("") || imageGame.equals("") || descriptionGame.equals("")) {

                        Logger.getAnonymousLogger().log(java.util.logging.Level.WARNING, "[doInsert: "+item+"] Parametri POST non validi "); // per stampare errori sul terminale al posto di System.Out
                        //torno KO alla chiamata servlet
                        response.getWriter().write("KO");
                        return;
                    }

                    GameDao gameDao = new GameDaoImpl(ds);

                    gameDao.init();
                    //provo ad inserire il gioco

                    Game game = gameDao.getGame();

                    game.setName(nameGame);
                    game.setExp(expGame);
                    game.setImage(imageGame);
                    game.setDescription(descriptionGame);

                    //inserisco game nel db
                    gameDao.insertGame(game);

                    //chiudo gameDao
                    gameDao.destroy();

                    break;

                case"usergame":

                    //prelevo parametri POST per game
                    int id_userUsergame = Integer.parseInt(request.getParameter("id_user"));
                    int id_gameUsergame = Integer.parseInt(request.getParameter("id_game"));
                    Timestamp dateUserGame = Timestamp.valueOf(request.getParameter("date"));

                    //se i parametri in input non sono validi
                    if (isNull(id_gameUsergame) || isNull(id_userUsergame) || isNull(dateUserGame) ||
                            dateUserGame.equals("")) {

                        Logger.getAnonymousLogger().log(java.util.logging.Level.WARNING, "[doInsert: "+item+"] Parametri POST non validi "); // per stampare errori sul terminale al posto di System.Out
                        //torno KO alla chiamata servlet
                        response.getWriter().write("KO");
                        return;
                    }

                    UserGameDao userGameDao = new UserGameDaoImpl(ds);

                    userGameDao.init();
                    //provo ad inserire il gioco

                    UserGame userGame = userGameDao.getUserGame();

                    userGame.setUserId(id_userUsergame);
                    userGame.setGameId(id_gameUsergame);
                    userGame.setDate(dateUserGame);

                    //inserisco game nel db
                    userGameDao.insertUserGame(userGame);
                    userGame = null;

                    //chiudo gameDao
                    userGameDao.destroy();
                    userGameDao = null;

                    break;

                case"review":

                    //prelevo parametri POST per game
                    int id_userReview = Integer.parseInt(request.getParameter("id_user"));
                    int id_gameReview = Integer.parseInt(request.getParameter("id_game"));
                    String titleReview = request.getParameter("title");
                    String bodyReview = request.getParameter("body");
                    int votereview = Integer.parseInt(request.getParameter("vote"));

                    //se i parametri in input non sono validi
                    if (isNull(id_userReview) || isNull(id_gameReview) || isNull(titleReview) || isNull(bodyReview) || isNull(votereview)||
                            titleReview.equals("") || bodyReview.equals("")) {

                        Logger.getAnonymousLogger().log(java.util.logging.Level.WARNING, "[doInsert: "+item+"] Parametri POST non validi "); // per stampare errori sul terminale al posto di System.Out
                        //torno KO alla chiamata servlet
                        response.getWriter().write("KO");
                        return;
                    }

                    ReviewDao reviewDao = new ReviewDaoImpl(ds);

                    reviewDao.init();

                    //provo ad inserire il gioco

                    Review review = reviewDao.getReview();

                    review.setIdUser(id_userReview);
                    review.setIdGame(id_gameReview);
                    review.setTitle(titleReview);
                    review.setBody(bodyReview);
                    review.setVote(votereview);

                    //inserisco game nel db
                    reviewDao.insertReview(review);
                    review = null;

                    //chiudo gameDao
                    reviewDao.destroy();
                    reviewDao = null;

                    break;

                case"level":

                    //prelevo parametri POST per game
                    int nameLevel = Integer.parseInt(request.getParameter("name"));
                    String trophyLevel = request.getParameter("trophy");
                    String iconLevel = request.getParameter("icon");
                    int expLevel =  Integer.parseInt(request.getParameter("exp"));

                    //se i parametri in input non sono validi
                    if (isNull(nameLevel) || isNull(trophyLevel) || isNull(iconLevel) || isNull(expLevel) ||
                            trophyLevel.equals("") || iconLevel.equals("")) {

                        Logger.getAnonymousLogger().log(java.util.logging.Level.WARNING, "[doInsert: "+item+"] Parametri POST non validi "); // per stampare errori sul terminale al posto di System.Out
                        //torno KO alla chiamata servlet
                        response.getWriter().write("KO");
                        return;
                    }

                    LevelDao levelDao = new LevelDaoImpl(ds);

                    levelDao.init();

                    gamingplatform.model.Level level = levelDao.getLevel();

                    level.setName(nameLevel);
                    level.setTrophy(trophyLevel);
                    level.setIcon(iconLevel);
                    level.setExp(expLevel);

                    //inserisco game nel db
                    levelDao.insertLevel(level);
                    level = null;

                    //chiudo gameDao
                    levelDao.destroy();
                    levelDao = null;

                    break;

                case"groups":

                    //prelevo parametri POST per game
                    String nameGroups = request.getParameter("name");
                    String descriptionGroups = request.getParameter("description");


                    //se i parametri in input non sono validi
                    if (isNull(nameGroups) || isNull(descriptionGroups) ||
                            nameGroups.equals("") || descriptionGroups.equals("")) {

                        Logger.getAnonymousLogger().log(java.util.logging.Level.WARNING, "[doInsert: "+item+"] Parametri POST non validi "); // per stampare errori sul terminale al posto di System.Out
                        //torno KO alla chiamata servlet
                        response.getWriter().write("KO");
                        return;
                    }

                    GroupsDao groupsDao = new GroupsDaoImpl(ds);

                    groupsDao.init();

                    Group groups= groupsDao.getGroup();

                    groups.setName(nameGroups);
                    groups.setDescription(descriptionGroups);

                    //inserisco game nel db
                    groupsDao.insertGroup(groups);
                    groups = null;

                    //chiudo gameDao
                    groupsDao.destroy();
                    groupsDao = null;

                    break;

                case"service":

                    //prelevo parametri POST per game
                    String nameService = request.getParameter("name");
                    String descriptionService = request.getParameter("description");


                    //se i parametri in input non sono validi
                    if (isNull(nameService) || isNull(descriptionService) ||
                            nameService.equals("") || descriptionService.equals("")) {

                        Logger.getAnonymousLogger().log(java.util.logging.Level.WARNING, "[doInsert: "+item+"] Parametri POST non validi "); // per stampare errori sul terminale al posto di System.Out
                        //torno KO alla chiamata servlet
                        response.getWriter().write("KO");
                        return;
                    }

                    ServiceDao serviceDao = new ServiceDaoImpl(ds);

                    serviceDao.init();

                    Service service= serviceDao.getService();

                    service.setName(nameService);
                    service.setDescription(descriptionService);

                    //inserisco game nel db
                    serviceDao.insertService(service);
                    service = null;

                    //chiudo gameDao
                    serviceDao.destroy();
                    serviceDao = null;

                    break;

                    //altri casi

                //default
                default:

                    Logger.getAnonymousLogger().log(java.util.logging.Level.WARNING, "[doInsert: "+item+"] caso default nello switch");
                    //torno KO alla chiamata servlet
                    response.getWriter().write("KO");
                    return;
            }


        } catch (DaoException e) {
            Logger.getAnonymousLogger().log(java.util.logging.Level.WARNING, "doInsert: "+item+"]" + e.getMessage());
            //torno KO alla chiamata servlet
            response.getWriter().write("KO");
            return;
        }

        //torno OK alla chiamata servlet se arrivo alla fine senza problemi
        response.getWriter().write("OK");

    }
}