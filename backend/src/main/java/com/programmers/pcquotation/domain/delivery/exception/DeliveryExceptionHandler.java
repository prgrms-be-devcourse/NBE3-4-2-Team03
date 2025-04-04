package com.programmers.pcquotation.domain.delivery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Comparator;
import java.util.stream.Collectors;

@ControllerAdvice
public class DeliveryExceptionHandler {
    @ExceptionHandler(NullEntityException.class)
    public ResponseEntity<String> handleException(NullEntityException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("다시 실행해주세요");
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handle(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .filter(error -> error instanceof FieldError)
                .map(error -> (FieldError) error)
                .map(error -> error.getField() + "-" +  error.getDefaultMessage())
                .sorted(Comparator.comparing(String::toString))
                .collect(Collectors.joining("\n"));
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}