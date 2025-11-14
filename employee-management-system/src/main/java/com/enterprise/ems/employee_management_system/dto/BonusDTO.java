package com.enterprise.ems.employee_management_system.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//maps entities to dto's that contain only the fields that we need to send through API's.
//DTO's break cycle of infinite JSON serilization problem. we control structure how we want. 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BonusDTO {
    private Long bonusId;
    private Double amount;
    private String bonusType;
    private LocalDate awardedDate;
    
    // Employee information (not the full object, just key details)
    private Long employeeId;
    private String employeeName;
}