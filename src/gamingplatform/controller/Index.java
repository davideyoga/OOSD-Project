package gamingplatform.controller;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import gamingplatform.view.FreemarkerHelper;

public class Index extends HttpServlet {


    //container dati che sarà processato da freemarker
    private Map<String, Object> data = new HashMap<>();


    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        FreemarkerHelper.process("index.ftl", data, response, getServletContext());

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        process(request, response);

    }

    @Override
    protected  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        process(request, response);
    }
}