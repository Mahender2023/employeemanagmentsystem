package com.enterprise.ems.employee_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDTO {
    private String employeeName;
    private String email;
    private String designation;
    private Double salary;
    private Long departmentId; 
    
}
