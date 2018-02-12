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
public class BookController extends HttpServlet {

    private static final String APPLICATION_PDF = "application/pdf";
    private static final String BOOK_ID = "bookID";
    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    private static final String FILE_NOT_FOUND = "File not found";
    private static final String INLINE = "inline";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookID = request.getParameter(BOOK_ID);
        byte[] bookFile = BookDAO.getInstance().loadBook(Integer.parseInt(bookID));
        if(bookFile!=null){
            response.setContentType(APPLICATION_PDF);
            response.setHeader(CONTENT_DISPOSITION,INLINE);
            response.setContentLength(bookFile.length);
            OutputStream output = response.getOutputStream();
            output.write(bookFile);
            output.flush();
        } else {
            response.sendError(404, FILE_NOT_FOUND);
        }
    }
}
