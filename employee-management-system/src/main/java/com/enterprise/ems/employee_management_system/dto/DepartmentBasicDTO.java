package com.enterprise.ems.employee_management_system.dto;

import lombok.Data;

@Data
public class DepartmentBasicDTO {
    private Long departmentId;
    private String departmentName;
    private String location;
    private String managerName;
}
