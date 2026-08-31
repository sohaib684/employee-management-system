# Employee Management System (EMS)

A production-style **Employee Management System REST API** built with Java and Spring Boot, implementing secure authentication, employee CRUD operations, pagination, sorting, searching, file management, email integration, testing, Dockerization, and application monitoring.

The project was developed with a focus on **real-world backend development practices** and interview-relevant concepts.

---

## 🚀 Tech Stack

### Backend

* Java 21
* Spring Boot
* Spring Web / REST API
* Spring Data JPA
* Hibernate
* Spring Security
* JWT Authentication
* Bean Validation

### Database

* MySQL 8

### Testing

* JUnit 5
* Mockito
* Spring Boot Integration Testing

### DevOps / Deployment

* Maven
* Docker
* Docker Compose
* Spring Boot Actuator
* Git / GitHub

### Additional

* DTO Pattern
* Global Exception Handling
* Logging with SLF4J
* File Upload & Download
* Email Service
* Spring Profiles
* Configuration using Environment Variables
* Pagination, Sorting & Searching

---

# ✨ Features

## 🔐 Authentication & Authorization

* User registration
* Secure password hashing using BCrypt
* User login
* JWT token generation
* Stateless authentication
* JWT request filtering
* Role-based authorization
* USER and ADMIN roles

### Authentication Flow

```text
Client
   ↓
Login
   ↓
AuthenticationManager
   ↓
CustomUserDetailsService
   ↓
Database
   ↓
Password Verification
   ↓
JWT Generation
   ↓
Client
```

---

# 👨‍💼 Employee Management

### Employee CRUD

* Create employee
* Get employee by ID
* Get all employees
* Update employee
* Delete employee

### Search & Filtering

Employees can be searched using:

* Name
* Department
* City

### Pagination & Sorting

The API supports:

* Page number
* Page size
* Sorting field
* Ascending / descending order

Example:

```text
/api/employees?page=0&size=10&sortBy=name&direction=asc
```

---

# 🖼️ Profile Image Management

Employee profile images can be:

* Uploaded
* Stored on the server
* Associated with an employee
* Downloaded through the API

The employee entity stores relevant file information such as:

* File name
* File type
* File path

---

# 📧 Email Service

The application integrates with **Gmail SMTP** for sending emails.

Configuration is handled through environment variables rather than hardcoding credentials.

Example configuration:

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
```

---

# ⚠️ Exception Handling

The project implements centralized exception handling using:

* Custom exceptions
* `@ControllerAdvice`
* Global exception handler
* Structured API error responses

Example:

```text
EmployeeNotFoundException
```

This prevents repetitive exception-handling logic inside individual controllers.

---

# 📦 DTO Architecture

The application uses separate DTOs instead of exposing entities directly for employee APIs.

### Request DTO

```text
EmployeeRequestDto
```

### Response DTO

```text
EmployeeResponseDto
```

This provides better API design and separation between the persistence and API layers.

---

# 📊 API Response

The application uses a generic response structure for APIs where applicable:

```text
ApiResponse<T>
```

This provides a consistent response format across the application.

---

# 📝 Validation

Request validation is implemented using Jakarta Bean Validation.

Examples include:

* Required fields
* Email validation
* Input constraints
* Invalid request handling

---

# 📄 Pagination

Spring Data's `Pageable` functionality is used for efficient pagination.

Example:

```java
Page<EmployeeResponseDto>
```

This avoids loading all employees into memory for large datasets.

---

# 🔍 Search Implementation

The project uses Spring Data JPA repository methods for employee searching.

Examples include:

```java
findByNameContainingIgnoreCase()
```

```java
findByDepartmentIgnoreCase()
```

```java
findByCityIgnoreCase()
```

---

# 🛡️ Security Architecture

```text
Client
   │
   ▼
Spring Security
   │
   ▼
JWT Authentication Filter
   │
   ├── No Token → Authentication Required
   │
   └── Token
         │
         ▼
      JWT Validation
         │
         ▼
   UserDetailsService
         │
         ▼
      User Roles
         │
         ▼
   Authorization
