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
                .body(new RsData("400", "입력한 내용을 다시 확인해주세요"));
    }
    @ExceptionHandler(ServerException.class)
    public ResponseEntity<RsData> handleException(ServerException e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new RsData("500", "인터넷 상태를 확인해주세요"));
    }
}