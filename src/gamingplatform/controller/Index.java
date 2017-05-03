package gamingplatform.controller;


import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.implementation.UserDaoImpl;
import gamingplatform.model.Game;
import gamingplatform.model.User;
import gamingplatform.dao.interfaces.UserDao;
import gamingplatform.view.FreemarkerHelper;

import static java.util.Objects.isNull;

public class Index extends HttpServlet {

    @Resource(name = "jdbc/gamingplatform")
    private static DataSource ds;


    //container dati che sarà processato da freemarker
    private Map<String, Object> data = new HashMap<>();


    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        UserDao userDao = new UserDaoImpl(ds);
        try {
            userDao.init();
        } catch (DaoException e) {
            e.printStackTrace();
        }

        User user=null;
        try {
            user = userDao.getUser(1);
        } catch (DaoException e) {
            e.printStackTrace();
        }

        data.put("pippo",user);

        try {
            userDao.destroy();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        //test
        /*
        List<Game> games=new ArrayList<>(5);
        games.add(new Game(1,"game 1",1, "ga1.jpg", "lalalala"));
        games.add(new Game(2,"game 2",2, "ga2.jpg", "lalalala"));
        games.add(new Game(3,"game 3",3, "ga3.jpg", "lalalala"));
        games.add(new Game(4,"game 4",4, "ga4.jpg", "lalalala"));
        games.add(new Game(5,"game 5",5, "ga5.jpg", "lalalala"));

        //ai fini di test controllo se la sessione è valida, altrimenti la creo con un utente fantoccio
        if (isNull(SessionManager.verifySession(request))){

            User user = new User(1,"valent0ne",null,null,null,null,0,"wo.jpg");

            //inserisco in data l'user che è attualmente in sessione
            data.put("user",SessionManager.initSession(request, user).getAttribute("user"));
        }

        data.put("games",games);
        */
        //end test

        FreemarkerHelper.process("index.ftl", data, response, getServletContext());

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }
}