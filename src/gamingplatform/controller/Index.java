package gamingplatform.controller;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.implementation.GameDaoImpl;
import gamingplatform.dao.interfaces.GameDao;
import gamingplatform.model.Game;

import static gamingplatform.controller.utils.SessionManager.getUser;
import static gamingplatform.controller.utils.SessionManager.popMessage;
import static gamingplatform.view.FreemarkerHelper.process;


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
        data.put("message", popMessage(request));

        //carico l'user nella Map prelevandolo dalla sessione se verificata
        data.put("user", getUser(request));

        //carico la lista dei giochi nell amappa di freemarker per mostrarli nella home
        try{
            GameDao game = new GameDaoImpl(ds);

            game.init();
            List<Game> games = game.getGames();
            game.destroy();

            data.put("games",games);

        }catch(DaoException e){
            Logger.getAnonymousLogger().log(Level.WARNING, "[Index] DaoException: nessun gioco nel db, oppure errore nella query "+e.getMessage());
        }

        //processo template
        process("index.ftl", data, response, getServletContext());
    }

}