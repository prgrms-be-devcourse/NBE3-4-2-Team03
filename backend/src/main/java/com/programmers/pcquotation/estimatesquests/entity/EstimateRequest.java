package com.programmers.pcquotation.estimatesquests.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstimateRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String purpose;

    @Column(columnDefinition = "Integer")
    private Integer budget;

    LocalDateTime createDate;

    @Column(columnDefinition = "TEXT")
    private String otherRequests;
}
