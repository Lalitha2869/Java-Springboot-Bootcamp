# Spring Boot Book API — Days 6, 7 & 8

A learning project built step-by-step while learning Java, Spring Boot, REST APIs, Spring Data JPA, MySQL, DTOs, dependency injection, and Bean Validation.

## 📚 Day 6 — Spring Boot REST API

### Goal

Build a basic REST API for managing books using Spring Boot.

### Technologies

- Java 21
- Spring Boot
- Spring Web
- Maven
- Lombok
- Postman

### Concepts Learned

- Spring Boot application setup
- `@SpringBootApplication`
- Embedded server
- REST APIs
- HTTP methods
- Controllers
- CRUD operations
- `@GetMapping`
- `@PostMapping`
- `@PutMapping`
- `@DeleteMapping`
- `@PathVariable`
- `@RequestBody`
- `ResponseEntity`
- JSON ↔ Java objects
- Postman API testing

### Day 6 Architecture

```text
Postman
   ↓
HTTP Request
   ↓
Controller
   ↓
Java Object
   ↓
JSON Response
```

### Main Endpoints

```text
GET     /api/books
GET     /api/books/{id}
POST    /api/books
PUT     /api/books/{id}
DELETE  /api/books/{id}
```

### Example Book JSON

```json
{
  "title": "Clean Code",
  "author": "Robert Martin",
  "price": 500
}
```

### Important Learning

Day 6 used an in-memory collection such as `List<Book>`.

That means:

```text
Application starts
      ↓
Data exists in memory
      ↓
Application stops
      ↓
Data is lost
```

This limitation leads to Day 7.

---

# 🗄️ Day 7 — Spring Data JPA + MySQL

## Goal

Replace in-memory storage with a real MySQL database.

### Technologies

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- MySQL
- MySQL Connector/J
- Maven
- Postman

## Concepts Learned

### ORM

ORM means Object-Relational Mapping.

It connects:

```text
Java Object
    ↕
Database Table
```

### JPA vs Hibernate vs Spring Data JPA

```text
JPA
 ↓
Specification

Hibernate
 ↓
JPA Implementation

Spring Data JPA
 ↓
Simplifies Repository Operations
```

Memory trick:

> JPA defines, Hibernate does, Spring Data simplifies.

## Entity

A Java class can represent a database table using:

```java
@Entity
@Table(name = "books")
```

Primary key:

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
```

## Repository

```java
public interface BookRepository
        extends JpaRepository<Book, Long> {
}
```

Spring Data JPA provides operations such as:

```text
save()
findAll()
findById()
deleteById()
existsById()
```

## Day 7 Architecture

```text
Postman
   ↓
Controller
   ↓
Service
   ↓
JpaRepository
   ↓
JPA
   ↓
Hibernate
   ↓
SQL
   ↓
MySQL
```

## Database

Example:

```sql
CREATE DATABASE bookdb;
USE bookdb;
```

Tables:

```text
authors
books
```

Relationship:

```text
Author
  1
  │
  │
  └──────────< Many Books
```

The `books` table contains:

```text
author_id
```

as the foreign key.

## Relationships Learned

### Many Books → One Author

```java
@ManyToOne
@JoinColumn(name = "author_id")
private Author author;
```

### One Author → Many Books

```java
@OneToMany(mappedBy = "author")
private List<Book> books;
```

## Derived Queries

Spring Data JPA can create queries from method names:

```java
List<Book> findByTitleContaining(String title);

List<Book> findByAuthorName(String authorName);
```

## Custom JPQL Query

```java
@Query("SELECT b FROM Book b WHERE b.price > :price")
List<Book> findBooksAbovePrice(
        @Param("price") double price
);
```

## Example API Request

Create author:

```http
POST /api/authors
```

```json
{
  "name": "Robert Martin"
}
```

Create book:

```http
POST /api/books
```

```json
{
  "title": "Clean Code",
  "price": 500,
  "author": {
    "id": 1
  }
}
```

## Day 7 Result

Unlike Day 6, data is persisted in MySQL.

```text
Application
   ↓
MySQL
   ↓
