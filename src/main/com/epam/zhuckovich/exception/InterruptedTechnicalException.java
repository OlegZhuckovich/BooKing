package com.epam.zhuckovich.exception;

public class InterruptedTechnicalException extends Exception {

    public InterruptedTechnicalException() { super(); }

    public InterruptedTechnicalException(String message){
        super(message);
    }

    public InterruptedTechnicalException(Exception e){
        super(e);
    }

    public InterruptedTechnicalException(String message, Exception e){
        super(message,e);
    }

}
