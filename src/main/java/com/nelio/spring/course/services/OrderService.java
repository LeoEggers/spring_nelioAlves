package com.nelio.spring.course.services;

import com.nelio.spring.course.entities.Order;
import com.nelio.spring.course.repositories.OrderRepository;
import com.nelio.spring.course.services.exceptions.DatabaseException;
import com.nelio.spring.course.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Order findOrderById(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        return optionalOrder.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        try {
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(id));
            orderRepository.deleteById(order.getId());
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Order updateOrder(Long id, Order newOrder) {
        try {
            Order existingOrder = orderRepository.getReferenceById(id);
            copyOrderData(existingOrder, newOrder);
            return orderRepository.save(existingOrder);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void copyOrderData(Order existingOrder, Order newOrder) {
        existingOrder.setOrderStatus(newOrder.getOrderStatus());
    }
}
