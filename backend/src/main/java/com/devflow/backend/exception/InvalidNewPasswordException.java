package com.devflow.backend.exception;

public class InvalidNewPasswordException extends RuntimeException {
    public  InvalidNewPasswordException(String message){
        super(message);
    }
}
