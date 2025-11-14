
Step 1: Spring Boot Configuration
The project was created using Spring Initializr(https://start.spring.io/)

Dependencies Added : pom.xml (Maven configuration)
Spring Web → Build RESTful APIs  
Spring Data JPA → Database operations via ORM  
MySQL Driver → Connect to MySQL database  
Lombok → Auto-generate boilerplate code  
Logback excluded and Log4j2 added

Step 2: Create MySQL Database (application.properties)
CREATE DATABASE employee_management_db;
Configure Database Connection:
spring.datasource.url=jdbc:mysql://localhost:3306/employee_management_db
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD

Build the Application : mvn clean install
Run the Application : mvn spring-boot:run

Step 3: Entity Relationships
Department (1) ----< (Many) Employee (1) ----< (Many) Bonus
Department.java - 5 fields with One-to-Many to Employee (LAZY)
Employee.java - 5 fields with Many-to-One to Department (EAGER) and One-to-Many to Bonus (LAZY)
Bonus.java - 5 fields with Many-to-One to Employee (EAGER)


Step 4: Logging Configuration Logging (Log4j2)
Log4j2 configuration with log4j2.xml
Multiple log levels (TRACE, DEBUG, INFO, WARN, ERROR, FATAL)
TRACE: Most detailed debugging information
DEBUG: Detailed debugging for development
INFO: General informational messages
WARN: Warning messages for potentially harmful situations
ERROR: Error events that might allow app to continue
FATAL: Very severe errors leading to application abort

Console Appender: Writes to console
File Appender: Writes to logs/application.log

Rolls over daily
Max file size: 10MB
Keeps last 30 log files

Step 4: Spring Data JPA (Repository Interfaces)
CRUD operations using JpaRepository
DepartmentRepository.java - with custom query methods
EmployeeRepository.java - with custom query methods
BonusRepository.java - with custom query methods

Step 4: Service Layer
DepartmentService.java - Business logic with comprehensive logging
EmployeeService.java - Business logic with comprehensive logging
BonusService.java - Business logic with comprehensive logging

Step 6: REST Controllers
RESTful APIs with @RestController
HTTP methods: GET, POST, PUT, DELETE
DepartmentController.java - Full CRUD APIs
EmployeeController.java - Full CRUD APIs
BonusController.java - Full CRUD APIs