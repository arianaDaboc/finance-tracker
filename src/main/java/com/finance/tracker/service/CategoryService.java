package com.finance.tracker.service;

import com.finance.tracker.entity.Category;
import com.finance.tracker.entity.User;
import com.finance.tracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllForUser(User user) {
        return categoryRepository.findByUser(user);
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public void delete(Long id, User user) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        if (!category.getUser().getId().equals(user.getId())) {
            throw new SecurityException("You cannot delete this category");
        }
        categoryRepository.delete(category);
    }
}
