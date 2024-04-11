package com.example.project.Exception;

public class UserNotFound extends RuntimeException {
    String message;

    public UserNotFound(String message) {
        super(message);
        this.message = message;
    }
}
