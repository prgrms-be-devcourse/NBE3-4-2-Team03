package com.programmers.pcquotation;

import com.programmers.pcquotation.estimaterequest.entity.EstimateRequest;
import com.programmers.pcquotation.estimaterequest.repository.EstimateRequestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class PcquotationApplicationTests {
    @Autowired
    public EstimateRequestRepository estimateRequestRepository;

    @Test
    void contextLoads() {
        estimateRequestRepository.save(EstimateRequest
                .builder()
                .purpose("qwe")
                .budget(100)
                .otherRequest("asd")
                .createDate(LocalDateTime.now())
                .build());


        assertThat(estimateRequestRepository.findAll().size()).isEqualTo(1);
    }

}
