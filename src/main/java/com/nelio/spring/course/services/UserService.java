package com.nelio.spring.course.services;

import com.nelio.spring.course.entities.User;
import com.nelio.spring.course.repositories.UserRepository;
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
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(id));
            userRepository.deleteById(user.getId());
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public User updateUser(Long id, User newUser) {
        try {
            User existingUser = userRepository.getReferenceById(id);
            copyUserData(existingUser, newUser);
            return userRepository.save(existingUser);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void copyUserData(User existingUser, User newUser) {
        existingUser.setUsername(newUser.getUsername());
        existingUser.setEmail(newUser.getEmail());
        existingUser.setPhone(newUser.getPhone());
    }
}
