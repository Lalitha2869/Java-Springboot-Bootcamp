# Day 9 – Advanced Spring Boot REST API

## Overview

Day 9 focuses on improving the Book API with production-style backend features:

- Global exception handling for missing resources
- Pagination and sorting
- Spring Boot profiles
- SLF4J logging

**Project:** `book-api-day9`  
**Java:** 21  
**Spring Boot:** 4.1.1  
**Build Tool:** Maven  
**Database:** MySQL  
**Server Port:** 8083

---

## Day 9 Tasks

| Task | Topic | Expected Result |
|---|---|---|
| T9.1 | ResourceNotFoundException + 404 handling | Missing ID returns clear JSON with HTTP 404 |
| T9.2 | Pagination and Sorting | API returns limited records with page/sort options |
| T9.3 | Spring Profiles | Dev and Prod configurations can be switched |
| T9.4 | SLF4J Logging | Service operations appear in application logs |

---

# T9.1 – Resource Not Found Exception

## Purpose

When a requested book does not exist, the API should return a meaningful `404 Not Found` response instead of a generic error.

### ResourceNotFoundException

```java
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
```

### ErrorResponse

```java
@Data
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
}
```

### GlobalExceptionHandler

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException exception) {

        ErrorResponse errorResponse =
                new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        exception.getMessage()
                );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }
}
```

### Test

```http
GET http://localhost:8083/api/books/999
```

Expected:

```json
{
  "status": 404,
  "message": "Book with id 999 not found"
}
```

---

# T9.2 – Pagination and Sorting

## Purpose

Instead of returning every book at once, pagination allows the API to return a limited number of records.

### Repository

`BookRepository.java`

```java
public interface BookRepository
        extends JpaRepository<Book, Long> {
}
```

### Service

`BookService.java`

```java
public Page<BookResponse> getBooks(Pageable pageable) {

    return bookRepository
            .findAll(pageable)
            .map(book -> new BookResponse(
                    book.getId(),
                    book.getTitle(),
                    book.getPrice()
            ));
}
```

### Controller

`BookController.java`

```java
@GetMapping
public Page<BookResponse> getBooks(Pageable pageable) {
    return bookService.getBooks(pageable);
}
```

### API Examples

```http
GET http://localhost:8083/api/books?page=0&size=5
```

```http
GET http://localhost:8083/api/books?page=1&size=5
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

```http
GET http://localhost:8083/api/books?page=0&size=5&sort=price,desc
```

Spring pagination is **zero-based**:

```text
page=0 → first page
page=1 → second page
page=2 → third page
```

If `size=5`:

```text
page=0 → records 1–5
page=1 → records 6–10
page=2 → records 11–15
```

### Mental Model

```text
Pageable
   |
   +-- page
   +-- size
   +-- sort
        |
        v
Repository
        |
        v
Database
        |
        v
Page<Book>
        |
        v
Page<BookResponse>
```

---

# T9.3 – Spring Profiles

## Purpose

Different environments need different configurations.

```text
Development → DEV
Production  → PROD
```

## application.properties

Location:

```text
src/main/resources/application.properties
```

```properties
spring.application.name=book-api-day9
spring.profiles.active=${SPRING_PROFILES_ACTIVE:dev}
```

## application-dev.properties

Location:

```text
src/main/resources/application-dev.properties
```

```properties
server.port=8083

spring.datasource.url=jdbc:mysql://localhost:3306/bookdb_day9_dev
spring.datasource.username=root
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

logging.level.com.example.book_api=DEBUG
```

## application-prod.properties

Location:

```text
src/main/resources/application-prod.properties
```

```properties
server.port=8083

spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false

logging.level.com.example.book_api=INFO
```

### Switch Profile

PowerShell:

```powershell
$env:SPRING_PROFILES_ACTIVE="dev"
```

or:

```powershell
$env:SPRING_PROFILES_ACTIVE="prod"
```

Then:

```bash
mvn spring-boot:run
```

**Important:** Never commit production passwords, API keys, tokens, or database credentials to Git. Use environment variables.

---

# T9.4 – SLF4J Logging

## Purpose

Logging helps us understand what the application is doing while it is running.

Example:

```text
INFO  Fetching book with id: 1
INFO  Book with id 1 found successfully
WARN  Book with id 999 was not found
INFO  Fetching books - page: 0, size: 5, sort: title: ASC
```

## File

```text
src/main/java/com/example/book_api/service/BookService.java
```

