package gamingplatform.controller;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import gamingplatform.view.FreemarkerHelper;
import gamingplatform.controller.SecurityLayer;

public class Signup extends HttpServlet {


    //container dati che sarà processato da freemarker
    private Map<String, Object> data = new HashMap<>();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //process template
        FreemarkerHelper.process("signup.ftl", data, response, getServletContext());

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String username=request.getParameter("username");
        String name=request.getParameter("name");
        String surname=request.getParameter("surname");
        String email=request.getParameter("email");
        String password=SecurityLayer.Sha1Encrypt( request.getParameter("password"));
        String avatar= "";
        if(request.getParameter("avatar")!=null){
            avatar=request.getParameter("avatar");
        }
        avatar="defaultAvatar";





    }

}