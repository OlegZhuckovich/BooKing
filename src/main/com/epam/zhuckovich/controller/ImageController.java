package com.epam.zhuckovich.controller;

import com.epam.zhuckovich.dao.UserDAO;

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imageName = request.getPathInfo().substring(1);
        UserDAO userDAO  = UserDAO.getInstance();
        byte[] content = userDAO.loadImage(imageName);
            if(content!=null){
                response.setContentType(getServletContext().getMimeType(imageName));
                response.setContentLength(content.length);
                response.getOutputStream().write(content);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
    }

}
