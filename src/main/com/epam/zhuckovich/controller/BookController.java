package com.epam.zhuckovich.controller;

import com.epam.zhuckovich.dao.BookDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


@MultipartConfig
@WebServlet("/bookController")
public class BookController extends HttpServlet{

    private static final String SQL_FIND = "SELECT book_content FROM book_content WHERE bookID = ?";


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookID = request.getParameter("bookID");
        BookDAO bookDAO = BookDAO.getInstance();
        byte[] bookFile = bookDAO.loadBook(Integer.parseInt(bookID),SQL_FIND);
        if(bookFile!=null){
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition","inline");
            response.setContentLength(bookFile.length);
            OutputStream output = response.getOutputStream();
            output.write(bookFile);
            output.flush();
        } else {
            response.sendError(404, "File not found");
        }
    }
}
