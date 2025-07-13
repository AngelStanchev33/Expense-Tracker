# Expense Tracker

A Spring Boot application for tracking personal expenses with budget management and multi-currency support.

## Features

- User authentication and authorization with JWT
- Expense tracking with categories
- Budget management
- Multi-currency support with exchange rates
- User preferences and roles
- RESTful API

## Technology Stack

- **Backend**: Spring Boot 3.x
- **Database**: MySQL (with Liquibase for migrations)
- **Security**: Spring Security with JWT
- **Build Tool**: Gradle
- **Java Version**: 17+

## Project Structure

```
src/main/java/com/angelstanchev/expense_tracker/
├── config/          # Security and configuration classes
├── model/           # Entities, DTOs, and enums
├── repository/      # Data access layer
├── service/         # Business logic layer
├── web/            # REST controllers
└── ExpenseTrackerApplication.java
```

## Getting Started

### Prerequisites

- Java 17 or higher
- PostgreSQL database
- Gradle (or use the included wrapper)

### Installation

1. Clone the repository:
```bash
git clone <your-repo-url>
cd expense-tracker
```

2. Configure the database connection in `src/main/resources/application.yml`

3. Run the application:
```bash
./gradlew bootRun
```

Or build and run:
```bash
./gradlew build
java -jar build/libs/expense-tracker-0.0.1-SNAPSHOT.jar
```

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - User login

### Expenses
- `GET /api/expenses` - Get all expenses
- `POST /api/expenses` - Create new expense
- `PUT /api/expenses/{id}` - Update expense
- `DELETE /api/expenses/{id}` - Delete expense

### Budgets
- `GET /api/budgets` - Get all budgets
- `POST /api/budgets` - Create new budget

### Categories
- `GET /api/categories` - Get all categories

### Currencies
- `GET /api/currencies` - Get all currencies
- `GET /api/exchange-rates` - Get exchange rates

## Database Schema

The application uses Liquibase for database migrations. All migration files are located in `src/main/resources/db/changelog/`.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License. 