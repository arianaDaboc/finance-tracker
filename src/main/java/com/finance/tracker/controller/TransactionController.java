package com.finance.tracker.controller;

import com.finance.tracker.entity.Transaction;
import com.finance.tracker.entity.TransactionType;
import com.finance.tracker.entity.User;
import com.finance.tracker.service.CategoryService;
import com.finance.tracker.service.TransactionService;
import com.finance.tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final CategoryService categoryService;
    private final UserService userService;

    private User currentUser(Authentication authentication) {
        return userService.getByUsername(authentication.getName());
    }

    @GetMapping
    public String list(Model model, Authentication authentication) {
        User user = currentUser(authentication);
        model.addAttribute("transactions", transactionService.getAllForUser(user));
        model.addAttribute("categories", categoryService.getAllForUser(user));

        Transaction newTransaction = new Transaction();
        newTransaction.setDate(LocalDate.now());
        model.addAttribute("newTransaction", newTransaction);
        model.addAttribute("types", TransactionType.values());
        return "transactions";
    }

    @PostMapping
    public String create(@ModelAttribute("newTransaction") Transaction transaction, Authentication authentication) {
        User user = currentUser(authentication);
        transaction.setUser(user);
        transactionService.save(transaction);
        return "redirect:/transactions";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, Authentication authentication) {
        transactionService.delete(id, currentUser(authentication));
        return "redirect:/transactions";
    }
}
