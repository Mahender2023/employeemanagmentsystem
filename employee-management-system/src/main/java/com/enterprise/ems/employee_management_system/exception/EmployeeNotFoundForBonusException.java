package com.enterprise.ems.employee_management_system.exception;

public class EmployeeNotFoundForBonusException extends RuntimeException {
    public EmployeeNotFoundForBonusException(String message) {
        super(message);
    }
}