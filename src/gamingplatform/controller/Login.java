package gamingplatform.controller;


import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.implementation.UserDaoImpl;
import gamingplatform.dao.interfaces.UserDao;
import gamingplatform.view.FreemarkerHelper;

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


        if (isNull(username) || isNull(password)){
            data.put("message","KO-login");
            doGet(request,response);

        }

        //user.getUserByUsernamePassword

    }
}