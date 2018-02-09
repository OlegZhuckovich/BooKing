package com.epam.zhuckovich.customtag;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class InfoTag extends TagSupport{

    private static final Logger LOGGER = LogManager.getLogger(InfoTag.class);

    private String role;

    public void setRole(String role){
        this.role = role;
    }

    @Override
    public int doStartTag(){
        try {
            JspWriter jspWriter = pageContext.getOut();
            jspWriter.write("<script>\n" +
                    "        $(document).ready(function () {\n" +
                    "            $('#example').DataTable( {\"language\": {\n" +
                    "                \"zeroRecords\": '<fmt:message key=\"tableZeroRecords\" bundle=\"${booking}\"/>',\n" +
                    "                \"info\": '<fmt:message key=\"tableInfo\" bundle=\"${booking}\"/>',\n" +
                    "                \"infoEmpty\": '<fmt:message key=\"tableInfoEmpty\" bundle=\"${booking}\"/>',\n" +
                    "                \"infoFiltered\": '<fmt:message key=\"tableInfoFiltered\" bundle=\"${booking}\"/>',\n" +
                    "                \"search\":'<fmt:message key=\"tableSearch\" bundle=\"${booking}\"/>'\n" +
                    "            },\n" +
                    "                \"dom\": '<\"toolbar\">frtip',\n" +
                    "                \"scrollX\": true,\n" +
                    "                \"lengthMenu\": [[5], [5]],\n" +
                    "                \"pagingType\": \"numbers\",\n" +
                    "                columnDefs: [\n" +
                    "                    {\n" +
                    "                        targets: '_all',\n" +
                    "                        className: 'mdl-data-table__cell--non-numeric'\n" +
                    "                    }\n" +
                    "                ]\n" +
                    "            });\n" +
                    "        });\n" +
                    "    </script>");
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
