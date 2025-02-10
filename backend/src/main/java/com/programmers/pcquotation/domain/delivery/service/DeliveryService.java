package com.programmers.pcquotation.domain.delivery.service;

import com.programmers.pcquotation.domain.delivery.entity.Delivery;
import com.programmers.pcquotation.domain.delivery.entity.DeliveryDto;
import com.programmers.pcquotation.domain.delivery.entity.DeliveryStatus;
import com.programmers.pcquotation.domain.delivery.exception.NullEntityException;
import com.programmers.pcquotation.domain.delivery.repository.DeliveryRepository;
import com.programmers.pcquotation.domain.estimate.repository.EstimateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final EstimateRepository estimateRepository;

    public void create(String address, Integer id) {
        deliveryRepository.save(Delivery
                .builder()
                    .address(address)
                    .status(DeliveryStatus.ORDER_COMPLETED)
                    .estimate(estimateRepository.findById(id).orElseThrow(NullEntityException::new))
                .build());
    }

    public List<DeliveryDto> findAll() {
        if (!deliveryRepository.findAll().isEmpty()){
            return deliveryRepository
                    .findAll()
                    .stream()
                    .map(DeliveryDto::new).toList();
        }else throw new NullEntityException();
    }

    public DeliveryDto findOne(Integer id) {
        return deliveryRepository
                .findById(id)
                .stream()
                .map(DeliveryDto::new)
                .findAny()
                .orElseThrow(NullEntityException::new);
    }

    public void delete(Integer id) {
        deliveryRepository
                .delete(deliveryRepository.findById(id)
                                .orElseThrow(NullEntityException::new));
    }

    @Transactional
    public void modify(Integer id, String address) {
        deliveryRepository
                .findById(id)
                .orElseThrow(NullEntityException::new)
                .updateAddress(address);
    }
}
