package com.finance.tracker.controller;

import com.finance.tracker.entity.Transaction;
import com.finance.tracker.entity.User;
import com.finance.tracker.service.TransactionService;
import com.finance.tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final TransactionService transactionService;
    private final UserService userService;

    @GetMapping("/")
    public String root() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        User user = userService.getByUsername(authentication.getName());

        // The current month is the default range for summary data and charts.
        YearMonth currentMonth = YearMonth.now();
        LocalDate from = currentMonth.atDay(1);
        LocalDate to = currentMonth.atEndOfMonth();

        BigDecimal income = transactionService.totalIncome(user, from, to);
        BigDecimal expense = transactionService.totalExpense(user, from, to);
        List<Transaction> transactions = transactionService.getAllForUser(user);

        model.addAttribute("income", income);
        model.addAttribute("expense", expense);
        model.addAttribute("balance", income.subtract(expense));
        model.addAttribute("month", currentMonth);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("transactionCount", transactions.size());
        model.addAttribute("recentTransactions", transactions.stream().limit(5).toList());

        return "dashboard";
    }
}