```

Protected employee operations are controlled using roles.

| Operation       | USER | ADMIN |
| --------------- | ---: | ----: |
| GET Employees   |    ✅ |     ✅ |
| POST Employee   |    ❌ |     ✅ |
| PUT Employee    |    ❌ |     ✅ |
| DELETE Employee |    ❌ |     ✅ |

---

# 🧪 Testing

The project includes:

* Unit testing
* Mockito-based service testing
* Spring Boot integration testing

Testing technologies:

```text
JUnit 5
Mockito
Spring Boot Test
```

The project has been tested using Maven:

```bash
mvn test
```

---

# 📈 Spring Boot Actuator

Spring Boot Actuator is integrated for application monitoring.

Currently exposed endpoints include:

```text
/actuator/health
/actuator/info
```

Example:

```http
GET /actuator/health
```

Example response:

```json
{
  "status": "UP"
}
```

The health endpoint also monitors application components such as:

* MySQL database
* Disk space
* Mail service
* Application ping
* SSL

---

# ⚙️ Spring Profiles

The application uses Spring Profiles to separate environment-specific configuration.

Example:

```text
application.properties
application-dev.properties
```

Development configuration can be activated using:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

---

# 🔐 Environment Variables

Sensitive configuration is not hardcoded into the application.

Examples:

```text
DB_USERNAME
DB_PASSWORD
JWT_SECRET
MAIL_USERNAME
MAIL_PASSWORD
```

This keeps credentials outside the source code.

---

# 🐳 Docker

The application has also been containerized using Docker.

Docker configuration includes:

```text
Dockerfile
docker-compose.yml
```

Docker Compose can run:

```text
Spring Boot Application
        +
      MySQL
```

The application was also tested successfully in a Docker Compose environment.

---

# 🗂️ Project Structure

```text
employee-management
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.sohaib.employeemanagement
│   │   │
│   │   │   ├── config
│   │   │   ├── controller
│   │   │   ├── dto
│   │   │   ├── entity
│   │   │   ├── exception
│   │   │   ├── repository
│   │   │   ├── security
│   │   │   └── service
│   │   │       └── impl
│   │   │
│   │   └── resources
│   │       ├── application.properties
│   │       └── application-dev.properties
│   │
│   └── test
│       └── java
│
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

---

# 🔄 Application Architecture

```text
                    ┌─────────────────┐
                    │     Client      │
                    │ Postman / Web   │
                    └────────┬────────┘
                             │
                             ▼
                    ┌─────────────────┐
                    │   Controller    │
                    └────────┬────────┘
                             │
                             ▼
                    ┌─────────────────┐
                    │     Service     │
                    └────────┬────────┘
                             │
                             ▼
                    ┌─────────────────┐
                    │   Repository    │
                    └────────┬────────┘
                             │
                             ▼
                    ┌─────────────────┐
                    │     MySQL       │
                    └─────────────────┘
```

Supporting components:

```text
Spring Security
      │
      ▼
JWT Authentication
      │
      ▼
Authorization

FileStorageService
      │
      ▼
Profile Images

Email Service
      │
      ▼
Gmail SMTP

Spring Boot Actuator
      │
      ▼
Application Monitoring
```

---

# ▶️ Running the Project Locally

## 1. Clone the repository

```bash
git clone <https://github.com/sohaib684/employee-management-system>
```

```bash
cd employee-management
```

## 2. Configure MySQL

Create the database:

```sql
CREATE DATABASE employee_db;
```

Make sure MySQL is running on:

```text
localhost:3306
```

## 3. Configure environment variables

Set:

```text
DB_USERNAME
DB_PASSWORD
JWT_SECRET
MAIL_USERNAME
MAIL_PASSWORD
```

## 4. Activate the development profile

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

The application runs on:

```text
http://localhost:8080
```

---

# 🔑 Main API Endpoints

## Authentication

```text
POST /api/auth/register
POST /api/auth/login
```

## Employees

```text
GET    /api/employees
GET    /api/employees/{id}
POST   /api/employees
PUT    /api/employees/{id}
DELETE /api/employees/{id}
```

## Search

```text
GET /api/employees/search
```

## Actuator

```text
GET /actuator/health
GET /actuator/info
```

---

# 🧰 Useful Commands

### Build

```bash
mvn clean package
```

### Run tests

```bash
mvn test
```

### Run application

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Docker build

```bash
docker build -t employee-management-app .
```

### Docker Compose

```bash
docker compose up -d
```

---

# 🎯 Interview Concepts Demonstrated

This project demonstrates practical knowledge of:

* Java 21
* OOP
* Collections
* Exception Handling
* Generics
* Stream API
* REST API development
* Spring Boot
* Spring MVC
* Spring Data JPA
* Hibernate
* MySQL
* DTO pattern
* Validation
* Global Exception Handling
* Pagination
* Sorting
* Searching
* Spring Security
* JWT
* BCrypt
* Role-Based Authorization
* File Handling
* Email Integration
* Spring Profiles
* Environment Variables
* JUnit 5
* Mockito
* Integration Testing
* Docker
* Docker Compose
* Spring Boot Actuator
* Logging
* Maven
* Git / GitHub

---

# 📌 Future Improvements

Possible future enhancements include:

* Redis caching
* Kafka event-driven architecture
* API Gateway
* Service Discovery
* Microservices architecture
* CI/CD pipeline
* AWS deployment
* Cloud storage for profile images
* Centralized logging
* Distributed tracing

---

# 👨‍💻 Author

**Sohaib Alam**

Java Full Stack Developer | Backend Developer

Built as a production-style backend project to demonstrate modern Java and Spring Boot development practices.
