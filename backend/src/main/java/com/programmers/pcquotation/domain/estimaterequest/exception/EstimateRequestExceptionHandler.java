package com.programmers.pcquotation.domain.estimaterequest.exception;

import com.programmers.pcquotation.domain.estimaterequest.dto.RsData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EstimateRequestExceptionHandler {
    @ExceptionHandler(EntityNullException.class)
    public ResponseEntity<RsData> handleException(EntityNullException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new RsData("400", "입력한 내용을 다시 확인해주세요"));
    }
}
