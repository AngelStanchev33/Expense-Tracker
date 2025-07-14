# JWT Автентикация - Прогрес в Ученето

## 🎯 Цел на проекта
**УЧЕНЕ** - разбиране на JWT автентикацията и правилното ѝ прилагане в Spring Boot

## 📚 Научено до момента

### 1. JWT Автентикация Система ✅
**Компоненти създадени:**
- `AuthRequestDto` и `AuthResponseDto` - за login заявки
- `JwtService` - методи за генериране и валидация на токени
- `AuthController` - endpoint `/auth/login` за автентикация
- `JwtAuthFilter` - филтър за проверка на JWT токени при всяка заявка
- `SecurityConfig` - конфигуриране на JWT автентикация, disable на form login

### 2. Интеграция с Съществуващата Система ✅
**Използвани компоненти:**
- `ExpenseTrackerDetailsService` - за намиране на потребители
- `ExpenseTrackerUser` - за user details
- MySQL база данни за production

### 3. Научени Концепции ✅
- Разликата между `AuthenticationManager` и директна валидация
- Защо използваме `AuthenticationProvider`
- Как работи JWT token validation
- Stateless vs Session-based автентикация

### 4. Детайлни Разбирания ✅
**AuthenticationManager vs Директна Валидация:**
- `AuthenticationManager` е официалният Spring Security начин
- Централизирана логика за всички AuthenticationProvider-и
- По-гъвкав и стандартизиран подход

**UsernamePasswordAuthenticationToken Конструктори:**
- Конструктор 1: `(username, password, authorities)` - за неавтентикирани потребители
- Конструктор 2: `(principal, null, authorities)` - за автентикирани потребители

**JwtAuthFilter Логика:**
- Проверява `Authorization: Bearer <token>` header
- При липса на header → продължава към следващия филтър (НЕ връща 401)
- При валиден токен → сетва authentication в SecurityContext
- Проверява `SecurityContextHolder.getContext().getAuthentication() == null` за да избегне дублиране

**Множество AuthenticationProvider-и:**
- Възможно е да имаме няколко providers
- Spring Security ги опитва последователно
- Strategy pattern за различни типове автентикация

## 🧪 Тестване - Текущ Прогрес

### Конфигуриране на Тестова Среда ✅
**Направено:**
1. **H2 Database за тестове**
   - Добавен `testImplementation("com.h2database:h2")` в `build.gradle.kts`
   - Създаден `src/test/resources/application-test.yml`
   - Конфигуриран H2 с `create-drop` режим

2. **JWT Конфигурация за тестове**
   ```yaml
   jwt:
     secret: 404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
     expiration: 86400000
   ```

### Unit Тестове за JwtService ✅
**Създаден:** `src/test/java/com/angelstanchev/expense_tracker/service/JwtServiceTest.java`

**Конфигурация:**
- `@ExtendWith(MockitoExtension.class)` - бърз unit тест без Spring контекст
- `@BeforeEach` - инициализира JwtService с тестови параметри
- Директно подаване на `secret` и `expiration` в конструктора

**Написани тестове:**
1. ✅ `generateToken_WithClams_ShoulCreateValidToken()` - тества генериране с claims
2. ✅ `extractUsername_ShouldReturnCorrectUsername()` - тества извличане на username

**Резултати:**
- Всички тестове минават успешно
- Време за изпълнение: ~1300ms (първо изпълнение)

## 🎓 Научени Принципи за Тестване

### Unit vs Integration Тестове
- **Unit тестове** = тестваме отделни методи/класове изолирано
- **Integration тестове** = тестваме как компонентите работят заедно

### Конфигурация за Тестове
- **H2 Database** = бърз, в паметта, `create-drop` режим
- **Отделен профил** = `@ActiveProfiles("test")` за да използва `application-test.yml`
- **Без Liquibase** = за тестове не ни трябва, H2 създава схемата автоматично

## 🚀 Следващи Стъпки

### 1. Допълнителни Unit Тестове за JwtService
- [ ] Тест за невалидни токени
- [ ] Тест за изтекли токени  
- [ ] Тест за edge cases

### 2. Integration Тестове за AuthController
- [ ] Тест на `/auth/login` endpoint
- [ ] Тест с валидни/невалидни credentials
- [ ] Тест на response формат

### 3. Integration Тестове за JwtAuthFilter
- [ ] Тест на филтриране на заявки
- [ ] Тест с/без JWT токени
- [ ] Тест на защитени endpoints

### 4. Регистрация Endpoint
- [ ] Създаване на endpoint за регистрация
- [ ] Валидация на входни данни
- [ ] Хеширане на пароли

### 5. Error Handling
- [ ] По-добри грешки при невалидни токени
- [ ] Global exception handling
- [ ] Custom error responses

### 6. Frontend Интеграция
- [ ] Как frontend-ът използва JWT токени
- [ ] Token storage (localStorage vs cookies)
- [ ] Token refresh логика

## 📝 Важни Файлове

### Конфигурация
- `build.gradle.kts` - H2 dependency за тестове
- `src/main/resources/application.yml` - production конфигурация
- `src/test/resources/application-test.yml` - тестова конфигурация

### Код
- `JwtService` и `JwstServiceImpl` - JWT логика
- `AuthController` - login endpoint
- `JwtAuthFilter` - филтриране на заявки
- `SecurityConfig` - Spring Security конфигурация

### Тестове
- `JwtServiceTest` - unit тестове за JWT логика

## 🎯 Ключови Научения

1. **JWT е stateless** - не се съхранява в база данни
2. **Unit тестове са бързи** - използваме MockitoExtension
3. **H2 е перфектен за тестове** - бърз и в паметта
4. **Важно е да тестваме edge cases** - за качествен код
5. **Разделяме unit и integration тестове** - за по-добра организация
6. **AuthenticationManager е официалният начин** - не директна валидация
7. **JwtAuthFilter не трябва да връща 401** при липса на header
8. **SecurityContextHolder проверка** предотвратява дублиране на сесии
9. **Множество AuthenticationProvider-и** са възможни за различни типове auth
10. **UsernamePasswordAuthenticationToken има два конструктора** - за различни случаи

## 🔧 Технически Детайли

### JWT Token Структура
```json
{
  "sub": "john@example.com",
  "roles": ["USER"],
  "exp": 1640995200,
  "iat": 1640908800
}
```

### Flow на Автентикация
```
1. Клиент: POST /auth/login {"username": "test", "password": "pass"}
2. AuthController: AuthenticationManager.authenticate()
3. AuthenticationManager: DaoAuthenticationProvider.authenticate()
4. DaoAuthenticationProvider: UserDetailsService + PasswordEncoder
5. Ако успешно: генерира JWT токен
6. Клиент: Authorization: Bearer <token>
7. JwtAuthFilter: валидира токена
8. Ако валиден: SecurityContextHolder.setAuthentication()
```

---
*Последна актуализация: Декември 2024* 