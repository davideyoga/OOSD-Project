package gamingplatform.controller.utils;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.implementation.GroupsDaoImpl;
import gamingplatform.dao.implementation.ServiceDaoImpl;
import gamingplatform.dao.implementation.UserDaoImpl;
import gamingplatform.dao.interfaces.GroupsDao;
import gamingplatform.dao.interfaces.ServiceDao;
import gamingplatform.dao.interfaces.UserDao;
import gamingplatform.model.Group;
import gamingplatform.model.Service;
import gamingplatform.model.User;

import static java.util.Objects.isNull;


/**
 * classe atta alla gestione delle sessioni e di tutto quello che le riguarda
 */
public class SessionManager {

    @Resource(name = "jdbc/gamingplatform")
    private static DataSource ds;

    //durata validità sessione in minuti
    private static final int SESSION_EXPIRE_TIME = 60*3;


    /**
     * inizializza la sessione, eliminandola se esiste
     *
     * @param request richiesta servlet
     * @param user oggetto user da inserire in sessione
     * @return la sessione creata
     */

    public static HttpSession initSession(HttpServletRequest request, User user) {

        //se esiste la elimino
        destroySession(request);

        //creo la sessione
        HttpSession session = request.getSession(true);

        //carico i dati nella sessione
        session.setAttribute("user", user);
        session.setAttribute("ip_address", request.getRemoteHost());
        session.setAttribute("session_start", Calendar.getInstance());

        //carico i gruppi a cui l'utente appartiene

        try{
            GroupsDao groupsDao = new GroupsDaoImpl(ds);
            groupsDao.init();
            List<Group> groups = groupsDao.getGroupsByUserId(user.getId());
            groupsDao.destroy();
            session.setAttribute("groups",groups);
        }catch (DaoException e){
            Logger.getAnonymousLogger().log(Level.INFO, "DaoException: user non appartenente a nessun gruppo, oppure query fallita "+e.getMessage());
        }

        //carico l'elenco dei servizi a cui ha accesso l'utente

        try{
            ServiceDao serviceDao = new ServiceDaoImpl(ds);
            serviceDao.init();
            List<Service> services = serviceDao.getServicesByUserId(user.getId());
            serviceDao.destroy();
            session.setAttribute("services",services);
        }catch (DaoException e){
            Logger.getAnonymousLogger().log(Level.INFO, "DaoException: user non appartenente a nessun gruppo, oppure query fallita "+e.getMessage());
        }

        return session;
    }

    /**
     * distrugge la sessione
     *
     * @param request richiesta servlet
     */

    public static void destroySession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        //se la sessione esiste la elimino
        if (session != null) {
            session.invalidate();
        }
    }


    /**
     * verifica la validità della sessione
     *
     * @param request  richiesta servlet
     * @return la sessione, se valida oppure null
     */
    public static HttpSession verifySession(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        try {
            //se la sessione non è attiva
            if (isNull(session)) {
                throw new SecurityException("session è null");
            }

            //controllo che ci sia l'user in sessione
            User user = (User) session.getAttribute("user");
            if (isNull(user)) {

                throw new SecurityException("session non valida (user non in session)");

                //controllo l'indirizzo ip del client, controllo se c'è e poi se corrisponde effettivamente a
                //quello del client
            } else if ((session.getAttribute("ip_address") == null) ||
                    !(session.getAttribute("ip_address")).equals(request.getRemoteHost())) {

                throw new SecurityException("session non valida (ip non matcha)");
            } else {
                Calendar now = Calendar.getInstance();
                Calendar start = (Calendar) session.getAttribute("session_start");

                if (start == null) {
                    throw new SecurityException("session non valida (session_start è null)");
                }

                long sessionAgeInMinutes = ((now.getTimeInMillis() - start.getTimeInMillis()) / 1000) / 30;

                if (sessionAgeInMinutes > SESSION_EXPIRE_TIME) {
                    throw new SecurityException("session expired");
                }
            }

            //se non ci sono errori torno la sessione aggiornata
            int id=((User)session.getAttribute("user")).getId();
            UserDao ud = new UserDaoImpl(ds);
            ud.init();
            User updatedUser = ud.getUser(id);
            return initSession(request, updatedUser);

        } catch (Exception ex) {

            destroySession(request);
            //loggo l'errore
            Logger.getAnonymousLogger().log(Level.INFO, "SecurityException: " + ex.getMessage());

            return null;
        }
    }

    /**
     * crea la sessione se non esiste e la riempie con il message (push)
     * @param request richiesta servlet
     * @param message messaggio da inserire
     */
    public static void pushMessage(HttpServletRequest request, String message){
        HttpSession session=request.getSession(true);
        session.removeAttribute("message");
        session.setAttribute("message", message);
    }

    /**
     * preleva il messaggio dalla sessione, eliminandolo dalla stessa (pop)
     * @param request richiesta servlet
     * @return il messaggio in sessione se c'è, oppure null
     */
    public static String popMessage(HttpServletRequest request){
        String message=null;

        try{
            HttpSession session=request.getSession(false);
            message = (String)session.getAttribute("message");
            session.removeAttribute("message");

        }catch(NullPointerException e){
            Logger.getAnonymousLogger().log(Level.INFO,"nessun messaggio in sessione ");

        }

        return message;
    }


    /**
     * recupera l'utente in sessione se la sessione è valida
     * @param request richiesta servlet
     * @return l'user se esiste e se la sessione è valuda, null altrimenti
     */
    public static User getUser(HttpServletRequest request){
        User user=null;

        try {
            user = (User) request.getSession().getAttribute("user");
        }catch(NullPointerException e){
            Logger.getAnonymousLogger().log(Level.WARNING,"non posso recuperare l'user dalla sessione "+e.getMessage());
        }

        return user;
    }

    /**
     * recupera i servizi relativi all'user in sessione se la sessione è valida
     * @param request richiesta servlet
     * @return la lista dei servizi se presenti opure null su errorre
     */
    public static List<Service> getServices(HttpServletRequest request){
        List <Service> services=null;

        try {
            services = (List<Service>)request.getSession().getAttribute("services");
        }catch(NullPointerException e){
            Logger.getAnonymousLogger().log(Level.WARNING,"non posso recuperare i servizi dalla sessione "+e.getMessage());
        }

        return services;
    }

    /**
     * controlla se l'utente è già loggato, se si lo redireziona alla home
     *
     * @param request
     * @param response
     */
    public static void redirectIfLogged(HttpServletRequest request, HttpServletResponse response){
        //selesiste già una sessione valida redirect a index senza messaggi
        HttpSession session = verifySession(request);
        if(!isNull(session)){
            Logger.getAnonymousLogger().log(Level.INFO, "utente già loggato, redirect alla home");
            try {
                response.sendRedirect("index");
            } catch (IOException e) {
                Logger.getAnonymousLogger().log(Level.INFO, "[SessionManager] sendRedirect IOException "+e.getMessage());
            }
        }
    }
}

