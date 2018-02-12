package com.epam.zhuckovich.manager;

import java.util.ResourceBundle;

/**
 * <p>A class that communicates with ResourceBundle that contains jsp paths.</p>
 * @author      Oleg Zhuckovich
 * @version     %I%, %G%
 * @since       1.0
 */

public class PageManager {

    private static final String JSP = "jsp";

    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle(JSP);
    private PageManager() { }

    /**
     * <p>Returns the path to jsp by key</p>
     * @param key key value
     * @return    the path of page
     */

    public static String getPage(String key) {
            return resourceBundle.getString(key);
    }
}
