# Finora: Personal Finance Tracker

**Finora** is a personal finance web application for tracking income, expenses and categories, with a monthly dashboard and charts that show where the money goes.

[![Watch the Finora demo](docs/demo-preview.svg)](https://arianadaboc.github.io/finance-tracker/demo.html)

<p align="center">
  <a href="https://arianadaboc.github.io/finance-tracker/demo.html"><b>▶ Watch the demo</b></a> (2 min, plays in the browser)
  &nbsp;·&nbsp;
  <a href="Finora-Technical-Documentation.pdf"><b>📄 Technical documentation</b></a>
  &nbsp;·&nbsp;
  <a href="https://github.com/arianaDaboc/finance-tracker/releases/tag/v2.0.0"><b>🏷 Release v2.0.0</b></a>
</p>

---

## Features

- **Secure accounts**: registration and sign-in with Spring Security (hashed passwords, per-user data)
- **Transactions**: add income and expenses with amount, category, date and description
- **Categories**: create custom income and expense categories
- **Dashboard**: available balance, monthly income, monthly expenses and recent activity
- **Charts**: expenses by category (pie) and income by category (bar), generated server-side with JFreeChart
- **Responsive interface** with an editorial "ledger" design

## Tech stack

| Layer | Technologies |
|---|---|
| Backend | Java 17, Spring Boot 3.3.2, Spring Web, Spring Data JPA (Hibernate), Validation |
| Security | Spring Security, Thymeleaf Spring Security extras |
| Frontend | Thymeleaf templates, HTML, CSS |
| Database | PostgreSQL |
| Charts | JFreeChart |
| Build | Maven, Lombok |

## Architecture

```text
Browser
  │
  ▼
Controllers (Auth, Dashboard, Transaction, Category, Chart)
  │
  ▼
Services (User, Transaction, Category, Chart)
  │
  ▼
Repositories (Spring Data JPA) ──► PostgreSQL
```

## Getting started

### Prerequisites
- Java 17+
- Maven
- PostgreSQL

### 1. Create the database

```sql
CREATE DATABASE finance_tracker;
```

Tables are created automatically by Hibernate on startup.

### 2. Configure the connection

The defaults are `jdbc:postgresql://localhost:5432/finance_tracker` with user `postgres` and password `postgres`. Override them with environment variables if needed:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/finance_tracker
export DB_USERNAME=postgres
export DB_PASSWORD=your_password
```

On PowerShell use `$env:DB_PASSWORD="your_password"`.

### 3. Run

```bash
mvn spring-boot:run
```

Open <http://localhost:8080>, create an account and start adding transactions.

## Project structure

```text
src/main/java/com/finance/tracker/
  config/        Spring Security configuration
  controller/    Auth, Dashboard, Transaction, Category and Chart controllers
  entity/        User, Transaction, Category, TransactionType
  repository/    Spring Data JPA repositories
  service/       business logic and chart generation
src/main/resources/
  templates/     Thymeleaf pages (login, register, dashboard, transactions, categories)
  static/css/    styles
docs/demo.html   video demo page (GitHub Pages)
```

## Documentation

- [Technical documentation (PDF)](Finora-Technical-Documentation.pdf): architecture, data model and implementation details
- [Video demo](https://arianadaboc.github.io/finance-tracker/demo.html): a full walkthrough of the application
