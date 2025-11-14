package com.enterprise.ems.employee_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

// maps entities to dto's that contain only the fields that we need to send through API's.
// DTO's break cycle of infinite JSON serilization problem. we control structure how we want. 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {
    private Long departmentId;
    private String departmentName;
    private String location;
	private  double setBudget;
    private String managerName;
    private List<String> employeeNames;
    private List<Long> employeeIds;

}