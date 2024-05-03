package com.nelio.spring.course.repositories;

import com.nelio.spring.course.entities.Product;
import com.nelio.spring.course.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
