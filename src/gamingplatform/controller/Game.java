package gamingplatform.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static gamingplatform.controller.utils.SecurityLayer.getLastBitFromUrl;
import static gamingplatform.controller.utils.SecurityLayer.redirect;
import static gamingplatform.controller.utils.SessionManager.verifySession;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNumeric;


public class Game extends HttpServlet {


    protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //se l'ultimo elemento dopo "/" non è numerico oppure se l'utente non è loggato
        if(!isNumeric(getLastBitFromUrl(request.getRequestURI())) || isNull(verifySession(request))){
            redirect("/index", "KO-not-logged", response, request);
        }

        int gameId= Integer.parseInt(getLastBitFromUrl(request.getRequestURI()));
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