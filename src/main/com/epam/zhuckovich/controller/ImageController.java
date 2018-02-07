package com.epam.zhuckovich.controller;

import com.epam.zhuckovich.command.CommandFactory;
import com.epam.zhuckovich.dao.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig
@WebServlet("/image/*")
public class ImageController extends HttpServlet {

    private static final String COMMAND_PARAMETER = "command";
    private static final String SQL_FIND = "SELECT photo FROM author WHERE name = ?";

    public void init(){
//        ConnectionPool.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imageName = request.getPathInfo().substring(1);
        System.out.println("параметр " + imageName);
        UserDAO userDAO  = UserDAO.getInstance();
        byte[] content = userDAO.loadImage(imageName);
            if(content!=null){
                response.setContentType(getServletContext().getMimeType(imageName));
                response.setContentLength(content.length);
                response.getOutputStream().write(content);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);

        /*ProxyConnection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet userResultSet = null;
            userResultSet = statement.executeQuery("SELECT photo FROM LibraryDatabase.author WHERE authorID = 3");
            Blob image = userResultSet.getBlob("photo");
            byte[ ] imgData = null ;
            imgData = image.getBytes(1,(int)image.length());
            String imgDataBase64=new String(Base64.getEncoder().encode(imgData));
            request.setAttribute("testImage",imgDataBase64);
            RequestDispatcher dispatcher = request.getRequestDispatcher(PageManager.getPage("loginPage"));
            dispatcher.forward(request, response);
        } catch(SQLException e){
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getParameter(COMMAND_PARAMETER ));
        Router page = CommandFactory.defineCommand(request.getParameter(COMMAND_PARAMETER),request).execute(request);
        System.out.println(page.getPage());
        switch(page.getRouterType()){
            case FORWARD:
                RequestDispatcher dispatcher = request.getRequestDispatcher(page.getPage());
                dispatcher.forward(request, response);
                System.out.println("форвард");
                break;
            case REDIRECT:
                response.sendRedirect(page.getPage());
                System.out.println("редирект");
                break;
        }
    }
}
