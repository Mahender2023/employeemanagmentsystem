package com.enterprise.ems.employee_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRequestDTO {
    private String departmentName;
    private String location;
    private String managerName;
	public Double getBudget;
}
