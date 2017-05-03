package gamingplatform.controller;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gamingplatform.model.User;

import static java.util.Objects.isNull;

/**
 * classe package-private atta alla gestione delle sessioni e di tutto quello che le riguarda
 */
class SessionManager {

    //durata validità sessione in minuti
    private static final int SESSION_EXPIRE_TIME = 60*3;

    /**
     * inizializza la sessione, eliminandola se esiste, tramite i template freemarker si accede alla sessione
     * tramite Session.<attribute>
     *
     * @param request richiesta servlet
     * @param user oggetto user da inserire in sessione
     * @return la sessione creata
     */

    static HttpSession initSession(HttpServletRequest request, User user) {

        //se esiste la elimino
        destroySession(request);

        //creo la sessione
        HttpSession session = request.getSession(true);

        //carico i dati nella sessione
        session.setAttribute("user", user);
        session.setAttribute("ip_address", request.getRemoteHost());
        session.setAttribute("session_start", Calendar.getInstance());

        //carico i gruppi a cui l'utente appartiene

        //TODO caricare in sessione i gruppi a cui appartiene l'utente

        //carico l'elenco dei servizi a cui ha accesso l'utente

        //TODO caricare in sessione i servizi a cui l'utente ha accesso

        return session;
    }

    /**
     * distrugge la sessione se esiste
     *
     * @param request richiesta servlet
     */

    static void destroySession(HttpServletRequest request) {
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
    static HttpSession verifySession(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        try {
            //se la sessione non è attiva
            if (isNull(session)) {
                throw new SecurityException("session is null");
            }

            //controllo che ci sia l'user in sessione
            User user = (User) session.getAttribute("user");
            if (isNull(user)) {

                throw new SecurityException("session is not valid (user not in session)");

                //controllo l'indirizzo ip del client, controllo se c'è e poi se corrisponde effettivamente a
                //quello del client
            } else if ((session.getAttribute("ip_address") == null) ||
                    !(session.getAttribute("ip_address")).equals(request.getRemoteHost())) {

                throw new SecurityException("session is not valid (ip does not match)");
            } else {
                Calendar now = Calendar.getInstance();
                Calendar start = (Calendar) session.getAttribute("session_start");

                if (start == null) {
                    throw new SecurityException("session is not valid (session_start is null)");
                }

                long sessionAgeInMinutes = ((now.getTimeInMillis() - start.getTimeInMillis()) / 1000) / 30;

                if (sessionAgeInMinutes > SESSION_EXPIRE_TIME) {
                    throw new SecurityException("session expired");
                }
            }

        } catch (SecurityException ex) {
            //distruggo la sessione e setto header 401 nella risposta html
            destroySession(request);
            //loggo l'errore
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.WARNING, "SecurityException: " + ex.getMessage());

            return null;
        }

        //se non ci sono errori torno la sessione
        return session;
    }

    /**
     * crea la sessione se non esiste e la riempie con il message
     * @param request richiesta servlet
     * @param message messaggio da inserire
     */
    static void pushMessage(HttpServletRequest request, String message){
        HttpSession session=request.getSession(true);
        session.removeAttribute("message");
        session.setAttribute("message", message);
    }

    /**
     * preleva il messaggio dalla sessione, eliminandolo dalla stessa (pop)
     * @param request richiesta servlet
     * @ il messaggio in sessione se c'è, oppure null
     */
    static String popMessage(HttpServletRequest request){
        String message=null;
        HttpSession session=SessionManager.verifySession(request);
        if(!isNull(session)){
            message = (String)session.getAttribute("message");
            session.removeAttribute("message");
        }
        return message;
    }


    /**
     * recupera l'utente in sessione se la sessione è valida
     * @param request richiesta servlet
     * @return l'user se esiste e se la sessione è valuda, null altrimenti
     */
    static User getUser(HttpServletRequest request){
        User user=null;

        try {
            user = (User) verifySession(request).getAttribute("user");
        }catch(NullPointerException e){
            destroySession(request);
        }

        return user;
    }
}

