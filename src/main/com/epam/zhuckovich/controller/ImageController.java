package com.epam.zhuckovich.controller;

import com.epam.zhuckovich.dao.UserDAO;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig
@WebServlet("/image/*")
public class ImageController extends HttpServlet {

    private static final String AUTHOR = "author";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var imageName = request.getPathInfo().substring(1);
        byte[] content;
        if(imageName.contains(AUTHOR)){
            var authorID = imageName.substring(6);
            content = UserDAO.getInstance().loadImage(authorID, 0);
        } else {
            content = UserDAO.getInstance().loadImage(imageName, 1);
        }

        if (content != null) {
            response.setContentType(getServletContext().getMimeType(imageName));
            response.setContentLength(content.length);
            response.getOutputStream().write(content);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
