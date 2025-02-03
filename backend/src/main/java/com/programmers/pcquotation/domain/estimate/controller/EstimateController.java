package com.programmers.pcquotation.domain.estimate.controller;

import com.programmers.pcquotation.domain.estimate.service.EstimateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/estimates")
public class EstimateController {
    private final EstimateService estimateService;

    //견적 다건 조회

    //견적 단건 조회

    //견적 생성

    //견적 수정

    //견적 삭제

    //견적 댓글 조회

    //견적 댓글 작성

    //견적 댓글 수정

    //견적 댓글 삭제
}
