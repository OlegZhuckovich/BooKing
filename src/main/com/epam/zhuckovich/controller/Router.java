package com.epam.zhuckovich.controller;

public class Router {

    private RouterType routerType;
    private String page;

    public enum RouterType {
        FORWARD, REDIRECT
    }

    public Router (RouterType routerType, String page) {
        this.routerType = routerType;
        this.page = page;
    }

    public void setRouterType(RouterType routerType){ this.routerType = routerType; }

    public void setPagePath(String page){
        this.page = page;
    }

    public RouterType getRouterType(){ return routerType; }

    public String getPage(){
        return page;
    }

}
