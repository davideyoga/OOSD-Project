package gamingplatform.controller;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import gamingplatform.view.FreemarkerHelper;

import static java.util.Objects.isNull;

public class Login extends HttpServlet {


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
        data.put("message","KO-login");

        if(SecurityLayer.login(username,password)){
            data.put("message","OK-login");
        }

        doGet(request,response);

    }
}