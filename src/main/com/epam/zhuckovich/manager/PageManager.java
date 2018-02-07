package com.epam.zhuckovich.manager;

import java.util.ResourceBundle;

public class PageManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("jsp");
    private PageManager() { }
    public static String getPage(String key) {
            return resourceBundle.getString(key);
    }
}
