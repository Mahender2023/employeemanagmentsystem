package com.enterprise.ems.employee_management_system.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BonusRequestDTO {
    private Double amount;
    private String bonusType;
    private LocalDate awardedDate;
    private Long employeeId; // Only need employee ID for assignment
}