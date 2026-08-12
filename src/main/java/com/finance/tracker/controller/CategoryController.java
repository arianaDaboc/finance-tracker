package com.finance.tracker.controller;

import com.finance.tracker.entity.Category;
import com.finance.tracker.entity.TransactionType;
import com.finance.tracker.entity.User;
import com.finance.tracker.service.CategoryService;
import com.finance.tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;

    private User currentUser(Authentication authentication) {
        return userService.getByUsername(authentication.getName());
    }

    @GetMapping
    public String list(Model model, Authentication authentication) {
        User user = currentUser(authentication);
        model.addAttribute("categories", categoryService.getAllForUser(user));
        model.addAttribute("newCategory", new Category());
        model.addAttribute("types", TransactionType.values());
        return "categories";
    }

    @PostMapping
    public String create(@ModelAttribute Category newCategory, Authentication authentication) {
        User user = currentUser(authentication);
        newCategory.setUser(user);
        categoryService.save(newCategory);
        return "redirect:/categories";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, Authentication authentication) {
        categoryService.delete(id, currentUser(authentication));
        return "redirect:/categories";
    }
}
