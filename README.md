# Production Database Management System

Проєкт на Spring Boot для управління даними виробництва (Вироби -> Вузли -> Деталі). Реалізовано з використанням **Spring Data JDBC**.

## Початок роботи

### 1. Вимоги
* Встановлений **Docker Desktop** (має бути запущений).
* JDK 21.

### 2. Налаштування бази даних
База даних розгортається автоматично через `docker-compose`.
* **Файл:** `compose.yaml`
* **Порт:** `5433` (зовнішній) -> `5432` (внутрішній)
* **Користувач:** `myuser`
* **Пароль:** `newpass`

### 3. Конфігурація додатка
Файл `src/main/resources/application.properties` вже налаштований для роботи з Docker-контейнером:

```properties
spring.application.name=production03
server.port=8080

# Підключення до порту 5433
spring.datasource.url=jdbc:postgresql://localhost:5433/mydatabase?options=-c%20TimeZone=UTC
spring.datasource.username=myuser
spring.datasource.password=newpass
debug=true
spring.jpa.properties.hibernate.jdbc.time_zone=UTC