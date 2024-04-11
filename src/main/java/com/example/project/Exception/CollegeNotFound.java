package com.example.project.Exception;

public class CollegeNotFound extends RuntimeException {
    String mesaage;

    public CollegeNotFound(String message) {
        super(message);
        this.mesaage = message;
    }


}
