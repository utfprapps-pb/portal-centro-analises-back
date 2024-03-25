package com.portal.centro.API.exceptions;

public class GenericException extends Exception {

    private final boolean mapped = true;

    public GenericException(String message) {
        super(message);
    }

}
