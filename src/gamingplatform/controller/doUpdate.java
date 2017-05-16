package gamingplatform.controller;

import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.implementation.UserDaoImpl;
import gamingplatform.dao.interfaces.UserDao;
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

        //controllo quì se l'utente è loggato e ha acesso a quella determinata tabella
        if (!checkAuth(request, item)) {
            //se il servizio a cui si sta provando ad accedere
            //non è un servizio a cui l'utente ha accesso
            response.getWriter().write("KO");
            return;
        }

        String id="";

        //per gestire il caso review abbiamo bisogno di 2 id, l'id utente e l'id del gioco per identificare la singola recensione
        //l'uri è del tipo /doUpdate/review/idGioco&idUser in questo caso
        String idGame="";
        String idUser="";

        if(item.equals("review")){

            String idArray[]=getLastBitFromUrl(request.getRequestURI()).split("&");
            idGame=idArray[0];
            idUser=idArray[1];

        }else{
            //caso base di qualsiasi altra tabella con id semplice
            id=getLastBitFromUrl(request.getRequestURI());

        }


        if(isNull(id) || id.equals("")){
            Logger.getAnonymousLogger().log(Level.WARNING, "[doDelete: "+item+"] parametri post non validi");
            response.getWriter().write("KO");
            return;
        }

        int itemId=Integer.parseInt(id);

        try {

            switch (item) {

                //caso inserimento user
                case "user":
                    //prelevo parametri POST
                    String username = request.getParameter("username");
                    String name = request.getParameter("name");
                    String surname = request.getParameter("surname");
                    String email = request.getParameter("email");
                    String password = sha1Encrypt(request.getParameter("password"));
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
                    //provo ad inserire l'utente

                    User user = userDao.getUser();
                    user.setId(itemId); //differenza rispetto alla insert, devo fornire l'id!, è sempre dentro la variabile itemId
                    user.setUsername(username);
                    user.setName(name);
                    user.setSurname(surname);
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setExp(0);
                    user.setAvatar(avatarName);
                    userDao.updateUser(user);
                    userDao.destroy();

                    break;

                //altri case

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