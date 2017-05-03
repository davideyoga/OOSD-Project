package gamingplatform.controller;


import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.implementation.UserDaoImpl;
import gamingplatform.dao.interfaces.UserDao;
import gamingplatform.model.User;
import gamingplatform.view.FreemarkerHelper;
import gamingplatform.controller.SecurityLayer;

import static java.util.Objects.isNull;

public class Login extends HttpServlet {


    @Resource(name = "jdbc/gamingplatform")
    private static DataSource ds;

    //container dati che sarà processato da freemarker
    private Map<String, Object> data = new HashMap<>();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //process template
        FreemarkerHelper.process("login.ftl", data, response, getServletContext());

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username=request.getParameter("username");
        String password=request.getParameter("password");


        if (isNull(username) || isNull(password) || username.equals("") || password.equals("")){
            data.put("message","KO-login");
            System.out.println("primo KO");

            doGet(request,response);
        }


        UserDao userDao=new UserDaoImpl(ds);

        try {
            userDao.init();
            User user=userDao.getUserByUsernamePassword(username, SecurityLayer.sha1Encrypt(password));
            if(user.getId()==0 || isNull(user)){
                System.out.println("secondo KO");
                data.put("message","KO-login");
            }else{
                HttpSession session=SessionManager.initSession(request, user);
                data.put("message","OK-login");
                data.put("user",session.getAttribute("user"));
            }
            doGet(request,response);


        } catch (DaoException e) {
            e.printStackTrace();
        }


        //user.getUserByUsernamePassword

    }
}