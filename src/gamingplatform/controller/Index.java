package gamingplatform.controller;


import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.implementation.UserDaoImpl;
import gamingplatform.model.Game;
import gamingplatform.model.User;
import gamingplatform.dao.interfaces.UserDao;
import gamingplatform.view.FreemarkerHelper;

import static java.util.Objects.isNull;

public class Index extends HttpServlet {

    @Resource(name = "jdbc/gamingplatform")
    private static DataSource ds;


    //container dati che sarà processato da freemarker
    private Map<String, Object> data = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        data.put("message",SessionManager.popMessage(request));
        FreemarkerHelper.process("index.ftl", data, response, getServletContext());
    }

}