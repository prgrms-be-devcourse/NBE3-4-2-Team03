package com.programmers.pcquotation.domain.estimaterequest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class RsData {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String recode;
    private String message;

    public RsData(String message){
        this.message = message;
    }
}
