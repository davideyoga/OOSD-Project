package gamingplatform.controller;

import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.implementation.*;
import gamingplatform.dao.interfaces.*;
import gamingplatform.model.Group;
import gamingplatform.model.Service;
import gamingplatform.model.User;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static gamingplatform.controller.utils.Utils.getLastBitFromUrl;
import static gamingplatform.controller.utils.SecurityLayer.redirect;
import static gamingplatform.controller.utils.SecurityLayer.checkAuth;
import static gamingplatform.controller.utils.SessionManager.getServices;
import static gamingplatform.controller.utils.SessionManager.getUser;
import static gamingplatform.controller.utils.SessionManager.popMessage;
import static gamingplatform.controller.utils.Utils.getClassFields;

import gamingplatform.view.FreemarkerHelper;

/**
 * classe servlet atta alla presentazione del report dei dati del database
 * per avere un overview prima delle operazioni di backoffice
 */
public class Report extends HttpServlet {

    @Resource(name = "jdbc/gamingplatform")
    private static DataSource ds;

    //container dati che sarà processato da freemarker
    private Map<String, Object> data = new HashMap<>();

    /**
     * si occupa di mostrare tutte le tuple di una tabella
     * @param request richiesta servlet
     * @param response risposta servlet
     * @throws ServletException
     * @throws IOException
     */
    protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //pop dell'eventuale messaggio in sessione
        //nel dettaglio inserisco un elemento "message" dentro la Map che andrà a processare freemarker
        //prendendolo dalla sessione tramite SessionManager.pop che appunto ritorna il messaggio in sessione, se c'è
        //oppure null
        data.put("message", popMessage(request));

        //carico l'user nella Map prelevandolo dalla sessione se verificata
        data.put("user", getUser(request));

        //carico i servizi
        data.put("services", getServices(request));

        //recuper ultimo pezzo di url che mi identifica la tabella a cui sono interessato es. /report/user
        String item = getLastBitFromUrl(request.getRequestURI());


        //controllo quì se l'utente è loggato e ha acesso al report di quella determinata tabella
        if(!checkAuth(request, item)){
            //se l'ultimo elemento dopo lo "/" (ovvero il servizio a cui si sta provando ad accedere)
            //non è un servizio a cui l'utente ha accesso
            redirect("/index", "KO-unauthorized", response, request);
            return;
        }
        data.put("table",item);

        //disambiguo la tabella di cui l'utente vuole il report
        try {

            List<String> columns = new ArrayList<>();

            switch (item) {

                //le review vengono eliminate direttamente dalla pagina del gioco, non rendiamo possibile modificarle
                //gli user possono essere solo eliminati, non possono essere modificati

                //caso report user
                case "user":

                    UserDao userDao = new UserDaoImpl(ds);
                    userDao.init();
                    List<User> users = userDao.getUsers();
                    data.put("items",users);
                    data.put("fields",getClassFields(userDao.getUser()));
                    userDao.destroy();

                    break;

                //caso report game
                case "game":

                    GameDao gameDao = new GameDaoImpl(ds);
                    gameDao.init();
                    List<gamingplatform.model.Game> games = gameDao.getGames();
                    data.put("items",games);
                    data.put("fields",getClassFields(gameDao.getGame()));
                    gameDao.destroy();

                    break;

                //caso report service
                case "service":

                    ServiceDao serviceDao = new ServiceDaoImpl(ds);
                    serviceDao.init();
                    List <Service> services = serviceDao.getServices();
                    data.put("items",services);
                    data.put("fields",getClassFields(serviceDao.getService()));
                    serviceDao.destroy();

                    break;

                //caso report groups
                case "groups":

                    GroupsDao groupsDao = new GroupsDaoImpl(ds);
                    groupsDao.init();
                    List <Group> groups = groupsDao.getGroups();
                    data.put("items",groups);
                    data.put("fields",getClassFields(groupsDao.getGroup()));
                    groupsDao.destroy();

                    break;

                //caso report level
                case "level":

                    LevelDao levelDao = new LevelDaoImpl(ds);
                    levelDao.init();
                    List <gamingplatform.model.Level> levels = levelDao.getLevels();
                    data.put("items",levels);
                    data.put("fields",getClassFields(levelDao.getLevel()));
                    levelDao.destroy();
                    break;


                default:
                    redirect("/index", "KO", response, request);
                    return;
            }


        }catch(DaoException e){
            Logger.getAnonymousLogger().log(Level.WARNING,"[Report "+item+"] DaoException: "+e.getMessage());
            redirect("/index", "KO", response, request);
            return;
        }

        //processo template
        FreemarkerHelper.process("report.ftl", data, response, getServletContext());
    }


    /**
     * gestisce richieste GET alla servlet
     * @param request richiesta servlet
     * @param response risposta servlet
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request,response);
    }

    /**
     * gestisce richieste POST alla servlet
     * @param request richiesta servlet
     * @param response risposta servlet
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request,response);
    }
}