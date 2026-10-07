# Book API - Day 10

Spring Boot REST API for managing books with Spring Data JPA, MySQL, DTOs, validation, exception handling, pagination, sorting, logging, unit testing, and Swagger/OpenAPI.

## Tech Stack

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA / Hibernate
- MySQL
- Maven
- Lombok
- JUnit 5
- Mockito
- MockMvc
- Springdoc OpenAPI / Swagger UI

## Prerequisites

Install Java 21, Maven, MySQL, IntelliJ IDEA, and Git.

```bash
java -version
mvn -v
git --version
```

## Database Setup

Create the development database:

```sql
CREATE DATABASE bookdb_day9_dev;
USE bookdb_day9_dev;
```

Hibernate creates/updates tables when:

```properties
spring.jpa.hibernate.ddl-auto=update
```

## Environment Variables

Do not hard-code database passwords.

```properties
spring.datasource.password=${DB_PASSWORD}
```

Windows PowerShell:

```powershell
$env:DB_PASSWORD="your_mysql_password"
```

Production can use:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
```

Never commit passwords, API keys, tokens, or other secrets to GitHub.

## Configuration

Application port: `8083`

Development profile:

```text
src/main/resources/application-dev.properties
```

Production profile:

```text
src/main/resources/application-prod.properties
```

Activate development:

```powershell
$env:SPRING_PROFILES_ACTIVE="dev"
```

Activate production:

```powershell
$env:SPRING_PROFILES_ACTIVE="prod"
```

## Run the Application

```powershell
$env:DB_PASSWORD="your_mysql_password"
mvn spring-boot:run
```

Application:

```text
http://localhost:8083
```

## API Endpoints

Base URL:

```text
http://localhost:8083/api/books
```

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/books` | Get books |
| GET | `/api/books/{id}` | Get book by ID |
| POST | `/api/books` | Create a book |
| PUT | `/api/books/{id}` | Update a book |
| DELETE | `/api/books/{id}` | Delete a book |

### Pagination and Sorting

```http
GET http://localhost:8083/api/books?page=0&size=5
```

```http
GET http://localhost:8083/api/books?page=0&size=5&sort=title
```

```http
GET http://localhost:8083/api/books?page=0&size=5&sort=title,desc
```

```http
GET http://localhost:8083/api/books?page=0&size=5&sort=price,asc
```

### Get Book

```http
GET http://localhost:8083/api/books/1
```

Example:

```json
{
  "id": 1,
  "title": "Spring Boot",
  "price": 750
}
```

Missing book:

```json
{
  "status": 404,
  "message": "Book with id 999 not found"
}
```

### Create Book

```http
POST http://localhost:8083/api/books
Content-Type: application/json
```

```json
{
  "title": "Spring Boot",
  "price": 750
}
```

### Update Book

```http
PUT http://localhost:8083/api/books/1
Content-Type: application/json
```

```json
{
  "title": "Advanced Spring Boot",
  "price": 950
}
```

### Delete Book

```http
DELETE http://localhost:8083/api/books/1
```

## Swagger UI

Start the application and open:

```text
http://localhost:8083/swagger-ui/index.html
```

OpenAPI JSON:

```text
http://localhost:8083/v3/api-docs
```

Swagger UI allows developers to view and execute API endpoints.

## Testing

Run all tests:

```bash
mvn test
```

### BookServiceTest

Covers:

1. Create book
2. Find book by ID
3. Book not found

### BookControllerTest

Tests:

```http
GET /api/books/{id}
```

using:

```text
@WebMvcTest
MockMvc
Mockito
```

The Service layer is mocked, so the controller test does not require MySQL.

## Logging

Example:

```text
INFO  Fetching book with id: 1
INFO  Book with id 1 found successfully
WARN  Book with id 999 was not found
INFO  Fetching books - page: 0, size: 5, sort: title: ASC
```

Logging levels:

```text
DEBUG
INFO
WARN
ERROR
```

Never log passwords, API keys, tokens, database credentials, or sensitive information.

## Architecture

```text
Client
   |
   v
Controller
   |
   v
Service
   |
   v
Repository
   |
   v
JPA / Hibernate
   |
   v
MySQL
```

Supporting features:

```text
DTO
Validation
Global Exception Handler
Pagination
Sorting
Logging
Swagger / OpenAPI
JUnit
Mockito
Spring Profiles
```

## Build the Application

```bash
mvn clean package
```

The executable JAR is created under:

```text
target/
```

Example:

```text
target/book-api-day9-0.0.1-SNAPSHOT.jar
```

## Run the JAR

```powershell
$env:DB_PASSWORD="your_mysql_password"
java -jar target/book-api-day9-0.0.1-SNAPSHOT.jar
```

Application:

```text
http://localhost:8083
```

## Useful Git Commands

```bash
git status
git add .
git commit -m "Complete Day 10 tasks"
git push
```

## Future Roadmap

1. Spring Security
2. Authentication
3. Authorization
4. JWT Authentication
5. Role-Based Authorization
6. Flyway Database Migrations
7. Docker
8. Docker Compose
9. GitHub Actions / CI-CD
10. Cloud Deployment
11. Monitoring and Observability

## Day 10 Learning Summary

- JUnit 5
- Mockito
- Service unit testing
- `@WebMvcTest`
- MockMvc
- Springdoc OpenAPI
- Swagger UI
- README documentation
- Maven packaging
- Runnable JAR
- Environment variables
- Regression testing

## Final Mental Model

```text
                  Client
                    |
                    v
                Controller
                    |
              Validation
                    |
                    v
                 Service
                    |
                 Logging
                    |
                    v
                Repository
                    |
                    v
             JPA / Hibernate
                    |
                    v
                  MySQL
```

Testing:

```text
JUnit + Mockito
       |
       v
 Service Test

@WebMvcTest
       |
       v
Controller Test
```

Documentation:

```text
Springdoc OpenAPI
       |
       v
 Swagger UI
```

Packaging:

```text
Source Code
     |
     v
mvn clean package
     |
     v
JAR
     |
     v
java -jar
     |
     v
Running Application
```
