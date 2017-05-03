package gamingplatform.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.xml.bind.DatatypeConverter;

import gamingplatform.controller.SessionManager;
import gamingplatform.dao.implementation.UserDaoImpl;
import gamingplatform.dao.interfaces.UserDao;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.isNull;


public class SecurityLayer {


    public static boolean checkAuth(){
        //TODO
        return true;
    }

    public static String sha1Encrypt(String x) {
        String sha1 = null;
        try {
            MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
            msdDigest.update(x.getBytes("UTF-8"), 0, x.length());
            sha1 = DatatypeConverter.printHexBinary(msdDigest.digest());
            sha1=sha1.toLowerCase();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.SEVERE, "SecurityException: " + e.getMessage());
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
