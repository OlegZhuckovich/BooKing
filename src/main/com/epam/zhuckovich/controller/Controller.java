package com.epam.zhuckovich.controller;

import com.epam.zhuckovich.command.CommandFactory;
import com.epam.zhuckovich.connection.ConnectionPool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/controller")
@MultipartConfig
public class Controller extends HttpServlet {

    private static final String COMMAND_PARAMETER = "command";

    public void init(){
        ConnectionPool.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getParameter(COMMAND_PARAMETER));
        Router page = CommandFactory.defineCommand(request.getParameter(COMMAND_PARAMETER).toUpperCase(),request).execute(request);
        System.out.println(page.getPage());
        switch(page.getRouterType()){
            case FORWARD:
                RequestDispatcher dispatcher = request.getRequestDispatcher(page.getPage());
                dispatcher.forward(request, response);
                System.out.println("форвард");
                break;
            case REDIRECT:
                response.sendRedirect(page.getPage());
                break;
        }
    }

    public void destroy(){
        ConnectionPool.getInstance().closePool();
    }

}
