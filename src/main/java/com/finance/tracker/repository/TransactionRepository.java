package com.finance.tracker.repository;

import com.finance.tracker.entity.Transaction;
import com.finance.tracker.entity.TransactionType;
import com.finance.tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserOrderByDateDesc(User user);

    // Returns category name and aggregate amount for one user, type, and date range.
    @Query("""
            SELECT c.name, SUM(t.amount)
            FROM Transaction t JOIN t.category c
            WHERE t.user = :user AND t.type = :type AND t.date BETWEEN :from AND :to
            GROUP BY c.name
            ORDER BY SUM(t.amount) DESC
            """)
    List<Object[]> sumByCategory(@Param("user") User user,
                                  @Param("type") TransactionType type,
                                  @Param("from") LocalDate from,
                                  @Param("to") LocalDate to);

    // Returns the aggregate amount for one transaction type and date range.
    @Query("""
            SELECT COALESCE(SUM(t.amount), 0)
            FROM Transaction t
            WHERE t.user = :user AND t.type = :type AND t.date BETWEEN :from AND :to
            """)
    BigDecimal sumByType(@Param("user") User user,
                          @Param("type") TransactionType type,
                          @Param("from") LocalDate from,
                          @Param("to") LocalDate to);
}
