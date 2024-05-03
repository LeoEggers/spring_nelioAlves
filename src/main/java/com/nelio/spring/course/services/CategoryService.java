package com.nelio.spring.course.services;

import com.nelio.spring.course.entities.Category;
import com.nelio.spring.course.repositories.CategoryRepository;
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
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Category findCategoryById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(id));
            categoryRepository.deleteById(category.getId());
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Category updateCategory(Long id, Category newCategory) {
        try {
            Category existingCategory = categoryRepository.getReferenceById(id);
            copyCategoryData(existingCategory, newCategory);
            return categoryRepository.save(existingCategory);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void copyCategoryData(Category existingCategory, Category newCategory) {
        existingCategory.setName(newCategory.getName());
    }
}
