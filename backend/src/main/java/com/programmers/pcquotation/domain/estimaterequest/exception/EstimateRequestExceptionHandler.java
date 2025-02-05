package com.programmers.pcquotation.domain.estimaterequest.exception;

import com.programmers.pcquotation.domain.estimaterequest.dto.RsData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EstimateRequestExceptionHandler {
    @ExceptionHandler(NullEntityException.class)
    public ResponseEntity<RsData> handleException(NullEntityException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new RsData("주문이 존재하지 않습니다"));
    }
}