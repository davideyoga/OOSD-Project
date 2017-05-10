package gamingplatform.controller;

import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.implementation.*;
import gamingplatform.dao.interfaces.*;
import gamingplatform.model.DBTableStructure;

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

import static gamingplatform.controller.utils.SecurityLayer.checkAuth;
import static gamingplatform.controller.utils.SecurityLayer.redirect;
import static gamingplatform.controller.utils.SessionManager.*;
import static gamingplatform.controller.utils.Utils.getLastBitFromUrl;
import static gamingplatform.view.FreemarkerHelper.process;


public class Add extends HttpServlet {

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
        data.put("mode", "add");

        //carico la tabella in cui si vuole aggiungere la tupla (la url è della forma /add/tabella
        String item = getLastBitFromUrl(request.getRequestURI());
        data.put("table", item);

        //controllo quì se l'utente è loggato e ha acesso al report di quella determinata tabella
        if (!checkAuth(request, item)) {
            //se l'ultimo elemento dopo lo "/" (ovvero il servizio a cui si sta provando ad accedere)
            //non è un servizio a cui l'utente ha accesso
            redirect("/index", "KO-unauthorized", response, request);
            return;
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

        } catch (DaoException e) {
            Logger.getAnonymousLogger().log(Level.WARNING, "[Add: item = " + item + "] " + e.getMessage());
        }


        //processo template
        process("crudForm.ftl", data, response, getServletContext());
    }

}