package com.nelio.spring.course.repositories;

import com.nelio.spring.course.entities.Order;
import com.nelio.spring.course.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
