package com.finance.tracker.service;

import com.finance.tracker.entity.Transaction;
import com.finance.tracker.entity.TransactionType;
import com.finance.tracker.entity.User;
import com.finance.tracker.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public List<Transaction> getAllForUser(User user) {
        return transactionRepository.findByUserOrderByDateDesc(user);
    }

    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public void delete(Long id, User user) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new SecurityException("You cannot delete this transaction");
        }
        transactionRepository.delete(transaction);
    }

    public BigDecimal totalIncome(User user, LocalDate from, LocalDate to) {
        return transactionRepository.sumByType(user, TransactionType.INCOME, from, to);
    }

    public BigDecimal totalExpense(User user, LocalDate from, LocalDate to) {
        return transactionRepository.sumByType(user, TransactionType.EXPENSE, from, to);
    }
}
