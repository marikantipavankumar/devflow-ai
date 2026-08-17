package com.devflow.backend.exception;

public class InvalidCurrentPasswordException extends RuntimeException{
    public InvalidCurrentPasswordException(String message){
        super(message);
    }

}
