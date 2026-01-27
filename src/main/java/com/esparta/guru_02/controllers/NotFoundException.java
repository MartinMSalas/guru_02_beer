package com.esparta.guru_02.controllers;

/*
 * Author: m
 * Date: 27/1/26
 * Project Name: guru_02_beer
 * Description: beExcellent
 */
public class NotFoundException extends RuntimeException{

    public NotFoundException() {

    }

    public NotFoundException(String message){
        super(message);
    }

    public NotFoundException(String message, Throwable cause){
        super(message, cause);
    }

    public NotFoundException(Throwable cause){
        super(cause);
    }

    public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
