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
import java.util.logging.Level;
import java.util.logging.Logger;

import gamingplatform.controller.utils.SecurityLayer;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.implementation.UserDaoImpl;
import gamingplatform.dao.interfaces.UserDao;
import gamingplatform.model.User;

import static gamingplatform.controller.utils.SecurityLayer.abort;
import static gamingplatform.controller.utils.SecurityLayer.redirect;
import static gamingplatform.controller.utils.SessionManager.*;
import static gamingplatform.view.FreemarkerHelper.process;
import static java.util.Objects.isNull;

public class Login extends HttpServlet {


    @Resource(name = "jdbc/gamingplatform")
    private static DataSource ds;


    //container dati che sarà processato da freemarker
    private Map<String, Object> data = new HashMap<>();


    /**
     * serve richieste GET alla pagina di login (mostra la form di login ed eventuali messaggi,
     * redirezionando utenti già loggati alla home
     * @param request richiesta servlet
     * @param response risposta servlet
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //pop dell'eventuale messaggio in sessione
        data.put("message", popMessage(request));

        redirectIfLogged(request,response);

        //process template
        process("login.ftl", data, response, getServletContext());

    }


    /**
     * serve richieste POST di login, ovvero quando l'utente fa submit su form di login
     * controlla i paramentri ed esegue la procedura di login
     * @param request richiesta servlet
     * @param response risposta servlet
     * @throws ServletException
     * @throws IOException
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //prelevo parametri POST
        String username = request.getParameter("username");
        String password = request.getParameter("password");



        if (isNull(username) || isNull(password) || username.equals("") || password.equals("")){
            //in caso di dati POST non validi
            //notifico l'errore e abort rimanendo sulla stessa pagina
            Logger.getAnonymousLogger().log(Level.WARNING, "[Login] attributi post non validi, abort ");
            abort("login.ftl",data,"KO",response,getServletContext());
            return;
        }

        //istanzio userDao per operazioni su DB
        UserDao userDao=new UserDaoImpl(ds);

        try {
            //inizializzazione userDao
            userDao.init();

            //query di select user dati username e password
            User user=userDao.getUserByUsernamePassword(username, SecurityLayer.sha1Encrypt(password));

            //distruggo connessione
            userDao.destroy();

            if(user.getId()==0 || isNull(user)){
                //credenziali non valide, abort con notifica di errore, rimanendo sulla stessa pagina
                Logger.getAnonymousLogger().log(Level.WARNING, "[Login] credenziali non valide, abort");
                abort("login.ftl",data,"KO-login",response,getServletContext());
                return;
            }else{
                //credenziali corrette, inizializzazione della sessione e redirect a index
                //inserisco il messaggio di ok in sessione così sarà mostrato dalla servlet di index
                initSession(request, user);
                pushMessage(request,"OK-login");
                response.sendRedirect("index");
            }


        } catch (DaoException e) {
            //in caso di eccezione:
            // log dell'eccezione
            // redirect a index con messaggio di errore
            Logger.getAnonymousLogger().log(Level.WARNING, "[Login] DaoException, redirect alla home "+e.getMessage());
            redirect("/index","KO",response,request);
        }


        //in ogni caso process template
        process("login.ftl", data, response, getServletContext());
    }
}