### Imports

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
```

### Logger

Inside `BookService`:

```java
private static final Logger log =
        LoggerFactory.getLogger(BookService.class);
```

### Get Book Logging

```java
public BookResponse getBookById(Long id) {

    log.info("Fetching book with id: {}", id);

    Book book = bookRepository.findById(id)
            .orElseThrow(() -> {

                log.warn("Book with id {} was not found", id);

                return new ResourceNotFoundException(
                        "Book with id " + id + " not found"
                );
            });

    log.info("Book with id {} found successfully", id);

    return new BookResponse(
            book.getId(),
            book.getTitle(),
            book.getPrice()
    );
}
```

### Pagination Logging

```java
public Page<BookResponse> getBooks(Pageable pageable) {

    log.info(
            "Fetching books - page: {}, size: {}, sort: {}",
            pageable.getPageNumber(),
            pageable.getPageSize(),
            pageable.getSort()
    );

    return bookRepository
            .findAll(pageable)
            .map(book -> new BookResponse(
                    book.getId(),
                    book.getTitle(),
                    book.getPrice()
            ));
}
```

### Logging Levels

| Level | Purpose |
|---|---|
| DEBUG | Detailed developer information |
| INFO | Normal application activity |
| WARN | Unexpected situation; application continues |
| ERROR | Serious failure |

Examples:

```java
log.debug("Debug information");
log.info("Book created successfully");
log.warn("Book was not found");
log.error("Database operation failed");
```

Never log passwords, API keys, access tokens, database credentials, or other sensitive information.

---

# Day 9 Architecture

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
                MySQL
```

Supporting features:

```text
GlobalExceptionHandler
        |
        +---- Handles application exceptions

DTOs
        |
        +---- Controls API request/response structure

Validation
        |
        +---- Validates incoming data

Pagination
        |
        +---- Controls large result sets

Profiles
        |
        +---- Environment-specific configuration

SLF4J Logging
        |
        +---- Application observability
```

---

# Day 9 Testing Checklist

## T9.1

- [ ] Start application
- [ ] Call an existing book ID
- [ ] Call a non-existing ID such as `999`
- [ ] Verify HTTP `404`
- [ ] Verify JSON error response

## T9.2

- [ ] Test `page=0&size=5`
- [ ] Test `page=1&size=5`
- [ ] Test `sort=title`
- [ ] Test `sort=title,desc`
- [ ] Test `sort=price,asc`
- [ ] Test `sort=price,desc`

## T9.3

- [ ] Create `application-dev.properties`
- [ ] Create `application-prod.properties`
- [ ] Set `SPRING_PROFILES_ACTIVE`
- [ ] Start with `dev`
- [ ] Start with `prod`
- [ ] Verify database configuration changes

## T9.4

- [ ] Add SLF4J logger
- [ ] Add INFO log for fetching a book
- [ ] Add WARN log when book is missing
- [ ] Add INFO log for pagination
- [ ] Run APIs
- [ ] Verify logs in IntelliJ console

---

# Key Concepts Learned

### Exception Handling

```text
Exception
   ↓
@ExceptionHandler
   ↓
JSON response
   ↓
HTTP status
```

### Pagination

```text
Pageable = page + size + sort
```

### Profiles

```text
application.properties
        ↓
     active profile
        ↓
application-dev.properties
or
application-prod.properties
```

### Logging

```text
Application
     ↓
SLF4J
     ↓
Console / Log file / Monitoring system
```

---

# Useful Git Commands

Check status:

```bash
git status
```

Create Day 9 branch:

```bash
git checkout -b day9-spring-boot
```

Add files:

```bash
git add .
```

Commit:

```bash
git commit -m "Complete Day 9 Spring Boot tasks"
```

Push:

```bash
git push -u origin day9-spring-boot
```

If you are already working on the correct Day 9 branch, just use:

```bash
git add .
git commit -m "Complete Day 9 Spring Boot tasks"
git push
```

---

# Day 9 Completion

By the end of Day 9, the Book API supports:

- REST CRUD operations
- DTO-based API responses
- Validation
- Global exception handling
- Resource-not-found handling
- Pagination
- Sorting
- Environment profiles
- SLF4J logging
- MySQL persistence
- Spring Data JPA

## Final Mental Model

```text
HTTP Request
     ↓
Controller
     ↓
Validation
     ↓
Service
     ↓
Logging
     ↓
Repository
     ↓
JPA / Hibernate
     ↓
MySQL
     ↓
Response
```

Day 9 builds the bridge from a basic CRUD API toward a more production-ready Spring Boot backend.
