package gamingplatform.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static gamingplatform.controller.utils.SecurityLayer.redirect;
import static gamingplatform.controller.utils.SessionManager.destroySession;

/**
 * classe servlet che si occupa di effettuare il logout
 */
public class Logout extends HttpServlet {


    /**
     * si occupa di effettuare materialmente il logout distruggendo la sessione
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        destroySession(request);
        redirect("/index", "OK-logout", response, request);
    }

    /**
     * gestisce richieste GET alla servlet
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request,response);
    }

    /**
     * gestisce richieste POST alla servlet
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request,response);
    }
}