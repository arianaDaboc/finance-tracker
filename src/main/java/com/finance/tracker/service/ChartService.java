package com.finance.tracker.service;

import com.finance.tracker.entity.TransactionType;
import com.finance.tracker.entity.User;
import com.finance.tracker.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChartService {

    private final TransactionRepository transactionRepository;

    public JFreeChart expensesByCategoryPieChart(User user, LocalDate from, LocalDate to) {
        List<Object[]> rows = transactionRepository.sumByCategory(user, TransactionType.EXPENSE, from, to);

        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        for (Object[] row : rows) {
            String categoryName = (String) row[0];
            BigDecimal sum = (BigDecimal) row[1];
            dataset.setValue(categoryName, sum);
        }

        if (rows.isEmpty()) {
            dataset.setValue("No expenses in the selected period", 1);
        }

        return ChartFactory.createPieChart(
                "Expenses by category",
                dataset,
                true,
                true,
                false
        );
    }

    public JFreeChart incomeByCategoryBarChart(User user, LocalDate from, LocalDate to) {
        List<Object[]> rows = transactionRepository.sumByCategory(user, TransactionType.INCOME, from, to);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Object[] row : rows) {
            String categoryName = (String) row[0];
            BigDecimal sum = (BigDecimal) row[1];
            dataset.addValue(sum, "Income", categoryName);
        }

        return ChartFactory.createBarChart(
                "Income by category",
                "Category",
                "Amount",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );
    }
}
