package gamingplatform.controller;

import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.implementation.UserDaoImpl;
import gamingplatform.dao.interfaces.UserDao;
import gamingplatform.model.User;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static gamingplatform.controller.utils.SecurityLayer.getLastBitFromUrl;
import static gamingplatform.controller.utils.SecurityLayer.redirect;
import static gamingplatform.controller.utils.SecurityLayer.checkAuth;




public class Report extends HttpServlet {

    @Resource(name = "jdbc/gamingplatform")
    private static DataSource ds;


    protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String item = getLastBitFromUrl(request.getRequestURI());

        //se l'ultimo elemento dopo lo "/" (ovvero il servizio a cui si sta provando ad accedere)
        //non è un servizio a cui l'utente ha accesso
        if(!checkAuth(request, item)){
            redirect("index", "KO-unauthorized", response, request);
            return;
        }

        //controllo quì se l'utente è loggato e ha acesso al report di quella determinata tabella
        //disambiguo la tabella di cui l'utente vuole il report
        try{
            switch(item){
                case "user":

                    UserDao userDao=new UserDaoImpl(ds);
                    userDao.init();
                    List<User> items = userDao.getUsers();
                    userDao.destroy();
                    break;

                case "game":
                    break;

                //TODO altre tabelle

        }


        }catch(DaoException e){
            Logger.getAnonymousLogger().log(Level.WARNING,"[Report "+item+"] DaoException: "+e.getMessage());
        }


        redirect("index", "OK-logout", response, request);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request,response);
    }
}