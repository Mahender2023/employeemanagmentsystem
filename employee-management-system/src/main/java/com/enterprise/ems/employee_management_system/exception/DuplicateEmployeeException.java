package com.enterprise.ems.employee_management_system.exception;

public class DuplicateEmployeeException extends RuntimeException {
    public DuplicateEmployeeException(String message) {
        super(message);
    }
}