package com.enterprise.ems.employee_management_system.exception;

import java.time.LocalDateTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.enterprise.ems.employee_management_system.dto.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);
    
    //ResponseEntity.status(HttpStatus.NOT_FOUND)
    //.body(new ErrorResponseDTO(ex.getMessage(), "DEPT_NOT_FOUND"))
    //Spring calling this automatically when an exception is thrown.
    
    // Department exceptions
    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleDepartmentNotFound(DepartmentNotFoundException ex) {
        logger.warn("Department not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(ex.getMessage(), "DEPT_NOT_FOUND"));
    }

    @ExceptionHandler(DuplicateDepartmentException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicateDepartment(DuplicateDepartmentException ex) {
        logger.warn("Duplicate department: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDTO(ex.getMessage(), "DEPT_DUPLICATE"));
    }
    @ExceptionHandler(DepartmentDeletionException.class)
    public ResponseEntity<ErrorResponseDTO> handleDepartmentDeletion(DepartmentDeletionException ex) {
        logger.warn("Department deletion failed: {}", ex.getMessage());
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage(), "DEPT_DELETE_CONFLICT");
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(error);
    }


    // Employee exceptions
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmployeeNotFound(EmployeeNotFoundException ex) {
        logger.warn("Employee not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(ex.getMessage(), "EMP_NOT_FOUND"));
    }

    @ExceptionHandler(DuplicateEmployeeException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicateEmployee(DuplicateEmployeeException ex) {
        logger.warn("Duplicate employee: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDTO(ex.getMessage(), "EMP_DUPLICATE"));
    }
    @ExceptionHandler(EmployeeDeletionException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmployeeDeletion(EmployeeDeletionException ex) {
        logger.warn("Employee deletion failed: {}", ex.getMessage());
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage(), "EMP_DELETE_CONFLICT");
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(error);
    }
    
    @ExceptionHandler(BonusNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleBonusNotFound(BonusNotFoundException ex) {
        logger.warn("Bonus not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(ex.getMessage(), "BONUS_NOT_FOUND"));
    }

    @ExceptionHandler(EmployeeNotFoundForBonusException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmployeeNotFoundForBonus(EmployeeNotFoundForBonusException ex) {
        logger.warn("Employee not found for bonus: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(ex.getMessage(), "EMP_NOT_FOUND_FOR_BONUS"));
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        logger.error("Invalid argument: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> handleRuntimeException(RuntimeException ex) {
        logger.fatal("Unrecoverable exception occurred", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO("Internal Server Error", "RUNTIME_ERROR"));
    }


    // Generic handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneral(Exception ex) {
        logger.error("Unhandled exception", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO("Internal server error. Please contact support.", "INTERNAL_ERROR"));
    }
    
}
