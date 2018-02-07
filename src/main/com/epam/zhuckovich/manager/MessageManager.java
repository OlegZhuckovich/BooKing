package com.epam.zhuckovich.manager;

import java.util.Locale;
import java.util.ResourceBundle;

public enum MessageManager {
    EN(ResourceBundle.getBundle("resources.message",new Locale("en","US"))),
    RU(ResourceBundle.getBundle("resources.message",new Locale("ru","RU")));

    private ResourceBundle bundle;

    MessageManager(ResourceBundle bundle){
        this.bundle=bundle;
    }

    public String getMessage(String key){
        return bundle.getString(key);
    }
}
