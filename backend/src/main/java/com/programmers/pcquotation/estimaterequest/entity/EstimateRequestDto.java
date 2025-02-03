package com.programmers.pcquotation.estimaterequest.entity;

import jakarta.persistence.Column;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EstimateRequestDto {
    private Integer id;
    private String purpose;
    private Integer budget;
    private String otherRequest;
    LocalDateTime createDate;
//    private Customer customer;

    public EstimateRequestDto(EstimateRequest estimateRequest){
        this.id = estimateRequest.getId();
        this.purpose = estimateRequest.getPurpose();
        this.budget = estimateRequest.getBudget();
        this.otherRequest = estimateRequest.getOtherRequest();
        this.createDate = estimateRequest.getCreateDate();
        //this.customer = estimateRequest.getCustomer();
    }
}
