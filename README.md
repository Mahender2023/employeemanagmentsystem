# Employee Management System

A Spring Boot application to manage Employees, Departments, and Bonuses with RESTful APIs, database integration, and comprehensive logging.

---

## **Step 1: Spring Boot Configuration**

The project was created using [Spring Initializr](https://start.spring.io/).

**Dependencies Added (pom.xml):**
- **Spring Web** → Build RESTful APIs  
- **Spring Data JPA** → Database operations via ORM  
- **MySQL Driver** → Connect to MySQL database  
- **Lombok** → Auto-generate boilerplate code  
- **Logback excluded** and **Log4j2 added** for logging

---

## **Step 2: Create MySQL Database**

Create the database:

```sql
CREATE DATABASE employee_management_db;


Configure database connection in application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/employee_management_db
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run

Step 3: Entity Relationships

Relationships:

Department (1) → (Many) Employee

Employee (1) → (Many) Bonus

Entities:

Department.java

5 fields

@OneToMany to Employee (LAZY)

Employee.java

5 fields

@ManyToOne to Department (EAGER)

@OneToMany to Bonus (LAZY)

Bonus.java

5 fields

@ManyToOne to Employee (EAGER)

Step 4: Logging Configuration (Log4j2)

log4j2.xml configuration includes multiple log levels:

TRACE: Most detailed debugging information

DEBUG: Detailed debugging for development

INFO: General informational messages

WARN: Warning messages for potentially harmful situations

ERROR: Error events that might allow app to continue

FATAL: Very severe errors leading to application abort

Appenders:

Console Appender: Writes logs to console

File Appender: Writes logs to logs/application.log

Rolls over daily

Max file size: 10MB

Keeps last 30 log files

Step 5: Spring Data JPA (Repository Interfaces)

CRUD operations implemented using JpaRepository:

DepartmentRepository.java → Custom query methods

EmployeeRepository.java → Custom query methods

BonusRepository.java → Custom query methods

Step 6: Service Layer

Business logic implemented with comprehensive logging:

DepartmentService.java

EmployeeService.java

BonusService.java

Step 7: REST Controllers

RESTful APIs using @RestController and HTTP methods:

DepartmentController.java → Full CRUD APIs

EmployeeController.java → Full CRUD APIs

BonusController.java → Full CRUD APIs

Usage

Run the Spring Boot application

Access APIs using tools like Postman or curl

Check logs in the console or in logs/application.log

Technologies Used

Java 17 / 20 (Spring Boot compatible)

Spring Boot

Spring Data JPA

MySQL

Log4j2

Maven

Lombok

REST API Endpoints
1. Department APIs

| HTTP Method | URL                      | Description                 |
| ----------- | ------------------------ | --------------------------- |
| POST        | `/api/departments`       | Create a new department     |
| GET         | `/api/departments`       | Get all departments (full)  |
| GET         | `/api/departments/basic` | Get all departments (basic) |
| GET         | `/api/departments/{id}`  | Get department by ID        |
| PUT         | `/api/departments/{id}`  | Update department by ID     |
| DELETE      | `/api/departments/{id}`  | Delete department by ID     |

2. Employee APIs

| HTTP Method | URL                                        | Description                        |
| ----------- | ------------------------------------------ | ---------------------------------- |
| POST        | `/api/employees`                           | Create a new employee              |
| GET         | `/api/employees`                           | Get all employees                  |
| GET         | `/api/employees/{id}`                      | Get employee by ID                 |
| PUT         | `/api/employees/{id}`                      | Update employee by ID              |
| DELETE      | `/api/employees/{id}`                      | Delete employee by ID              |
| GET         | `/api/employees/department/{departmentId}` | Get all employees by department ID |

3. Bonus APIs
| HTTP Method | URL                                  | Description                             |
| ----------- | ------------------------------------ | --------------------------------------- |
| POST        | `/api/bonuses`                       | Create a new bonus                      |
| GET         | `/api/bonuses`                       | Get all bonuses                         |
| GET         | `/api/bonuses/{id}`                  | Get bonus by ID                         |
| PUT         | `/api/bonuses/{id}`                  | Update bonus by ID                      |
| DELETE      | `/api/bonuses/{id}`                  | Delete bonus by ID                      |
| GET         | `/api/bonuses/employee/{employeeId}` | Get all bonuses for a specific employee |

