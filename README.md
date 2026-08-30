# Finora Finance Tracker

Finora is a personal finance web application for tracking income, expenses, categories, and monthly financial activity.

## Features

- Secure account registration and sign-in
- Income and expense tracking
- Custom financial categories
- Monthly balance and recent activity
- Expense and income charts
- Responsive editorial-ledger interface

## Technology

Java 17, Spring Boot 3.3.2, Spring Security, Thymeleaf, PostgreSQL, Hibernate, and JFreeChart.

## Run locally

1. Create a PostgreSQL database named `finance_tracker`.
2. Configure `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` if they differ from the defaults.
3. Start the application:

```bash
mvn spring-boot:run
```

4. Open [http://localhost:8080](http://localhost:8080).

## Demo and documentation

- [Watch the application video](https://arianadaboc.github.io/finance-tracker/demo.html)
- [Download the video from GitHub Releases](https://github.com/arianaDaboc/finance-tracker/releases/download/v2.0.0/financial_tracker_video.mp4)
- [Finora v2.0.0 release](https://github.com/arianaDaboc/finance-tracker/releases/tag/v2.0.0)
- [Technical documentation](DOCUMENTATION.html)
- [Technical documentation PDF](Finora-Technical-Documentation.pdf)
