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
import static gamingplatform.controller.utils.SessionManager.verifySession;
import static gamingplatform.controller.utils.Utils.fileUpload;
import static gamingplatform.controller.utils.Utils.getLastBitFromUrl;
import static java.util.Objects.isNull;


/**
 * classe atta al processamento di richieste ajax post per l'inserimento di elementi nel db
 * risponde a url del tipo /doInser/tabella/
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,    // 1 MB
        maxFileSize = 1024 * 1024 * 5,        // 5 MB
        maxRequestSize = 1024 * 1024 * 5 * 5    // 25 MB
)
public class doInsert extends HttpServlet {

    @Resource(name = "jdbc/gamingplatform")
    private static DataSource ds;


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");


        //se non è una chiamata ajax
        if (!"XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            Logger.getAnonymousLogger().log(java.util.logging.Level.WARNING, "[doInsert] non è una chiamata ajax");
            //torno KO alla chiamata servlet
            response.getWriter().write("KO");
            return;
        }

        //carico la tabella in cui si vuole aggiungere la tupla (la url è della forma /doInsert/tabella
        String item = getLastBitFromUrl(request.getRequestURI());

        //se devo aggiungere una review non controllo autorizzazioni,
        //se l'utente chiama in qualche modo questa servlet tramite ajax non essendo
        //registrato verrà lanciata eccezione nel caso review dello switch
        if (!item.equals("review")) {

            //controllo quì se l'utente è loggato e ha acesso a quella determinata tabella
            if (!checkAuth(request, item)) {
                //se l'ultimo elemento dopo lo "/" (ovvero il servizio a cui si sta provando ad accedere)
                //non è un servizio a cui l'utente ha accesso
                response.getWriter().write("KO");
                return;
            }
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

                        Logger.getAnonymousLogger().log(java.util.logging.Level.WARNING, "[doInsert: " + item + "] Parametri POST non validi ");
                        //torno KO alla chiamata servlet
                        response.getWriter().write("KO");
                        return;
                    }
                    //provo ad effettuare l'upload del file
                    String avatarName = fileUpload(avatarUser, "avatars", getServletContext());
                    if (isNull(avatarName)) {

                        Logger.getAnonymousLogger().log(java.util.logging.Level.WARNING, "[doInsert: " + item + "] Upload file fallito");
                        //torno KO alla chiamata servlet
                        response.getWriter().write("KO");
                        return;
                    }

                    UserDao userDao = new UserDaoImpl(ds);
                    UserLevelDao userLevelDao1 = new UserLevelDaoImpl(ds);

                    userDao.init();
                    userLevelDao1.init();
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

                    //metto l'user al livello 0
                    UserLevel userLevel1 = userLevelDao1.getUserLevel();
                    userLevel1.setDate(new Timestamp(System.currentTimeMillis()));
                    userLevel1.setLevelId(0);
                    userLevel1.setUserId(userDao.getUserByUsernamePassword(usernameUser, passwordUser).getId());
                    userLevelDao1.insertUserlevel(userLevel1);

                    userDao.destroy();
                    userLevelDao1.destroy();

                    break;

                case "game":

                    //prelevo parametri POST per game
                    String nameGame = request.getParameter("name");
                    int expGame = Integer.parseInt(request.getParameter("exp"));
                    Part imageGame = request.getPart("image");
                    String descriptionGame = request.getParameter("description");

                    //se i parametri in input non sono validi
                    if (isNull(nameGame) || isNull(imageGame) || nameGame.equals("") || descriptionGame.equals("")) {

                        Logger.getAnonymousLogger().log(java.util.logging.Level.WARNING, "[doInsert: " + item + "] Parametri POST non validi "); // per stampare errori sul terminale al posto di System.Out
                        //torno KO alla chiamata servlet
                        response.getWriter().write("KO");
                        return;
                    }

                    String imageName = fileUpload(imageGame,"images",getServletContext());

                    GameDao gameDao = new GameDaoImpl(ds);

                    gameDao.init();
                    //provo ad inserire il gioco

                    Game game = gameDao.getGame();

                    game.setName(nameGame);
                    game.setExp(expGame);
                    game.setImage(imageName);
                    game.setDescription(descriptionGame);

                    //inserisco game nel db
                    gameDao.insertGame(game);

                    //chiudo gameDao
                    gameDao.destroy();

                    break;

                case "usergame":

                    //prelevo parametri POST per game
                    int id_userUsergame = Integer.parseInt(request.getParameter("id_user"));
                    int id_gameUsergame = Integer.parseInt(request.getParameter("id_game"));
                    Timestamp dateUserGame = Timestamp.valueOf(request.getParameter("date"));

                    //se i parametri in input non sono validi
                    if (dateUserGame.equals("")) {

                        Logger.getAnonymousLogger().log(java.util.logging.Level.WARNING, "[doInsert: " + item + "] Parametri POST non validi "); // per stampare errori sul terminale al posto di System.Out
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

                    //chiudo gameDao
                    userGameDao.destroy();

                    break;

                case "review":

                    //prelevo parametri POST per game
                    int id_gameReview = Integer.parseInt(request.getParameter("gameId"));
                    int id_userReview = ((User) verifySession(request).getAttribute("user")).getId();
                    String titleReview = request.getParameter("title");
                    String bodyReview = request.getParameter("body");
                    System.out.println(bodyReview);
                    int votereview = Integer.parseInt(request.getParameter("vote"));

                    //se i parametri in input non sono validi
                    if (isNull(bodyReview) || bodyReview.equals("")) {

                        Logger.getAnonymousLogger().log(java.util.logging.Level.WARNING, "[doInsert: " + item + "] Parametri POST non validi "); // per stampare errori sul terminale al posto di System.Out
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

                    //chiudo gameDao
                    reviewDao.destroy();

                    break;

                case "level":

                    //prelevo parametri POST per game
                    int nameLevel = Integer.parseInt(request.getParameter("name"));
                    String trophyLevel = request.getParameter("trophy");
                    Part iconLevel = request.getPart("icon");
                    int expLevel = Integer.parseInt(request.getParameter("exp"));

                    //se i parametri in input non sono validi
                    if (isNull(trophyLevel) || isNull(iconLevel) || trophyLevel.equals("") || iconLevel.equals("")) {

                        Logger.getAnonymousLogger().log(java.util.logging.Level.WARNING, "[doInsert: " + item + "] Parametri POST non validi "); // per stampare errori sul terminale al posto di System.Out
                        //torno KO alla chiamata servlet
                        response.getWriter().write("KO");
                        return;
                    }

                    String iconName=fileUpload(iconLevel,"images", getServletContext());

                    LevelDao levelDao = new LevelDaoImpl(ds);

                    levelDao.init();

                    gamingplatform.model.Level level = levelDao.getLevel();

                    level.setName(nameLevel);
                    level.setTrophy(trophyLevel);
                    level.setIcon(iconName);
                    level.setExp(expLevel);

                    //inserisco game nel db
                    levelDao.insertLevel(level);

                    //chiudo gameDao
                    levelDao.destroy();

                    break;

                case "groups":

                    //prelevo parametri POST per game
                    String nameGroups = request.getParameter("name");
                    String descriptionGroups = request.getParameter("description");


                    //se i parametri in input non sono validi
                    if (isNull(nameGroups) || isNull(descriptionGroups) ||
                            nameGroups.equals("") || descriptionGroups.equals("")) {

                        Logger.getAnonymousLogger().log(java.util.logging.Level.WARNING, "[doInsert: " + item + "] Parametri POST non validi "); // per stampare errori sul terminale al posto di System.Out
                        //torno KO alla chiamata servlet
                        response.getWriter().write("KO");
                        return;
                    }

                    GroupsDao groupsDao = new GroupsDaoImpl(ds);

                    groupsDao.init();

                    Group groups = groupsDao.getGroup();

                    groups.setName(nameGroups);
                    groups.setDescription(descriptionGroups);

                    //inserisco game nel db
                    groupsDao.insertGroup(groups);
                    groups = null;

                    //chiudo gameDao
                    groupsDao.destroy();
                    groupsDao = null;

                    break;

                case "service":

                    //prelevo parametri POST per game
                    String nameService = request.getParameter("name");
                    String descriptionService = request.getParameter("description");


                    //se i parametri in input non sono validi
                    if (isNull(nameService) || isNull(descriptionService) ||
                            nameService.equals("") || descriptionService.equals("")) {

                        Logger.getAnonymousLogger().log(java.util.logging.Level.WARNING, "[doInsert: " + item + "] Parametri POST non validi "); // per stampare errori sul terminale al posto di System.Out
                        //torno KO alla chiamata servlet
                        response.getWriter().write("KO");
                        return;
                    }

                    ServiceDao serviceDao = new ServiceDaoImpl(ds);

                    serviceDao.init();

                    Service service = serviceDao.getService();

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

                    Logger.getAnonymousLogger().log(java.util.logging.Level.WARNING, "[doInsert: " + item + "] caso default nello switch");
                    //torno KO alla chiamata servlet
                    response.getWriter().write("KO");
                    return;
            }


        } catch (Exception e) {
            Logger.getAnonymousLogger().log(java.util.logging.Level.WARNING, "doInsert: " + item + "]" + e.getMessage());
            //torno KO alla chiamata servlet
            response.getWriter().write("KO");
            return;
        }

        //torno OK alla chiamata servlet se arrivo alla fine senza problemi
        response.getWriter().write("OK");

    }
}