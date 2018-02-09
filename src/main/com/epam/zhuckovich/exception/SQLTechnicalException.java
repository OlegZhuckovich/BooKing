package com.epam.zhuckovich.exception;

public class SQLTechnicalException extends Exception {

    public SQLTechnicalException() { super(); }

    public SQLTechnicalException(String message){
        super(message);
    }

    public SQLTechnicalException(Exception e){
        super(e);
    }

    public SQLTechnicalException(String message, Exception e){
        super(message,e);
    }

}
