package com.epam.zhuckovich.customtag;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class InfoTag extends TagSupport{

    private static final Logger LOGGER = LogManager.getLogger(InfoTag.class);

    @Override
    public int doStartTag(){
        try {
            JspWriter jspWriter = pageContext.getOut();
            jspWriter.write("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>\n" +
                    "    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>\n" +
                    "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js\" type=\"text/javascript\"></script>\n" +
                    "    <script src=\"https://unpkg.com/sweetalert/dist/sweetalert.min.js\"></script>");
        } catch (IOException e) {
            LOGGER.log(Level.ERROR,"IOException occurred while processing the custom tag");
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag(){
        return EVAL_PAGE;
    }

}