Data remains after application restart
```

---

# 🏗️ Day 8 — Clean Architecture, DTOs & Validation

## Goal

Improve the API structure and make it closer to a real production-style backend.

### Technologies

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL
- Bean Validation
- Lombok
- Maven
- Postman

---

# 1. Controller → Service → Repository

The application is split into layers.

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

## Controller

Responsible for:

- HTTP requests
- HTTP responses
- Request/response DTOs
- Validation trigger
- HTTP status codes

Example:

```java
@RestController
@RequestMapping("/api/books")
public class BookController {
}
```

## Service

Responsible for:

- Business logic
- Processing data
- DTO ↔ Entity conversion
- Calling repositories

Example:

```java
@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
}
```

## Repository

Responsible for database access.

```java
public interface BookRepository
        extends JpaRepository<Book, Long> {
}
```

---

# 2. Constructor Injection

Day 8 uses constructor injection.

```java
private final BookRepository bookRepository;

public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
}
```

Spring creates the required dependency and provides it through the constructor.

Flow:

```text
Spring
  ↓
Creates Repository
  ↓
Creates Service
  ↓
Injects Repository into Service
```

This keeps dependencies explicit and makes the code easier to test and maintain.

---

# 3. DTOs

DTO means:

> Data Transfer Object

Important rule:

```text
Entity → Database model
DTO    → API model
```

The API no longer directly accepts or returns the `Book` entity.

## BookRequest

Used when the client sends data to the API.

```java
@Data
public class BookRequest {

    private String title;

    private double price;
}
```

Example request:

```json
{
  "title": "Clean Code",
  "price": 500
}
```

## BookResponse

Used when the API returns data to the client.

```java
@Data
@AllArgsConstructor
public class BookResponse {

    private Long id;

    private String title;

    private double price;
}
```

Example response:

```json
{
  "id": 1,
  "title": "Clean Code",
  "price": 500.0
}
```

## DTO Flow

Request:

```text
Client
   ↓
BookRequest DTO
   ↓
Service
   ↓
Book Entity
   ↓
Repository
   ↓
MySQL
```

Response:

```text
MySQL
   ↓
Book Entity
   ↓
Service
   ↓
BookResponse DTO
   ↓
Client
```

---

# 4. Bean Validation

Validation prevents invalid data from entering the application.

The validation dependency:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

## Validation Annotations

### `@NotBlank`

Prevents empty or whitespace-only strings.

```java
@NotBlank(message = "Title is required")
private String title;
```

### `@Size`

Controls string length.

```java
@Size(
    min = 2,
    max = 100,
    message = "Title must be between 2 and 100 characters"
)
private String title;
```

### `@Positive`

Requires a number greater than zero.

```java
@Positive(message = "Price must be greater than 0")
private double price;
```

---

# 5. Validated BookRequest

Final Day 8 request DTO:

```java
package com.example.book_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookRequest {

    @NotBlank(message = "Title is required")
    @Size(
            min = 2,
            max = 100,
            message = "Title must be between 2 and 100 characters"
    )
    private String title;

    @Positive(message = "Price must be greater than 0")
    private double price;
}
```

---

# 6. `@Valid`

The controller uses:

```java
@PostMapping
public ResponseEntity<BookResponse> createBook(
        @Valid @RequestBody BookRequest request) {

    BookResponse response =
            bookService.createBook(request);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
}
```

`@Valid` tells Spring:

> Validate the request object before processing it.

Flow:

```text
JSON Request
     ↓
BookRequest
     ↓
@Valid
     ↓
Validation
     ↓
Valid? ───── No ───→ 400 Bad Request
  │
 Yes
  ↓
Service
```

---

# 7. Global Exception Handling

Validation errors are handled centrally using:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
}
```

The validation exception:

```java
MethodArgumentNotValidException
```

is handled with:

```java
@ExceptionHandler(MethodArgumentNotValidException.class)
```

## Final GlobalExceptionHandler

```java
package com.example.book_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {

                    String fieldName = error.getField();
                    String errorMessage = error.getDefaultMessage();

                    errors.put(fieldName, errorMessage);
                });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }
}
```

---

# 🧪 Day 8 Validation Testing

## Valid request

```http
POST /api/books
```

```json
{
  "title": "Clean Code",
  "price": 500
}
```

Expected:

```text
201 Created
```

---

## Blank title

```json
{
  "title": "",
  "price": 500
}
```

Expected:

```text
400 Bad Request
```

Response:

```json
{
  "title": "Title is required"
}
```

---

## Title too short

```json
{
  "title": "A",
  "price": 500
}
```

