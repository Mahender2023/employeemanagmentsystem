package com.enterprise.ems.employee_management_system.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorResponseDTO {
    private String message;
    private String errorCode;
    private LocalDateTime timestamp;

    public ErrorResponseDTO(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
    } 
}