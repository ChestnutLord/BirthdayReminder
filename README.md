# Поздравлятор (Birthday Reminder)

**Фронтенд доступен по ссылке [https://github.com/ChestnutLord/birthday-reminder-ui](https://github.com/ChestnutLord/birthday-reminder-ui)**

## Stack

**Основные технологии**

| Технология | Версия |
|------------|--------|
| Java | 21 |
| Spring Boot | 4.0.2 |
| Spring Boot Web | определяется Spring |
| Spring Boot Data JPA | определяется Spring |
| Spring Boot Validation | определяется Spring |
| Springdoc OpenAPI (Swagger) | 2.6.0 |

**База данных и миграции**

| Технология | Версия |
|------------|--------|
| PostgreSQL | 16 (образ `postgres:16-alpine`) |
| Flyway | определяется Spring |

**Библиотеки**

| Технология | Версия |
|------------|--------|
| Lombok | определяется Spring |
| MapStruct | 1.5.5.Final |

## Требования для развёртывания

- **Java:** 21 и выше  
- **Gradle:** для сборки (используется wrapper `gradlew` / `gradlew.bat`)  
- **Docker**  
- **Docker Compose**  

## Запуск в Docker

1. Клонируйте репозиторий:

   ```bash
   git clone https://github.com/ChestnutLord/BirthdayReminder
   cd BirthdayReminder
   ```

2. Скопируйте файл .env.docker в .env:

   ```bash
   cp env.example .env
   ```

3. Соберите JAR-файл приложения:

   ```bash
   ./gradlew clean build
   ```

   В Windows (cmd/PowerShell):

   ```bash
   gradlew.bat clean bootJar
   ```

4. Запустите контейнеры:

   ```bash
   docker compose up -d
   ```

   Приложение будет доступно на порту **8080**, PostgreSQL — на **5432**. Фотографии сохраняются в volume `photo-data`.

## Эндпоинты

| Назначение | URL |
|------------|-----|
| Swagger UI | http://localhost:8080/swagger-ui.html |
| OpenAPI JSON | http://localhost:8080/v3/api-docs |

