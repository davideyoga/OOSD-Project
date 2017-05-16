package gamingplatform.controller.utils;

import gamingplatform.model.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import static gamingplatform.controller.utils.SessionManager.verifySession;
import static gamingplatform.view.FreemarkerHelper.process;
import static java.util.Objects.isNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SecurityLayer {


    /**
     * passata una stringa corrispondente al nome di un servizio controlla se l'utente loggato
     * ha accesso a quel servizio
     * @param request richiesta servlet
     * @param service servizio da controllare
     * @return true se l'utente è autorizzato all'uso del servizio, false altrimenti
     */
    public static boolean checkAuth(HttpServletRequest request, String service){
        //controllo se la sessione è valida
        HttpSession session = verifySession(request);
        if(!isNull(session) && !isNull(service)){

            //recupero la lista dei servizi dalla sessione
            List<Service> services = (ArrayList<Service>) session.getAttribute("services");
            //scorro i servizi e provo a trovare il servizio con nome = a quello passato
            for (Service s: services){
                if(s.getName().equals(service)){
                    Logger.getAnonymousLogger().log(Level.INFO,"[SecurityManager] servizio trovato, accesso consentito");
                    //se lo trovo
                    return true;
                }
            }
            Logger.getAnonymousLogger().log(Level.INFO,"[SecurityManager] servizio non trovato, accesso negato");
        }
        //altrimenti
        Logger.getAnonymousLogger().log(Level.INFO,"[SecurityManager] sessione non valida oppure service == null, accesso negato");
        return false;
    }

    /**
     * codifica la stringa in sha1
     * @param x stringa da codificare
     * @return sha1(x) oppure null
     */
    public static String sha1Encrypt(String x) {
        if(isNull(x) || x.equals("")){
            Logger.getAnonymousLogger().log(Level.WARNING, "SecurityException: parametro null" );
            return null;
        }
        String sha1 = null;
        try {
            MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
            msdDigest.update(x.getBytes("UTF-8"), 0, x.length());
            sha1 = DatatypeConverter.printHexBinary(msdDigest.digest());
            sha1=sha1.toLowerCase();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            Logger.getAnonymousLogger().log(Level.WARNING, "SecurityException: " + e.getMessage());
        }
        return sha1;
    }



    /**
     * abort della servlet con messaggio di errore rimanendo sulla stessa pagina
     * @param tpl template da elaborare da freemarker
     * @param data map con i dati da elaborare da freemarker
     * @param message messaggio da notificare all'user
     * @param response risposta servlet
     * @param svc servlet context
     */
    public static void abort(String tpl, Map<String, Object> data, String message, HttpServletResponse response, ServletContext svc){

        data.put("message", message);
        process(tpl, data, response, svc);

    }

    /**
     * abort della servlet con messaggio e redirect
     * @param redirect pagina a cui effettuare il redirect
     * @param message messaggio da notificare all'user
     * @param response risposta servlet
     * @param request richiesta servlet
     */
    public static void redirect(String redirect, String message, HttpServletResponse response, HttpServletRequest request){
        SessionManager.pushMessage(request, message);
        try {
            response.sendRedirect(redirect);
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Redirect fallito "+e.getMessage());
        }

    }



    //--------- DATA SECURITY ------------//




    /**
     * questa funzione aggiunge un backslash davanti a
     * tutti i caratteri "pericolosi", usati per eseguire
     * SQL injection attraverso i parametri delle form
     * @param s stringa da modificare
     * @return stringa pulita
     */
    public static String addSlashes(String s) {

        if(isNull(s)){
            return "";
        }
        return s.replaceAll("(['\"\\\\])", "\\\\$1");
    }

    /**
     * questa funzione rimuove gli slash aggiunti da addSlashes
     * @param s stringa da modificare
     * @return stringa pulita
     */
    public static String stripSlashes(String s) {
        if(isNull(s)){
            return "";
        }

        return s.replaceAll("\\\\(['\"\\\\])", "$1");
    }

}
