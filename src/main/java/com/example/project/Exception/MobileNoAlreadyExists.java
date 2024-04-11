package com.example.project.Exception;

public class MobileNoAlreadyExists extends RuntimeException {
    String message;

    public MobileNoAlreadyExists(String message) {
        super(message);
        this.message = message;
    }
}



