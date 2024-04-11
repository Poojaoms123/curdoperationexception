package com.example.project.Exception;

public class EmailAlreadyExists extends RuntimeException{
    String message;

    public EmailAlreadyExists(String message) {
        super(message);
        this.message = message;
    }
}
