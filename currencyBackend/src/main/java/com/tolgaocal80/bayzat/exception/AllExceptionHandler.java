package com.tolgaocal80.bayzat.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@ControllerAdvice
public class AllExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerRuntimeException(HttpServletRequest request, Throwable throwable){
        var map = new HashMap<>();
        map.put("error", throwable.getMessage());
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnsupportedCurrencyCreationException.class)
    public ResponseEntity<?> unsupportedCoinExceptionHandler(HttpServletRequest request, Throwable throwable){
        var map = new HashMap<>();
        map.put("error", throwable.getMessage());
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalExceptionHandler(HttpServletRequest request, Throwable throwable){
        var map = new HashMap<>();
        map.put("error", throwable.getMessage());
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }





}
