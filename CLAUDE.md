# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build and Development Commands

### Core Commands
- **Build project**: `./gradlew build`
- **Run application**: `./gradlew bootRun`
- **Run tests**: `./gradlew test`
- **Run single test class**: `./gradlew test --tests "com.angelstanchev.expense_tracker.service.JwtServiceTest"`
- **Clean build**: `./gradlew clean build`

### Database Management
- **Run with MySQL profile**: `./gradlew bootRun --args='--spring.profiles.active=mysql'`
- **Database migrations**: Handled automatically by Liquibase on startup
- **Migration files**: Located in `src/main/resources/db/changelog/`

### Testing
- **Unit tests**: Use H2 in-memory database (configured in `application-test.yml`)
- **Test profile**: Tests automatically use H2 with `create-drop` mode
- **JWT testing**: Test configuration includes hardcoded JWT secret for consistent testing

## Project Architecture

### Core Structure
This is a Spring Boot 3.x application using JWT authentication with the following layered architecture:

- **config/**: Security configuration, JWT filters, and authentication setup
- **model/entity/**: JPA entities with Liquibase-managed schema
- **model/dto/**: Data transfer objects for API communication
- **repository/**: Spring Data JPA repositories
- **service/**: Business logic layer with interface/implementation pattern
- **web/**: REST controllers for API endpoints

### Authentication System
- **JWT-based stateless authentication** with custom filter chain
- **AuthController**: Handles `/auth/login` endpoint
- **JwtAuthFilter**: Validates JWT tokens on each request
- **SecurityConfig**: Configures Spring Security with stateless session management
- **Database authentication**: Uses `ExpenseTrackerDetailsService` with BCrypt password encoding

### Database Design
- **Liquibase migrations**: All schema changes versioned in `db/changelog/v1.0/`
- **Multi-database support**: MySQL (production), H2 (testing)
- **Entities**: Users, Roles, Expenses, Budgets, Categories, Currencies, Exchange Rates, User Preferences

### Key Dependencies
- Spring Boot 3.5.3 with Java 17
- JWT library: `io.jsonwebtoken:jjwt-*`
- Database: MySQL Connector (production), H2 (testing)
- Liquibase for database migrations
- Lombok for reduced boilerplate
- ModelMapper for object mapping

## Development Guidelines

### Testing Strategy
- **Unit tests**: Use `@ExtendWith(MockitoExtension.class)` for fast isolated testing
- **Integration tests**: Use H2 database with Spring Boot test context
- **JWT testing**: Tests use predefined secret from `application-test.yml`

### Security Implementation
- All endpoints except `/auth/login`, `/auth/register`, and `/` require JWT authentication
- Passwords are BCrypt-encoded
- JWT tokens include user roles as claims
- Stateless session management (no server-side session storage)

### Database Development
- Never modify existing migration files
- Create new migration files for schema changes in `src/main/resources/db/changelog/v1.0/`
- Update `master.xml` to include new migration files
- Use appropriate rollback strategies in migrations

### Configuration Profiles
- **Default**: MySQL with local database
- **mysql**: Explicit MySQL configuration
- **postgresql**: PostgreSQL alternative
- **test**: H2 in-memory database (automatically used for tests)

## Teaching Mode Instructions
- This project is being used for job interview preparation
- Provide guidance and hints instead of complete solutions
- Ask leading quest3ions to help the user discover answers themselves
- Encourage exploration and understanding of the codebase
- Focus on teaching Spring Boot, JWT, and enterprise development patterns
- Answear only in Bulgarian

## Прогрес на разработката (20.07.2025)

### ✅ Завършено:
1. **JWT автентикация система**
   - AuthController с /auth/login endpoint
   - JWT токен генериране с роли
   - Security конфигурация
   - Валидация и error handling

2. **Custom валидационна система (@FieldMatch)**
   - FieldMatch анотация за сравняване на полета
   - FieldMatchValidator клас с Java reflection
   - Прилагане върху RegisterRequestDto за password == confirmPassword проверка

3. **RegisterRequestDto подготвен**
   - Всички стандартни валидации (@Email, @NotBlank, @Size)
   - @FieldMatch за проверка на паролите
   - Готов за използване в registration endpoint

### 📋 Следващи стъпки за утре:
1. **@UniqueEmail анотация**
   - Създаване на UniqueEmail анотация
   - UniqueEmailValidator с dependency injection на UserRepository
   - Прилагане върху email полето в RegisterRequestDto

2. **Registration endpoint**
   - UserService за business логика
   - /auth/register в AuthController
   - Тестване на пълната регистрационна функционалност

### 🎯 Цел: Завършена регистрационна система с уникалност на email