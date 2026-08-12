package com.finance.tracker.controller;

import com.finance.tracker.entity.User;
import com.finance.tracker.service.ChartService;
import com.finance.tracker.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;

@Controller
@RequiredArgsConstructor
public class ChartController {

    private final ChartService chartService;
    private final UserService userService;

    @GetMapping("/chart/expenses-by-category")
    public void expensesByCategory(HttpServletResponse response, Authentication authentication) throws IOException {
        User user = userService.getByUsername(authentication.getName());
        YearMonth currentMonth = YearMonth.now();

        JFreeChart chart = chartService.expensesByCategoryPieChart(
                user, currentMonth.atDay(1), currentMonth.atEndOfMonth());

        writeChart(response, chart);
    }

    @GetMapping("/chart/income-by-category")
    public void incomeByCategory(HttpServletResponse response, Authentication authentication) throws IOException {
        User user = userService.getByUsername(authentication.getName());
        YearMonth currentMonth = YearMonth.now();

        JFreeChart chart = chartService.incomeByCategoryBarChart(
                user, currentMonth.atDay(1), currentMonth.atEndOfMonth());

        writeChart(response, chart);
    }

    private void writeChart(HttpServletResponse response, JFreeChart chart) throws IOException {
        response.setContentType("image/png");
        ChartUtils.writeChartAsPNG(response.getOutputStream(), chart, 480, 360);
    }
}
