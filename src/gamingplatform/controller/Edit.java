package gamingplatform.controller;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.implementation.*;
import gamingplatform.dao.interfaces.*;
import gamingplatform.model.DBTableStructure;
import gamingplatform.model.Group;
import gamingplatform.model.Service;
import gamingplatform.model.User;

import static gamingplatform.controller.utils.SecurityLayer.checkAuth;
import static gamingplatform.controller.utils.SecurityLayer.redirect;
import static gamingplatform.controller.utils.Utils.getLastBitFromUrl;
import static gamingplatform.controller.utils.Utils.getNlastBitFromUrl;
import static gamingplatform.controller.utils.SessionManager.getServices;
import static gamingplatform.controller.utils.SessionManager.getUser;
import static gamingplatform.controller.utils.SessionManager.popMessage;
import static gamingplatform.view.FreemarkerHelper.process;


public class Edit extends HttpServlet {

    @Resource(name = "jdbc/gamingplatform")
    private static DataSource ds;

    //container dati che sarà processato da freemarker
    private Map<String, Object> data = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //pop dell'eventuale messaggio in sessione
        //nel dettaglio inserisco un elemento "message" dentro la Map che andrà a processare freemarker
        //prendendolo dalla sessione tramite SessionManager.pop che appunto ritorna il messaggio in sessione, se c'è
        //oppure null
        data.put("message", popMessage(request));

        //carico l'user nella Map prelevandolo dalla sessione se verificata
        data.put("user", getUser(request));

        //carico i servizi
        data.put("services", getServices(request));

        //carico la "modalità" (edit)
        data.put("mode", "edit");

        //recuper il peultimo pezzo di url che mi identifica la tabella a cui sono interessato es. /edit/user/5, io voglio "user"
        String item = getNlastBitFromUrl(request.getRequestURI(), 1);
        data.put("table", item);

        //recupero l'id dell'item di cui mostrare la form di modifica
        Integer id = Integer.parseInt(getLastBitFromUrl(request.getRequestURI()));
        data.put("itemId", id);



        //controllo quì se l'utente è loggato e ha acesso al report di quella determinata tabella
        //se l'utente sta cercando di modificare il suo profilo, glielo permetto
        if(!(item.equals("user") && getUser(request).getId()==id)) {
            if (!checkAuth(request, item)) {
                //se l'ultimo elemento dopo lo "/" (ovvero il servizio a cui si sta provando ad accedere)
                //non è un servizio a cui l'utente ha accesso
                redirect("/index", "KO-unauthorized", response, request);
                return;
            }
        }


        try {

            DBTableStructureDao dbsDao = new DBTableStructureDaoImpl(ds);

            dbsDao.init(item);
            DBTableStructure dbs = dbsDao.getDBTableStructure();
            dbs = dbsDao.getTableStructure();
            dbsDao.destroy();
            data.put("fields", dbs.getFields());
            data.put("keys", dbs.getKeys());
            data.put("nulls", dbs.getNulls());
            data.put("types", dbs.getTypes());
            data.put("defaults", dbs.getDefaults());
            data.put("extras", dbs.getExtras());
            data.put("arity", dbs.getArity());


            switch (item) {

                case "user":

                    UserDao userDao = new UserDaoImpl(ds);
                    userDao.init();
                    User user = userDao.getUser(id);
                    data.put("item", user);
                    userDao.destroy();

                    break;

                case "game":

                    GameDao gameDao = new GameDaoImpl(ds);
                    gameDao.init();
                    gamingplatform.model.Game game = gameDao.getGameById(id);
                    data.put("item", game);
                    gameDao.destroy();

                    break;

                case "service":

                    ServiceDao serviceDao = new ServiceDaoImpl(ds);
                    serviceDao.init();
                    Service service = serviceDao.getServiceById(id);
                    data.put("item", service);
                    serviceDao.destroy();

                    break;


                case "groups":

                    GroupsDao groupsDao = new GroupsDaoImpl(ds);
                    groupsDao.init();
                    Group groups = groupsDao.getGroup(id);


                    List<Service> servicesInGroup = groupsDao.getServicesByGroupId(id);
                    List<Service> servicesNotInGroup = groupsDao.getServicesNotInThisGroup(id);

                    List<User> usersInGroup = groupsDao.getUsersByGroupId(id);
                    List<User> usersNotInGroup = groupsDao.getUsersNotInThisGroup(id);

                    groupsDao.destroy();


                    data.put("item", groups);
                    data.put("servicesNotInGroup",servicesNotInGroup);
                    data.put("servicesInGroup",servicesInGroup);

                    data.put("usersNotInGroup",usersNotInGroup);
                    data.put("usersInGroup",usersInGroup);

                    break;


                case "level":

                    LevelDao levelDao = new LevelDaoImpl(ds);
                    levelDao.init();
                    gamingplatform.model.Level level = levelDao.getLevelById(id);
                    data.put("item", level);
                    levelDao.destroy();
                    break;

                default:
                    redirect("/index", "KO", response, request);
                    return;
            }


        } catch (DaoException e) {
            Logger.getAnonymousLogger().log(Level.WARNING, "[Edit: item = " + item + "] " + e.getMessage());
        }


        //processo template
        process("crudForm.ftl", data, response, getServletContext());
    }

}