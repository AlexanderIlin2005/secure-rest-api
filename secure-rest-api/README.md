# Secure REST API (Лабораторная работа №1)

## Описание проекта
Учебный backend-сервис на Java (Spring Boot), реализующий базовые меры защиты от OWASP Top 10.
В качестве базы данных используется PostgreSQL.

## Ссылка на репозиторий
https://github.com/AlexanderIlin2005/secure-rest-api

## Эндпоинты API
1. `POST /auth/register` — регистрация нового пользователя.
    * Тело запроса: `{"username": "test", "password": "password"}`
2. `POST /auth/login` — аутентификация пользователя.
    * Тело запроса: `{"username": "test", "password": "password"}`
    * Ответ: `{"token": "<JWT_TOKEN>"}`
3. `GET /api/data` — получение защищенных данных.
    * Требует заголовок `Authorization: Bearer <JWT_TOKEN>`

## Реализованные меры защиты
* **Защита от SQLi (SQL-инъекций):** Используется Spring Data JPA (Hibernate), который по умолчанию использует параметризованные запросы. Конкатенация строк в SQL-запросах отсутствует.
* **Защита от XSS:** Spring Security и Jackson автоматически экранируют данные при сериализации в JSON, предотвращая выполнение вредоносных скриптов.
* **Broken Authentication:**
    * Пароли хэшируются с помощью `BCryptPasswordEncoder` перед сохранением в БД.
    * При успешном входе выдается JWT-токен (с использованием библиотеки `jjwt`).
    * Написан `JwtFilter` (middleware), который проверяет токен на всех защищенных эндпоинтах.

## CI/CD Pipeline
В репозитории настроен GitHub Actions (файл `.github/workflows/ci.yml`), который при каждом пуше выполняет:
1. Сборку проекта (`mvn -B clean package`).
2. **SAST:** Статический анализ кода на уязвимости с помощью **SpotBugs**.
3. **SCA:** Анализ зависимостей на известные уязвимости с помощью **OWASP Dependency-Check**.

## Как запустить локально
1. Поднять базу данных в Docker:
   `docker run --name postgres-lab1 -e POSTGRES_PASSWORD=password -e POSTGRES_DB=securedb -p 5433:5432 -d postgres`
2. Запустить приложение: `mvn spring-boot:run`.