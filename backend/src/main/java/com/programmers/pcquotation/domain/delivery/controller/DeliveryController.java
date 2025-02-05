package com.programmers.pcquotation.domain.delivery.controller;

import com.programmers.pcquotation.domain.delivery.entity.Delivery;
import com.programmers.pcquotation.domain.delivery.service.DeliveryService;
import com.programmers.pcquotation.domain.estimate.entity.Estimate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery")
public class DeliveryController {
    private final DeliveryService deliveryService;
    record DeliveryCreateRequest(@NotBlank String address){}

    @GetMapping
    public List<Delivery> getDeliveryList(){
        return deliveryService.findAll();
    }

    @GetMapping("/{id}")
    public Delivery getDeliveryDetail(@PathVariable Integer id){
        return deliveryService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<String> createDelivery(
            @RequestBody @Valid DeliveryCreateRequest deliveryCreateRequest,
            @RequestParam Estimate estimate){
        deliveryService.create(deliveryCreateRequest.address, estimate);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("주문이 완료되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDelivery(@PathVariable Integer id){
        deliveryService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("주문이 취소되었습니다.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modifyDelivery(
            @PathVariable Integer id,
            @RequestBody @Valid DeliveryCreateRequest deliveryCreateRequest){
        deliveryService.modify(id, deliveryCreateRequest.address);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("주문이 수정되었습니다.");
    }
}
