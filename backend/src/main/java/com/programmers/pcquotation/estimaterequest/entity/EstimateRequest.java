package com.programmers.pcquotation.estimaterequest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstimateRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20)
    private String purpose;

    @Column(columnDefinition = "INTEGER")
    private Integer budget;

    @Column(length = 200)
    private String otherRequest;

    LocalDateTime createDate;

//    @OneToMany
//    private Customer customer;
}
