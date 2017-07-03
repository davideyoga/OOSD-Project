package gamingplatform.controller;

import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.implementation.*;
import gamingplatform.dao.interfaces.*;
import gamingplatform.model.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static gamingplatform.controller.utils.SecurityLayer.redirect;
import static gamingplatform.controller.utils.SessionManager.*;
import static gamingplatform.view.FreemarkerHelper.process;
import static java.util.Objects.isNull;

/**
 * classe servlet atta alla presentazione dei dati relativi al profilo utente
 */
public class Profile extends HttpServlet {

    @Resource(name = "jdbc/gamingplatform")
    private static DataSource ds;

    //container dati che sarà processato da freemarker
    private Map<String, Object> data = new HashMap<>();


    /**
     * gestisce richieste GET alla servlet, nello specifico presenta i dati relativi al profilo utente
     * ovvero i dati dell'utente e lo storico delle giocate e dei livelli raggiunti
     * @param request richiesta servlet
     * @param response richiesta servlet
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //carico i servizi
        data.put("services", getServices(request));

        //se l'ultimo elemento dopo "/" non è numerico oppure se l'utente non è loggato
        if(isNull(verifySession(request))){
            Logger.getAnonymousLogger().log(Level.WARNING,"[Profile] user non loggato");
            redirect("/", "KO-not-logged", response, request);
            return;
        }

        User user = (User)verifySession(request).getAttribute("user");

        int userId = user.getId();

        try{
            UserDao userDao = new UserDaoImpl(ds);
            UserLevelDao userLevelDao = new UserLevelDaoImpl(ds);
            UserGameDao userGameDao = new UserGameDaoImpl(ds);
            LevelDao levelDao = new LevelDaoImpl(ds);
            GameDao gameDao = new GameDaoImpl(ds);

            gameDao.init();
            levelDao.init();
            userDao.init();
            userLevelDao.init();
            userGameDao.init();

            data.put("user", userDao.getUser(userId));

            //prelevo la lista degli ultimi 5 livelli raggiunti e delle ultime 5 giocate
            gamingplatform.model.Level currentLevel = userDao.getLevelByUserId(userId);
            List<List<Object>> userGameList = userGameDao.getLastXItemsFromUserGame(userId,5);
            List<List<Object>> userLevelList = userLevelDao.getLastXItemsFromUserLevel(userId,5);

            Collections.reverse(userGameList);
            Collections.reverse(userLevelList);

            //recupero la lista di tutte le giocate e di tutti i livelli raggiunti
            List<List<Object>> userGameListFull = userGameDao.getLastXItemsFromUserGame(userId,100000);
            List<List<Object>> userLevelListFull = userLevelDao.getLastXItemsFromUserLevel(userId,100000);

            Collections.reverse(userGameListFull);
            Collections.reverse(userLevelListFull);

            //calcolo quanto manca a raggiungere il livello successivo
            gamingplatform.model.Level nextLevel = levelDao.getNextLevel(currentLevel);

            userDao.destroy();
            userLevelDao.destroy();
            levelDao.destroy();
            userGameDao.destroy();
            userLevelDao.destroy();

            //carico i dati nella map di freemarker
            data.put("userGameList", userGameList);
            data.put("userLevelList", userLevelList);
            data.put("userGameListFull", userGameListFull);
            data.put("userLevelListFull", userLevelListFull);


            data.put("level", currentLevel);
            data.put("nextLevel", nextLevel);

            //calcolo la percentuale di exp necessaria al raggiungimento del livello succesivo
            double z = user.getExp();
            double x = currentLevel.getExp();
            double y = nextLevel.getExp();
            double temp= ((z-x)/(y-x));
            data.put("percentage", temp*100);


        }catch (DaoException e){
            Logger.getAnonymousLogger().log(Level.WARNING, "[Profile] DaoException: "+e.getMessage());
            redirect("/", "KO", response, request);
            return;
        }

        //processo template
        process("profile.ftl", data, response, getServletContext());
    }

}