package com.epam.zhuckovich.exception;

public class SQLTechnicalException extends Exception {

    public SQLTechnicalException() { super(); }

    public SQLTechnicalException(String message){
        super(message);
    }

    public SQLTechnicalException(Throwable e){
        super(e);
    }

    public SQLTechnicalException(String message, Throwable e){
        super(message,e);
    }

}
