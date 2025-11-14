package com.enterprise.ems.employee_management_system.exception;

public class BonusNotFoundException extends RuntimeException {
    public BonusNotFoundException(String message) {
        super(message);
    }
}