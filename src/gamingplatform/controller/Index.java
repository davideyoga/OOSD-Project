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

import gamingplatform.view.FreemarkerHelper;


public class Index extends HttpServlet {

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
        data.put("message",SessionManager.popMessage(request));

        //carico l'user nella Map prelevandolo dalla sessione se verificata
        data.put("user",SessionManager.getUser(request));

        //processo template
        FreemarkerHelper.process("index.ftl", data, response, getServletContext());
    }

}