package com.programmers.pcquotation.estimate.entity;

import com.programmers.pcquotation.estimaterequest.entity.EstimateRequest;
import com.programmers.pcquotation.sellers.entitiy.Sellers;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class estimate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime createDate;

    @Column(columnDefinition = "Integer")
    private Integer totalPrice;

    @OneToOne
    private EstimateRequest estimateRequest;

    @OneToOne
    private Sellers sellers;
}
