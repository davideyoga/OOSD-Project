package gamingplatform.controller;


import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.implementation.UserDaoImpl;
import gamingplatform.dao.interfaces.UserDao;
import gamingplatform.model.User;

import static gamingplatform.controller.utils.Utils.fileUpload;
import static gamingplatform.controller.utils.SecurityLayer.*;
import static gamingplatform.controller.utils.SessionManager.popMessage;
import static gamingplatform.controller.utils.SessionManager.redirectIfLogged;
import static gamingplatform.view.FreemarkerHelper.process;
import static java.util.Objects.isNull;

@MultipartConfig(
        fileSizeThreshold=1024*1024,    // 1 MB
        maxFileSize=1024*1024*5,        // 5 MB
        maxRequestSize=1024*1024*5*5    // 25 MB
)
public class Signup extends HttpServlet {

    @Resource(name = "jdbc/gamingplatform")
    private static DataSource ds;

    //container dati che sarà processato da freemarker
    private Map<String, Object> data = new HashMap<>();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        data.put("message", popMessage(request));

        redirectIfLogged(request,response);

        //process template
        process("signup.ftl", data, response, getServletContext());

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        //prelevo parametri POST
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = sha1Encrypt( request.getParameter("password"));
        Part avatar = request.getPart("avatar"); // recupera <input type="file" name="avatar">

        //se i parametri in input non sono validi
        if(isNull(username) || isNull(name) || isNull(surname) || isNull(email) || isNull(password) ||
           username.equals("") || name.equals("") || surname.equals("") || email.equals("") || password.equals("")){

            Logger.getAnonymousLogger().log(Level.WARNING,"[Signup] Parametri POST non validi ");
            abort("signup.ftl",data,"KO-signup",response,getServletContext());
            return;
        }
        //provo ad effettuare l'upload del file
        String avatarName = fileUpload(avatar,"avatars", getServletContext());
        if(isNull(avatarName)){

            Logger.getAnonymousLogger().log(Level.WARNING,"[Signup] Upload file fallito");
            abort("signup.ftl",data,"KO-signup",response,getServletContext());
            return;
        }

        UserDao userDao = new UserDaoImpl(ds);
        try{
            userDao.init();
            //provo ad inserire l'utente

            User user=userDao.getUser();

            user.setUsername(username);
            user.setName(name);
            user.setSurname(surname);
            user.setEmail(email);
            user.setPassword(password);
            user.setExp(0);
            //TODO nell'iserimento dell'utente (da fare anche in doInsert/user) bisogna aggiungere na tupla dentro userlevel che indica che al momento della registrazione l'utente è al livello 0

            user.setAvatar(avatarName);

            userDao.insertUser(user);

            userDao.destroy();
        }catch(DaoException e){
            //in caso di errori nell'inserimento dell'utente
            Logger.getAnonymousLogger().log(Level.WARNING,"[Signup] Inserimento utente fallito "+e.getMessage());
            abort("signup.ftl",data,"KO-signup",response,getServletContext());
            return;
        }

        //se arrivo quì ho inserito l'user con successo
        redirect("/login","OK-signup",response,request);

    }

}