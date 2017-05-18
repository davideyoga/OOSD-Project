package gamingplatform.controller;

import gamingplatform.controller.utils.SessionManager;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.implementation.*;
import gamingplatform.dao.interfaces.*;
import gamingplatform.model.User;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static gamingplatform.controller.utils.SecurityLayer.checkAuth;
import static gamingplatform.controller.utils.SecurityLayer.sha1Encrypt;
import static gamingplatform.controller.utils.SessionManager.getUser;
import static gamingplatform.controller.utils.Utils.fileUpload;
import static gamingplatform.controller.utils.Utils.getLastBitFromUrl;
import static gamingplatform.controller.utils.Utils.getNlastBitFromUrl;
import static java.util.Objects.isNull;


/**
 * classe atta al processamento di richieste ajax post per la modifica di elementi nel db
 * risponde a url del tipo /doUpdate/tabella/idElemento
 */
@MultipartConfig(
        fileSizeThreshold=1024*1024,    // 1 MB
        maxFileSize=1024*1024*5,        // 5 MB
        maxRequestSize=1024*1024*5*5    // 25 MB
)
public class doUpdate extends HttpServlet {

    @Resource(name = "jdbc/gamingplatform")
    private static DataSource ds;


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        //se non è una chiamata ajax
        if (!"XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            Logger.getAnonymousLogger().log(Level.WARNING, "[doUpdate] non è una chiamata ajax");
            //torno KO alla chiamata servlet
            response.getWriter().write("KO");
            return;
        }

        //carico la tabella in cui si vuole modificare la tupla (la url è della forma /doUpdate/tabella/idElemento
        String item = getNlastBitFromUrl(request.getRequestURI(), 1);

        String id="";
        int itemId=0;

            //caso base di qualsiasi altra tabella con id semplice
            id=getLastBitFromUrl(request.getRequestURI());
            if(isNull(id) || id.equals("")){
                Logger.getAnonymousLogger().log(Level.WARNING, "[doDelete: "+item+"] parametri post non validi");
                response.getWriter().write("KO");
                return;
            }

        itemId=Integer.parseInt(id);

        //controllo quì se l'utente è loggato e ha acesso a quella determinata tabella
        //se l'utente sta cercando di modificare il suo profilo, glielo permetto
        if(!(item.equals("user") && getUser(request).getId() == itemId)) {
            if (!checkAuth(request, item)) {
                //se il servizio a cui si sta provando ad accedere
                //non è un servizio a cui l'utente ha accesso
                response.getWriter().write("KO");
                return;
            }
        }

