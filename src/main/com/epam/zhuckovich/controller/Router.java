package com.epam.zhuckovich.controller;

/**
 * <p>A class that contains information about the page to be migrated to
 * and the type of transition</p>
 * @author      Oleg Zhuckovich
 * @version     %I%, %G%
 * @since       1.0
 */

public class Router {

    private RouterType routerType;
    private String page;

    /**
     * Enum that contains transition types
     */

    public enum RouterType {
        FORWARD, REDIRECT
    }

    /**
     * Class constructor with two parameters
     * @param routerType contains type of the transition
     * @param page       the page to which the transition will be made
     */

    public Router (RouterType routerType, String page) {
        this.routerType = routerType;
        this.page = page;
    }

    /**
     * <p>Return the type of the Router transition</p>
     * @return the type of the Router transition
     */

    RouterType getRouterType(){ return routerType; }

    /**
     * <p>Return the page of the Router</p>
     * @return the page of the Router
     */

    public String getPage(){
        return page;
    }

}
