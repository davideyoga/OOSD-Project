package gamingplatform.controller;


import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.implementation.UserDaoImpl;
import gamingplatform.dao.interfaces.UserDao;
import gamingplatform.view.FreemarkerHelper;

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

        data.put("message",SessionManager.popMessage(request));

        SessionManager.redirectIfLogged(request,response);



        //process template
        FreemarkerHelper.process("signup.ftl", data, response, getServletContext());

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        //prelevo parametri POST
        String username=request.getParameter("username");
        String name=request.getParameter("name");
        String surname=request.getParameter("surname");
        String email=request.getParameter("email");
        String password=SecurityLayer.sha1Encrypt( request.getParameter("password"));
        Part avatar = request.getPart("avatar"); // recupera <input type="file" name="avatar">

        //se i parametri in input non sono validi
        if(isNull(username) || isNull(name) || isNull(surname) || isNull(email) || isNull(password) ||
           username.equals("") || name.equals("") || surname.equals("") || email.equals("") || password.equals("")){

            Logger.getAnonymousLogger().log(Level.WARNING,"[Signup] Parametri POST non validi ");
            SecurityLayer.abort("signup.tlp",data,"KO-signup",request,response,getServletContext());
            return;
        }
        //provo ad effettuare l'upload del file
        String avatarName = FileManager.fileUpload(avatar,"avatar");
        if(isNull(avatarName)){

            Logger.getAnonymousLogger().log(Level.WARNING,"[Signup] Upload file fallito");
            SecurityLayer.abort("signup.tpl",data,"KO-signup", request,response,getServletContext());
            return;
        }

        UserDao userDao = new UserDaoImpl(ds);
        try{
            //provo ad inserire l'utente
            userDao.insertUser(username, name, surname, email, password, 0, avatarName);
        }catch(DaoException e){
            //in caso di errori nell'inserimento dell'utente
            Logger.getAnonymousLogger().log(Level.WARNING,"[Signup] Inserimento utente fallito "+e.getMessage());
            SecurityLayer.abort("signup.tpl",data,"KO-KO-signup", request,response,getServletContext());
            return;
        }

        //se arrivo quì ho inserito l'user con successo
        SecurityLayer.redirect("index","OK-signup",response,request);

    }

}