        try {

            switch (item) {

                //caso update user
                case "user":
                    //prelevo parametri POST
                    String username = request.getParameter("username");
                    String name = request.getParameter("name");
                    String surname = request.getParameter("surname");
                    String email = request.getParameter("email");
                    String password = sha1Encrypt(request.getParameter("password"));
                    int exp = Integer.parseInt(request.getParameter("exp"));
                    Part avatar = request.getPart("avatar"); // recupera <input type="file" name="avatar">

                    //se i parametri in input non sono validi
                    if (isNull(username) || isNull(name) || isNull(surname) || isNull(email) || isNull(password) ||
                            username.equals("") || name.equals("") || surname.equals("") || email.equals("") || password.equals("")) {

                        Logger.getAnonymousLogger().log(Level.WARNING, "[doUpdate: "+item+"] Parametri POST non validi ");
                        //torno KO alla chiamata servlet
                        response.getWriter().write("KO");
                        return;
                    }
                    //provo ad effettuare l'upload del file
                    String avatarName = fileUpload(avatar, "avatars", getServletContext());
                    if (isNull(avatarName)) {

                        Logger.getAnonymousLogger().log(Level.WARNING, "[doUpdate: "+item+"] Upload file fallito");
                        //torno KO alla chiamata servlet
                        response.getWriter().write("KO");
                        return;
                    }

                    UserDao userDao = new UserDaoImpl(ds);

                    userDao.init();
                    //provo a modificare l'utente

                    User user = userDao.getUser();
                    user.setId(itemId); //differenza rispetto alla insert, devo fornire l'id!, è sempre dentro la variabile itemId
                    user.setUsername(username);
                    user.setName(name);
                    user.setSurname(surname);
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setExp(exp);
                    user.setAvatar(avatarName);
                    userDao.updateUser(user);

                    userDao.destroy();

                    break;


                //Caso update level
                case "level":

                    //Prelevo parametri POST
                    int namelevel=Integer.parseInt(request.getParameter("name"));
                    String trophy=request.getParameter("trophy");
                    Part icon=request.getPart("icon");
                    int explevel=Integer.parseInt(request.getParameter("exp"));

                    //Se i parametri di input non sono validi
                    if(isNull(trophy) || trophy.equals("")){
                        Logger.getAnonymousLogger().log(Level.WARNING, "[doUpdate: "+item+"] Parametri POST non validi ");
                        //torno KO alla chiamata servlet
                        response.getWriter().write("KO");
                        return;
                    }

                    //provo ad effettuare l'upload del file (icon)
                    String iconlevel = fileUpload(icon, "images", getServletContext());
                    if (isNull(iconlevel)) {

                        Logger.getAnonymousLogger().log(Level.WARNING, "[doUpdate: "+item+"] Upload file fallito");
                        //torno KO alla chiamata servlet
                        response.getWriter().write("KO");
                        return;
                    }


                    LevelDao levelDao= new LevelDaoImpl(ds);
                    levelDao.init();

                    //Provo a modificare il livello
                    gamingplatform.model.Level level=levelDao.getLevel();
                    level.setId(itemId);
                    level.setName(namelevel);
                    level.setTrophy(trophy);
                    level.setIcon(iconlevel);
                    level.setExp(explevel);
                    levelDao.updateLevel(level);

                    levelDao.destroy();
                    break;

                //Caso update game
                case "game":

                    //Prelevo parametri POST
                    String nameGame=request.getParameter("name");
                    int expgame=Integer.parseInt(request.getParameter("exp"));
                    Part image=request.getPart("image");
                    String description=request.getParameter("description");



                    //Se i parametri di input non sono validi
                    if(isNull(nameGame) || isNull(description) || nameGame.equals("") || description.equals("")){
                        Logger.getAnonymousLogger().log(Level.WARNING, "[doUpdate: "+item+"] Parametri POST non validi ");
                        //torno KO alla chiamata servlet
                        response.getWriter().write("KO");
                        return;
                    }

                    if(expgame<1){
                        expgame=1;
                    }

                    //provo ad effettuare l'upload del file (icon)
                    String imageName = fileUpload(image, "images", getServletContext());
                    if (isNull(imageName)) {

                        Logger.getAnonymousLogger().log(Level.WARNING, "[doUpdate: "+item+"] Upload file fallito");
                        //torno KO alla chiamata servlet
                        response.getWriter().write("KO");
                        return;
                    }


                    GameDao gameDao= new GameDaoImpl(ds);
                    gameDao.init();

                    //Provo a modificare il livello
                    gamingplatform.model.Game game=gameDao.getGame();
                    game.setId(itemId);
                    game.setName(nameGame);
                    game.setExp(expgame);
                    game.setImage(imageName);
                    game.setDescription(description);
                    gameDao.updateGame(game);

                    gameDao.destroy();
                    break;

                //Caso update service
                case "service" :
                    //Prelevo parametri POST
                    String nameService=request.getParameter("name");
                    String descriptionService=request.getParameter("description");

                    //Se i parametri in input non sono validi
                    if(isNull(nameService) || isNull(descriptionService) || nameService.equals("") || descriptionService.equals("")){
                        Logger.getAnonymousLogger().log(Level.WARNING, "[doUpdate: "+item+"] Parametri POST non validi ");
                        //torno KO alla chiamata servlet
                        response.getWriter().write("KO");
                        return;
                    }

                    ServiceDao serviceDao=new ServiceDaoImpl(ds);
                    serviceDao.init();

                    //Provo a modificare il servizio
                    gamingplatform.model.Service service= serviceDao.getService();
                    service.setId(itemId);
                    service.setName(nameService);
                    service.setDescription(descriptionService);
                    serviceDao.updateSerivce(service);

                    serviceDao.destroy();
                    break;

                //Caso update review
                /*
                case "review":
                    int gameId=Integer.parseInt(idGame);
                    int userId=Integer.parseInt(idUser);
                    //Prelevo i parametri POST
                    String title=request.getParameter("title");
                    String body=request.getParameter("body");
                    int vote=Integer.parseInt(request.getParameter("vote"));

                    if(isNull(title) || isNull(body)|| title.equals("") || body.equals("") || vote==0 || vote>5){
                        Logger.getAnonymousLogger().log(Level.WARNING, "[doUpdate: "+item+"] Parametri POST non validi ");
                        //torno KO alla chiamata servlet
                        response.getWriter().write("KO");
                        return;
                    }

                    ReviewDao reviewDao=new ReviewDaoImpl(ds);
                    reviewDao.init();

                    //Provo a modificare la review
                    gamingplatform.model.Review review=reviewDao.getReview();
                    review.setIdGame(gameId);
                    review.setIdUser(userId);
                    review.setTitle(title);
                    review.setBody(body);
                    review.setVote(vote);
                    reviewDao.updateReview(review);

                    reviewDao.destroy();
                    break;
                    */

                case "groups":

                    //Prelevo i parametri in POST
                    String nameGroups=request.getParameter("name");
                    String descriptionGroups=request.getParameter("description");
                    String serviceToAddRemove=request.getParameter("serviceToAddRemove");
                    String userToAddRemove=request.getParameter("userToAddRemove");
                    String radioService=request.getParameter("servicesRadio");
                    System.out.println("radioService: "+radioService);
                    String radioUser=request.getParameter("usersRadio");
                    System.out.println("radioUser: "+radioUser);


                    if(isNull(nameGroups) || isNull(descriptionGroups) || isNull(serviceToAddRemove) || isNull(userToAddRemove) ||
                        nameGroups.equals("") || descriptionGroups.equals("") || serviceToAddRemove.equals("") || userToAddRemove.equals("")){
                        Logger.getAnonymousLogger().log(Level.WARNING, "[doUpdate: "+item+"] Parametri POST non validi ");
                        //torno KO alla chiamata servlet
                        response.getWriter().write("KO");
                        return;
                    }

                    GroupsDao groupsDao=new GroupsDaoImpl(ds);
                    groupsDao.init();

                    //Provo a modificare il gruppo
                    gamingplatform.model.Group group=groupsDao.getGroup();
                    group.setId(itemId);
                    group.setName(nameGroups);
                    group.setDescription(descriptionGroups);

                    groupsDao.updateGroup(group);

                    int idService=Integer.parseInt(serviceToAddRemove);

                    int idUser=Integer.parseInt(userToAddRemove);



                    if(idUser!=-1 || idService!=-1) {


                        if (radioService.equals("add") && idService>-1) {
                                groupsDao.addServiceToGroup(itemId, idService);
                            } else if (radioService.equals("remove")&& idService>-1) {
                                groupsDao.removeServiceFromGroup(itemId, idService);
                            }

                            if (radioUser.equals("add")&& idUser>-1 ) {
                                groupsDao.addUserToGroup(itemId, idUser);
                            } else if (radioUser.equals("remove") && idUser>-1) {
                                    groupsDao.removeUserFromGroup(itemId, idUser);
                            }

                    }

                    groupsDao.destroy();

                    break;


                //default
                default:

                    Logger.getAnonymousLogger().log(Level.WARNING, "[doUpdate: "+item+"] caso default nello switch");
                    //torno KO alla chiamata servlet
                    response.getWriter().write("KO");
                    return;
            }



        } catch (DaoException e) {
            Logger.getAnonymousLogger().log(Level.WARNING, "doUpdate: "+item+"]" + e.getMessage());
            //torno KO alla chiamata servlet
            response.getWriter().write("KO");
            return;
        }
        //torno OK alla chiamata servlet se arrivo alla fine senza problemi
        response.getWriter().write("OK");

    }
}