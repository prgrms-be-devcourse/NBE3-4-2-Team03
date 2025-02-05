package com.programmers.pcquotation.domain.delivery.service;

import com.programmers.pcquotation.domain.delivery.entity.Delivery;
import com.programmers.pcquotation.domain.delivery.entity.DeliveryStatus;
import com.programmers.pcquotation.domain.delivery.exception.NullEntityException;
import com.programmers.pcquotation.domain.delivery.repository.DeliveryRepository;
import com.programmers.pcquotation.domain.estimate.entity.Estimate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    public void create(String address, Estimate estimate) {
        deliveryRepository.save(Delivery
                .builder()
                .address(address)
                .status(DeliveryStatus.ORDER_COMPLETED)
                .estimate(estimate)
                .build());
    }

    public List<Delivery> findAll() {
        if (!deliveryRepository.findAll().isEmpty()){
            return deliveryRepository.findAll();
        }else throw new NullEntityException();
    }

    public Delivery findOne(Integer id) {
        return deliveryRepository.findById(id).orElseThrow(NullEntityException::new);
    }

    public void delete(Integer id) {
        deliveryRepository.delete(findOne(id));
    }

    public void modify(Integer id, String address) {
        Delivery delivery = findOne(id);
        deliveryRepository.save(new Delivery(
                delivery.getId(),
                delivery.getEstimate(),
                delivery.getStatus(),
                address));
    }
}
