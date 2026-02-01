package com.esparta.guru_02.exceptions;

/*
 * Author: M
 * Date: 31-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 */
public class BadRequestException extends RuntimeException {


    /**
     * Construct a new BadRequestException with no other information.
     */
    public BadRequestException() {
        super();
    }


    /**
     * Construct a new BadRequestException for the specified message.
     *
     * @param message Message describing this exception
     */
    public BadRequestException(String message) {
        super(message);
    }


    /**
     * Construct a new BadRequestException for the specified throwable.
     *
     * @param throwable Throwable that caused this exception
     */
    public BadRequestException(Throwable throwable) {
        super(throwable);
    }


    /**
     * Construct a new BadRequestException for the specified message and throwable.
     *
     * @param message   Message describing this exception
     * @param throwable Throwable that caused this exception
     */
    public BadRequestException(String message, Throwable throwable) {
        super(message, throwable);
    }}
