package gamingplatform.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class Logout extends HttpServlet {


    protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionManager.destroySession(request);
        SecurityLayer.redirect("index", "OK-logout", response, request);
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