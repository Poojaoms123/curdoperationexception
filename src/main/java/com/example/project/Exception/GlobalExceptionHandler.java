package com.example.project.Exception;

import com.example.project.Response.EntityResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExists.class)
    public ResponseEntity<?> EmailAlreadyExists(Exception e){
        return  new ResponseEntity<>(new EntityResponse(e.getMessage(),-1), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MobileNoAlreadyExists.class)
    public ResponseEntity<?>MobileNoAlreadyExists(Exception e){
        return new ResponseEntity<>(new EntityResponse(e.getMessage(),-1),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CollegeNotFound.class)
    public ResponseEntity<?>CollegeNotFound(Exception e){
        return new ResponseEntity<>(new EntityResponse(e.getMessage(),-1),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<?>UserNotFound(Exception e){
        return new ResponseEntity<>(new EntityResponse(e.getMessage(),-1),HttpStatus.NOT_FOUND);
    }
}