Expected:

```text
400 Bad Request
```

Response:

```json
{
  "title": "Title must be between 2 and 100 characters"
}
```

---

## Negative price

```json
{
  "title": "Clean Code",
  "price": -500
}
```

Expected:

```text
400 Bad Request
```

Response:

```json
{
  "price": "Price must be greater than 0"
}
```

---

## Multiple validation errors

```json
{
  "title": "",
  "price": -500
}
```

Expected:

```text
400 Bad Request
```

Response:

```json
{
  "title": "Title is required",
  "price": "Price must be greater than 0"
}
```

---

# 📁 Day 8 Project Structure

```text
book-api-day8
│
├── src
│   └── main
│       ├── java
│       │   └── com.example.book_api
│       │       │
│       │       ├── BookApiDay8Application.java
│       │       │
│       │       ├── controller
│       │       │   └── BookController.java
│       │       │
│       │       ├── service
│       │       │   └── BookService.java
│       │       │
│       │       ├── repository
│       │       │   └── BookRepository.java
│       │       │
│       │       ├── model
│       │       │   └── Book.java
│       │       │
│       │       ├── dto
│       │       │   ├── BookRequest.java
│       │       │   └── BookResponse.java
│       │       │
│       │       └── exception
│       │           └── GlobalExceptionHandler.java
│       │
│       └── resources
│           └── application.properties
│
├── pom.xml
└── README.md
```

---

# 🔄 Complete Architecture — Days 6 → 8

## Day 6

```text
Postman
   ↓
Controller
   ↓
In-Memory List
```

Focus:

> REST API fundamentals.

---

## Day 7

```text
Postman
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
JPA / Hibernate
   ↓
MySQL
```

Focus:

> Database persistence and Spring Data JPA.

---

## Day 8

```text
                         Client
                           │
                           ▼
                    BookRequest DTO
                           │
                         @Valid
                           │
                    ┌──────┴──────┐
                    │             │
                  Valid         Invalid
                    │             │
                    ▼             ▼
                Controller       400
                    │             │
                    ▼             │
                 Service           │
                    │             │
              DTO → Entity         │
                    │             │
                    ▼             │
                Repository         │
                    │             │
                    ▼             │
                  MySQL            │
                    │              │
                    ▼              │
                Entity             │
                    │              │
              Entity → DTO         │
                    │              │
                    ▼              │
             BookResponse          │
                    │              │
                    └──────┬───────┘
                           ▼
                         Client
```

---

# 🧠 What I Learned

### Day 6

- Spring Boot fundamentals
- REST APIs
- Controllers
- CRUD
- HTTP methods
- Request/Response
- Postman

### Day 7

- JPA
- Hibernate
- Spring Data JPA
- Entities
- Repositories
- MySQL
- ORM
- Relationships
- Derived queries
- JPQL

### Day 8

- Layered architecture
- Controller / Service / Repository
- Constructor injection
- DTOs
- Request DTO vs Response DTO
- Entity ↔ DTO mapping
- Bean Validation
- `@Valid`
- `@NotBlank`
- `@Size`
- `@Positive`
- Global exception handling
- Field-level validation errors

---

# 🏆 Golden Mental Model

Remember these lines:

```text
Controller → Handles HTTP
Service → Handles Business Logic
Repository → Handles Database Access

Entity → Represents Database Data
DTO → Represents API Data

@Valid → Triggers Validation

GlobalExceptionHandler
→ Converts Exceptions into Clean API Responses
```

The complete backend flow:

```text
HTTP Request
     ↓
DTO
     ↓
Validation
     ↓
Controller
     ↓
Service
     ↓
Entity
     ↓
Repository
     ↓
Database
     ↓
Entity
     ↓
DTO
     ↓
HTTP Response
```

---

# 🚀 Progress

| Day | Topic | Status |
|---|---|---|
| Day 6 | Spring Boot REST API & CRUD | ✅ |
| Day 7 | Spring Data JPA & MySQL | ✅ |
| Day 8 | Clean Architecture, DTOs & Validation | ✅ |

---

## 👩‍💻 Author

**Lalitha Rani**

Java / Spring Boot Backend Learning Journey

---

## 📝 Note

These projects are part of a step-by-step Java and Spring Boot backend learning journey. The focus is on understanding the architecture and concepts rather than only completing the code.
