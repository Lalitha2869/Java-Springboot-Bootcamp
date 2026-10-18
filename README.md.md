# Incident Management System API

A RESTful backend for creating, tracking, assigning, filtering, and
resolving operational incidents. Built as a Java/Spring Boot learning
project.

> **Project status:** Features described below reflect the
> intended/current code structure. Run the build and tests on your
> machine before claiming all tests pass or calling the project
> production-ready.

## Contents

-   [Features](#features)
-   [Technology stack](#technology-stack)
-   [Architecture](#architecture)
-   [Project structure](#project-structure)
-   [Prerequisites](#prerequisites)
-   [Configuration](#configuration)
-   [Database setup](#database-setup)
-   [Run the application](#run-the-application)
-   [Swagger/OpenAPI](#swaggeropenapi)
-   [API endpoints](#api-endpoints)
-   [Examples](#examples)
-   [Status workflow](#status-workflow)
-   [Error handling and logging](#error-handling-and-logging)
-   [Testing](#testing)
-   [GitHub preparation](#github-preparation)
-   [Future improvements](#future-improvements)

## Features

-   CRUD operations for incidents.
-   Filter by status and priority.
-   Pagination and sorting.
-   Assign incidents to team members.
-   Controlled incident status transitions.
-   DTO-based request/response models and request validation.
-   Global exception handling with JSON errors.
-   SLF4J/Logback logging to console and a file.
-   Swagger UI / OpenAPI documentation.
-   JUnit 5, Mockito, and MockMvc test setup.

## Technology stack

  Technology                    Purpose
  ----------------------------- ------------------------
  Java 21                       Language/runtime
  Spring Boot                   Application framework
  Spring Web                    REST endpoints
  Spring Data JPA / Hibernate   Persistence and ORM
  MySQL                         Relational database
  Jakarta Validation            Request validation
  Maven                         Build and dependencies
  SLF4J / Logback               Logging
  Springdoc OpenAPI             API documentation
  JUnit 5 / Mockito             Unit tests and mocks
  MockMvc                       Controller tests
  Postman                       Manual API testing

## Architecture

``` text
Postman / Swagger UI
        |
        v
IncidentController
        |
        v
IncidentService
        |
        v
IncidentRepository
        |
        v
      MySQL
```

Supporting components: - **DTOs** define API request and response
shapes. - **GlobalExceptionHandler** maps handled exceptions to HTTP
responses. - **OpenApiConfig** supplies API documentation metadata. -
**Tests** check service logic and HTTP behavior.

## Project structure

``` text
incident-management-api/
├── src/
│   ├── main/
│   │   ├── java/com/example/incident_management_api/
│   │   │   ├── config/OpenApiConfig.java
│   │   │   ├── controller/IncidentController.java
│   │   │   ├── dto/
│   │   │   │   ├── IncidentRequest.java
│   │   │   │   ├── IncidentResponse.java
│   │   │   │   ├── IncidentStatusRequest.java
│   │   │   │   └── IncidentAssignmentRequest.java
│   │   │   ├── exception/
│   │   │   │   ├── ErrorResponse.java
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   └── ResourceNotFoundException.java
│   │   │   ├── model/
│   │   │   │   ├── Incident.java
│   │   │   │   ├── IncidentEnvironment.java
│   │   │   │   ├── IncidentPriority.java
│   │   │   │   └── IncidentStatus.java
│   │   │   ├── repository/IncidentRepository.java
│   │   │   ├── service/IncidentService.java
│   │   │   └── IncidentManagementApiApplication.java
│   │   └── resources/application.properties
│   └── test/java/com/example/incident_management_api/
│       ├── service/IncidentServiceTest.java
│       └── controller/IncidentControllerTest.java
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

Actual test files may vary as you continue development.

## Prerequisites

-   JDK 21
-   MySQL Server
-   IntelliJ IDEA or another Java IDE
-   Maven or the included Maven Wrapper
-   Postman (optional; Swagger UI can also be used)

Check the tools:

``` powershell
java -version
.\mvnw.cmd -version
```

## Configuration

Example `src/main/resources/application.properties`:

``` properties
spring.application.name=incident-management-api
server.port=8084

spring.datasource.url=jdbc:mysql://localhost:3306/incident_management_db
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

logging.level.root=INFO
logging.level.com.example.incident_management_api=DEBUG
logging.level.org.springframework.web=WARN
logging.level.org.hibernate.SQL=DEBUG
logging.file.name=logs/incident-management.log
```

Preserve your working configuration if it differs. Set credentials in
the terminal used to launch the app:

``` powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your-local-mysql-password"
```

Replace the example password with your local password. If launching
through IntelliJ, add the variables to the Run Configuration as well.
Never commit real credentials or tokens.

## Database setup

Start MySQL and create the database:

``` sql
CREATE DATABASE incident_management_db;
```

The application uses JPA/Hibernate to create or update mapped tables
when `spring.jpa.hibernate.ddl-auto=update` is enabled. This is
convenient for local development; production systems should use reviewed
schema migrations, for example Flyway or Liquibase.

### Main incident fields

  Field                       Purpose
  --------------------------- ---------------------------------------------
  `id`                        Generated identifier
  `title`                     Short summary
  `description`               Incident details
  `priority`                  `LOW`, `MEDIUM`, `HIGH`, `CRITICAL`
  `status`                    `OPEN`, `IN_PROGRESS`, `RESOLVED`, `CLOSED`
  `serviceName`               Affected service, e.g. Kafka or MySQL
  `environment`               `DEV`, `UAT`, `PRODUCTION`
  `assignedTo`                Assignee name
  `createdAt` / `updatedAt`   Timestamps

## Run the application

1.  Start MySQL.
2.  Set `DB_USERNAME` and `DB_PASSWORD`.
3.  Open the project in IntelliJ and reload Maven.
4.  Run the main Spring Boot application class, or run:

``` powershell
.\mvnw.cmd spring-boot:run
```

Base application URL: `http://localhost:8084`

Build the project:

``` powershell
.\mvnw.cmd clean package
```

## Swagger/OpenAPI

If Springdoc is configured and the application starts, open: - Swagger
UI: `http://localhost:8084/swagger-ui/index.html` - OpenAPI JSON:
`http://localhost:8084/v3/api-docs`

Use Swagger's **Try it out** feature to send requests and inspect
responses.

## API endpoints

Base API URL: `http://localhost:8084/api/incidents`

  Method   Endpoint                       Purpose                   Typical success
  -------- ------------------------------ ------------------------- ------------------
  POST     `/api/incidents`               Create incident           `201 Created`
  GET      `/api/incidents`               List/filter incidents     `200 OK`
  GET      `/api/incidents/{id}`          Get one incident          `200 OK`
  PUT      `/api/incidents/{id}`          Update incident details   `200 OK`
  DELETE   `/api/incidents/{id}`          Delete incident           `204 No Content`
  PATCH    `/api/incidents/{id}/status`   Change status             `200 OK`
  PATCH    `/api/incidents/{id}/assign`   Assign incident           `200 OK`

### Filtering, pagination, sorting

``` text
GET /api/incidents?status=OPEN
GET /api/incidents?priority=CRITICAL
GET /api/incidents?status=OPEN&priority=HIGH
GET /api/incidents?page=0&size=5
GET /api/incidents?page=0&size=5&sort=createdAt,desc
GET /api/incidents?status=OPEN&priority=HIGH&page=0&size=5&sort=createdAt,desc
```

Page numbers typically start at zero. Enum values must match exactly,
e.g. `IN_PROGRESS`.

## Examples

### Create an incident

`POST /api/incidents`, with `Content-Type: application/json`:

``` json
{
  "title": "Kafka Consumer Lag",
  "description": "Consumer lag exceeded the critical threshold",
  "priority": "CRITICAL",
  "serviceName": "Kafka",
  "environment": "PRODUCTION"
}
```

Expected on success: `201 Created`, with the generated ID, initial
`OPEN` status, incident fields, and timestamps where configured.

### Update incident details

`PUT /api/incidents/1`:

``` json
{
  "title": "Kafka Consumer Lag - Investigating",
  "description": "The support team is investigating high consumer lag",
  "priority": "HIGH",
  "serviceName": "Kafka",
  "environment": "PRODUCTION"
}
```

Use an existing ID. The current service implementation preserves status
and assignee when updating details.

### Assign an incident

`PATCH /api/incidents/1/assign`:

``` json
{
  "assignedTo": "Lalitha"
}
```

### Change status

`PATCH /api/incidents/1/status`:

``` json
{
  "status": "IN_PROGRESS"
}
```

### Delete an incident

`DELETE /api/incidents/1`. Use an existing ID; successful deletion
returns `204 No Content`.

## Status workflow

The current service rules allow:

``` text
OPEN -> IN_PROGRESS -> RESOLVED -> CLOSED
```

-   `OPEN` can move to `IN_PROGRESS`.
-   `IN_PROGRESS` can move to `RESOLVED`.
-   `RESOLVED` can move to `CLOSED`.
-   Other transitions are rejected.

This is a basic workflow. Production requirements may include reopening,
cancellation, authorization, audit history, and concurrency safeguards.

## Error handling and logging

Expected handled errors include: \| Scenario \| Expected status \|
\|---\|---\| \| Incident not found \| `404 Not Found` \| \| Validation
failure \| `400 Bad Request` \| \| Malformed JSON / invalid enum \|
`400 Bad Request` \| \| Invalid status transition \| `400 Bad Request`
\| \| Unexpected server error \| `500 Internal Server Error` \|

Example not-found response:

``` json
{
  "status": 404,
  "message": "Incident with id 999 not found"
}
```

Example validation response:

``` json
{
  "status": 400,
  "message": "Request validation failed",
  "errors": {
    "title": "Title is required"
  }
}
```

The precise response shape depends on the `GlobalExceptionHandler` in
the project.

The app uses SLF4J with Spring Boot's default logging implementation.
The configured `logs/incident-management.log` path is relative to the
application's working directory. Avoid logging secrets or sensitive
data; configure retention and rotation before production use.

## Testing

Run all tests:

``` powershell
.\mvnw.cmd test
```

Or, if the Maven wrapper is unavailable:

``` powershell
mvn test
```

-   **Service tests** use JUnit 5 and Mockito to test business behavior
    while mocking the repository.
-   **Controller tests** use `@WebMvcTest` and MockMvc to exercise HTTP
    behavior without requiring a running MySQL database.

Test scenarios may include creation, filtering, updating, deletion,
assignment, status transitions, not-found responses, validation, and
HTTP status codes. Run the suite and review the Maven reports before
claiming tests passed.

The `pom.xml` should include this test dependency, normally managed by
the Spring Boot parent:

``` xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

If MockMvc imports cannot be resolved, reload Maven and verify
dependency downloads. Keep test annotations compatible with the
project's Spring Boot version.

## GitHub preparation

### 1. Create `.gitignore`

Place this in the project root:

``` gitignore
target/
.idea/
*.iml
.vscode/
logs/
*.log
.env
.env.*
!.env.example
.DS_Store
Thumbs.db
```

Keep `mvnw`, `mvnw.cmd`, and Maven wrapper files under version control
if used by your project.

### 2. Check for secrets

Before pushing, ensure the repository contains no database passwords,
API keys, tokens, local environment files, or sensitive logs. If a
secret was committed, rotate it; deleting it from the latest version
alone may not remove it from Git history.

### 3. Initialize and commit

If this project is not already a Git repository:

``` powershell
git init
git status
git add .
git commit -m "Build incident management REST API"
```

If Git is already initialized, don't initialize it again unnecessarily;
inspect `git status` first.

### 4. Create the GitHub repository

Create a repository named `incident-management-api`. Suggested
description:

> Spring Boot REST API for incident tracking, assignment, filtering, and
> status management.

If your local project already has a README, create the remote repository
without adding a separate README to avoid an unnecessary merge.

Connect your local repository using your actual repository URL:

``` powershell
git branch -M main
git remote add origin https://github.com/YOUR-USERNAME/incident-management-api.git
git push -u origin main
```

Replace `YOUR-USERNAME` with your GitHub username. If `origin` already
exists, inspect it with `git remote -v` before changing it.

### 5. Portfolio checklist

-   Verify a clean build and run the test suite.
-   Confirm the README matches the code actually present.
-   Add Swagger UI or Postman screenshots if helpful.
-   Document local setup and required environment variables.
-   Never include real secrets or credentials.
-   Don't commit `target/`, logs, or personal files.
-   Add a license only if you intend to distribute the project under
    that license.

## Future improvements

-   More service and controller test coverage.
-   JaCoCo test coverage reports.
-   Database migrations with Flyway.
-   Structured JSON logging and log rotation.
-   Incident audit history and comments.
-   Search by service name and environment.
-   Authentication and role-based authorization.
-   Assignee validation against a team-member table.
-   Pagination limits and sort-field allowlisting.
-   CI pipeline to build and test pull requests.

## Learning outcomes

This project practices REST API design, layered architecture, DTOs,
validation, JPA/MySQL persistence, pagination, sorting, filtering,
workflow rules, exception handling, logging, OpenAPI documentation,
automated testing, and GitHub project presentation.

**Maintainer:** Add your name and profile link if desired.

**License:** Add a license (for example, MIT) only if you choose to
publish the project under that license.
