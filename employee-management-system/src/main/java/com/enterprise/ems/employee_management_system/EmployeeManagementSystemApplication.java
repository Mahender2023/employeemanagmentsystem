package com.enterprise.ems.employee_management_system;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeManagementSystemApplication {
	
	// Logger instance for logging application event
    private static final Logger logger = LogManager.getLogger(EmployeeManagementSystemApplication.class);

	public static void main(String[] args) {
		logger.info("========================================");
        logger.info("Starting Employee Management System...");
      
		SpringApplication.run(EmployeeManagementSystemApplication.class, args);
		logger.info("========================================");
        logger.info("Employee Management System Started Successfully!");
	}

}
