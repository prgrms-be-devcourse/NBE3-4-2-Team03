package com.programmers.pcquotation.estimatesquests.estimaterequestdto;

import com.programmers.pcquotation.estimatesquests.entity.EstimateRequest;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EstimateRequestDto {
    private Integer id;
    private String purpose;
    private Integer budget;
    LocalDateTime createDate;
    private String otherRequests;

    public EstimateRequestDto(EstimateRequest estimateRequest){
        this.id = estimateRequest.getId();
        this.purpose = estimateRequest.getPurpose();
        this.budget = estimateRequest.getBudget();
        this.createDate = estimateRequest.getCreateDate();
        this.otherRequests = estimateRequest.getOtherRequests();
    }
}
