package com.programmers.pcquotation.domain.estimate.entity;

import com.programmers.pcquotation.domain.estimaterequest.entity.EstimateRequest;
import com.programmers.pcquotation.domain.sellers.entitiy.Sellers;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Estimate {
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

    @OneToMany(mappedBy = "estimate", cascade = CascadeType.REMOVE)
    private List<EstimateComment> estimateComments;

//    @PrePersist
//    @PreUpdate
//    public void calculateTotalPrice() {
//    }
}
