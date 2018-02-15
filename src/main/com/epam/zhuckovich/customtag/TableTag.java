package com.epam.zhuckovich.customtag;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class TableTag extends TagSupport {

    private static final Logger LOGGER = LogManager.getLogger(TableTag.class);

    private String userName;
    private String userSurname;
    private String userEmail;
    private String bookTitle;
    private String bookGenre;
    private String bookPublishingHouse;
    private String bookYear;
    private String bookPages;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setBookGenre(String bookGenre) {
        this.bookGenre = bookGenre;
    }

    public void setBookPublishingHouse(String bookPublishingHouse) {
        this.bookPublishingHouse = bookPublishingHouse;
    }

    public void setBookYear(String bookYear) {
        this.bookYear = bookYear;
    }

    public void setBookPages(String bookPages) {
        this.bookPages = bookPages;
    }


    @Override
    public int doStartTag() throws JspException {

        try {
            JspWriter jspWriter = pageContext.getOut();
            jspWriter.write("<td>" + userName + "</td>\n" +
                                 "<td>" + userSurname + "</td>\n" +
                                 "<td>" + userEmail + "</td>\n" +
                                 "<td>" + bookTitle + "</td>\n" +
                                 "<td>" + bookGenre + "</td>\n" +
                                 "<td>" + bookPublishingHouse + "</td>\n" +
                                 "<td>" + bookYear + "</td>\n" +
                                 "<td>" + bookPages + "</td>");
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "IOException occurred while processing the custom tag");
        }
        return SKIP_BODY;

    }

}
