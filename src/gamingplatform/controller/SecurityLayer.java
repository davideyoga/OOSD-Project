package gamingplatform.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import gamingplatform.controller.SessionManager;
import gamingplatform.dao.implementation.UserDaoImpl;
import gamingplatform.dao.interfaces.UserDao;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.isNull;


public class SecurityLayer {


    public static boolean checkAuth(){
        //TODO
        return true;
    }

    public static String Sha1Encrypt(String x) {
        String sha1=x;
        java.security.MessageDigest d = null;
        try{
            d = java.security.MessageDigest.getInstance("SHA-1");
            d.reset();
            d.update(x.getBytes());
            sha1 = new String(d.digest(),"UTF-8");
        } catch(Exception e){
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.SEVERE, "Encrypting exception: " + e.getMessage(), e);

        }
        return sha1;
    }


    /**
     * aggiorna i valori di gruppi/servizi a cui appartiene l'utente e li carica in sessione
     *
     * @param request richiesta servlet
     */

    public static void updateAuth(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = SessionManager.verifySession(request);
        //TODO update permessi
    }


}
