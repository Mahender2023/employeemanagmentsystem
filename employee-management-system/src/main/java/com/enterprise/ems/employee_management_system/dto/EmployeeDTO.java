package com.enterprise.ems.employee_management_system.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Long employeeId;
    private String employeeName;
    private String email;
    private String designation;
    private Double salary;
    private Long departmentId;
    private String departmentName;
    private List<Long> bonusIds;
}