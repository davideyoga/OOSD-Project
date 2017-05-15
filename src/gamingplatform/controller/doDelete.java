package gamingplatform.controller;

import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.implementation.UserDaoImpl;
import gamingplatform.dao.interfaces.UserDao;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static gamingplatform.controller.utils.SecurityLayer.checkAuth;
import static gamingplatform.controller.utils.Utils.getLastBitFromUrl;
import static gamingplatform.controller.utils.Utils.getNlastBitFromUrl;
import static java.util.Objects.isNull;


/**
 * classe atta al processamento di richieste ajax post per l'eliminazione di elementi nel db
 * risponde a url del tipo /doDelete/tabella/idElemento
 */

public class doDelete extends HttpServlet {

    @Resource(name = "jdbc/gamingplatform")
    private static DataSource ds;


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //se non è una chiamata ajax
        if (!"XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            Logger.getAnonymousLogger().log(Level.WARNING, "[doDelete] non è una chiamata ajax");
            //torno KO alla chiamata servlet
            response.getWriter().write("KO");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");

        //carico la tabella in cui si vuole aggiungere la tupla (la url è della forma /doDelete/tabella/idElemento
        String item = getNlastBitFromUrl(request.getRequestURI(), 1);

        //controllo quì se l'utente è loggato e ha acesso a quella determinata tabella
        if (!checkAuth(request, item)) {
            //se  il servizio a cui si sta provando ad accedere
            //non è un servizio a cui l'utente ha accesso
            response.getWriter().write("KO");
            return;
        }

        String id=getLastBitFromUrl(request.getRequestURI());

        if(isNull(id) || id.equals("")){
            Logger.getAnonymousLogger().log(Level.WARNING, "[doDelete: "+item+"] parametri POST non validi");
            response.getWriter().write("KO");
            return;
        }

        int itemId=Integer.parseInt(id);

        try {
            switch (item) {
                //caso eliminazione user
                case "user":
                    UserDao userDao = new UserDaoImpl(ds);
                    userDao.init();
                    //provo ad eliminare l'utente
                    userDao.deleteUserByKey(itemId);
                    userDao.destroy();
                    break;

                //altri case

                //default
                default:

                    Logger.getAnonymousLogger().log(Level.WARNING, "[doDelete: "+item+"] caso default nello switch");
                    //torno KO alla chiamata servlet
                    response.getWriter().write("KO");
                    return;
            }


        } catch (DaoException e) {
            Logger.getAnonymousLogger().log(Level.WARNING, "[doDelete: "+item+"] " + e.getMessage());
            //torno KO alla chiamata servlet
            response.getWriter().write("KO");
            return;
        }

        //torno OK alla chiamata servlet se arrivo alla fine senza problemi
        response.getWriter().write("OK");

    }
}