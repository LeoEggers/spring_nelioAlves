package com.nelio.spring.course.services;

import com.nelio.spring.course.entities.OrderItem;
import com.nelio.spring.course.repositories.OrderItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    public OrderItem findById(Long id) {
        Optional<OrderItem> obj = orderItemRepository.findById(id);
        return obj.orElse(null);
    }
}
