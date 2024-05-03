package com.nelio.spring.course.repositories;

import com.nelio.spring.course.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
