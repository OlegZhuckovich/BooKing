package com.epam.zhuckovich.customtag;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class InfoTag extends TagSupport{

    @Override
    public int doStartTag(){
        try {
            JspWriter jspWriter = pageContext.getOut();
            jspWriter.write("\"dom\": '<\"toolbar\">frtip',\n" +
                    "                \"scrollX\": true,\n" +
                    "                \"lengthMenu\": [[5], [5]],\n" +
                    "                \"pagingType\": \"numbers\",\n" +
                    "                columnDefs: [\n" +
                    "                    {\n" +
                    "                        targets: [ 0, 1, 2 ],\n" +
                    "                        className: 'mdl-data-table__cell--non-numeric'\n" +
                    "                    }\n" +
                    "                ]\n" +
                    "            });\n" +
                    "            $(\"#textField\").focusout(function(){ $(this).css(\"color\", \"white\"); });\n" +
                    "            $(\"#bookTitle\").focus(function () {\n" +
                    "                $(\"#submitButton\").css(\"margin-top\", \"10px\");\n" +
                    "            });\n" +
                    "            $(\"div.toolbar\").html('');\n" +
                    "        } );\n" +
                    "    </script>\n" +
                    "    <style>\n" +
                    "        #example_filter{\n" +
                    "            margin-bottom: 1%;\n" +
                    "            margin-right: 30px;\n" +
                    "        }\n" +
                    "        #example_filter label{\n" +
                    "            color:white;\n" +
                    "        }\n" +
                    "        .mdl-button{\n" +
                    "            color: white;\n" +
                    "        }\n" +
                    "        #example_info{\n" +
                    "            color:white;\n" +
                    "        }\n" +
                    "        .btn dropdown-toggle btn-default{\n" +
                    "            margin-top:0;\n" +
                    "        }\n" +
                    "        table.dataTable thead .sorting,\n" +
                    "        table.dataTable thead .sorting_asc,\n" +
                    "        table.dataTable thead .sorting_desc {\n" +
                    "            background : none;\n" +
                    "        }\n" +
                    "        form .question input:valid {\n" +
                    "            margin-top: 10px;\n" +
                    "        }\n" +
                    "        form .question input:focus {\n" +
                    "            outline: none;\n" +
                    "            background: white;\n" +
                    "            color: #33c4ba;\n" +
                    "            margin-top: 10px;\n" +
                    "        }\n" +
                    "        .mdl-button--raised.mdl-button--colored{\n" +
                    "        }\n" +
                    "        .mdl-button--raised.mdl-button--colored:hover{\n" +
                    "        }\n" +
                    "    </style>"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag(){
        return EVAL_PAGE;
    }

}
