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
        data.put("message",SessionManager.popMessage(request));
        FreemarkerHelper.process("login.ftl", data, response, getServletContext());

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String username=request.getParameter("username");
        String password=request.getParameter("password");


        if (isNull(username) || isNull(password) || username.equals("") || password.equals("")){
            data.put("message","KO-login");
        }


        UserDao userDao=new UserDaoImpl(ds);

        try {
            userDao.init();
            User user=userDao.getUserByUsernamePassword(username, SecurityLayer.sha1Encrypt(password));

            System.out.println(user.getId()+" "+user.getEmail());
            if(user.getId()==0 || isNull(user)){

                data.put("message", "KO-login");
                FreemarkerHelper.process("login.ftl", data, response, getServletContext());
                return;
            }else{

                HttpSession session=SessionManager.initSession(request, user);
                session.setAttribute("message","OK-login");
                response.sendRedirect("index");
            }


        } catch (DaoException e) {
            e.printStackTrace();
            response.sendRedirect("index");

        }


        //process template
        FreemarkerHelper.process("login.ftl", data, response, getServletContext());
    }
}