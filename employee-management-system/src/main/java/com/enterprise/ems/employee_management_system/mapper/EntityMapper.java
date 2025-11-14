package com.enterprise.ems.employee_management_system.mapper;


import com.enterprise.ems.employee_management_system.dto.*;
import com.enterprise.ems.employee_management_system.entity.Bonus;
import com.enterprise.ems.employee_management_system.entity.Department;
import com.enterprise.ems.employee_management_system.entity.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {
	
	//Department
	// Convert Department Request DTO to Entity (saving in database)
    public Department toDepartmentEntity(DepartmentRequestDTO requestDTO) {
    	
        if (requestDTO == null) {
            return null;
        }
        Department department = new Department();
        department.setDepartmentName(requestDTO.getDepartmentName());
        department.setLocation(requestDTO.getLocation());
        department.setBudget(requestDTO.getGetBudget());
        department.setManagerName(requestDTO.getManagerName());
        return department;
    }

    // Convert Department Entity to DTO (sending back as response to user(client))
    public DepartmentDTO toDepartmentDTO(Department department) {
        if (department == null) {
            return null;
        }

        DepartmentDTO dto = new DepartmentDTO();
        dto.setDepartmentId(department.getDepartmentId());
        dto.setDepartmentName(department.getDepartmentName());
        dto.setLocation(department.getLocation());
        dto.setSetBudget(department.getBudget());
        dto.setManagerName(department.getManagerName());

        if (department.getEmployees() != null && !department.getEmployees().isEmpty()) {
            List<Long> employeeIds = new ArrayList<>();
            List<String> employeeNames = new ArrayList<>();

            department.getEmployees().forEach(emp -> {
                employeeIds.add(emp.getEmployeeId());
                employeeNames.add(emp.getEmployeeName());
            });

            dto.setEmployeeIds(employeeIds);
            dto.setEmployeeNames(employeeNames);
        }

        return dto;
    }
    
    public DepartmentBasicDTO toBasicDepartmentDTO(Department department) {
        if (department == null) return null;

        DepartmentBasicDTO dto = new DepartmentBasicDTO();
        dto.setDepartmentId(department.getDepartmentId());
        dto.setDepartmentName(department.getDepartmentName());
        dto.setLocation(department.getLocation());
        dto.setManagerName(department.getManagerName());
        return dto;
    }
    
    
    
    //Employee
    //Convert Employee Request DTO to Entity 
    public Employee toEmployeeEntity(EmployeeRequestDTO requestDTO) {
        if (requestDTO == null) {
            return null;
        }
        
        Employee employee = new Employee();
        employee.setEmployeeName(requestDTO.getEmployeeName());
        employee.setEmail(requestDTO.getEmail());
        employee.setDesignation(requestDTO.getDesignation());
        employee.setSalary(requestDTO.getSalary());
        
        return employee;
    }
    
    //Convert Employee Entity to DTO
    public EmployeeDTO toEmployeeDTO(Employee employee) {
        if (employee == null) {
            return null;
        }
        
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setEmployeeName(employee.getEmployeeName());
        dto.setEmail(employee.getEmail());
        dto.setDesignation(employee.getDesignation());
        dto.setSalary(employee.getSalary());
        
        // Add department info without loading full department object
        if (employee.getDepartment() != null) {
            dto.setDepartmentId(employee.getDepartment().getDepartmentId());
            dto.setDepartmentName(employee.getDepartment().getDepartmentName());
        }
        
        // Only include bonus IDs, not full bonus objects (handles lazy loading)
        if (employee.getBonuses() != null) {
            try {
                List<Long> bonusIds = employee.getBonuses().stream()
                        .map(Bonus::getBonusId)
                        .collect(Collectors.toList());
                dto.setBonusIds(bonusIds);
            } catch (Exception e) {
                // If lazy loading fails, set empty list
                dto.setBonusIds(List.of());
            }
        }     
        return dto;
    }
    
    // Convert Bonus Request DTO to Entity
    public Bonus toBonusEntity(BonusRequestDTO requestDTO) {
        if (requestDTO == null) {
            return null;
        }
        
        Bonus bonus = new Bonus();
        bonus.setAmount(requestDTO.getAmount());
        bonus.setBonusType(requestDTO.getBonusType());
        bonus.setAwardedDate(requestDTO.getAwardedDate());
       
        return bonus;
    }
   
    //Convert Bonus Entity to DTO 
    public BonusDTO toBonusDTO(Bonus bonus) {
        if (bonus == null) {
            return null;
        }
        
        BonusDTO dto = new BonusDTO();
        dto.setBonusId(bonus.getBonusId());
        dto.setAmount(bonus.getAmount());
        dto.setBonusType(bonus.getBonusType());
        dto.setAwardedDate(bonus.getAwardedDate());
        
        // Add employee info without loading full employee object
        if (bonus.getEmployee() != null) {
            dto.setEmployeeId(bonus.getEmployee().getEmployeeId());
            dto.setEmployeeName(bonus.getEmployee().getEmployeeName());
        }
        return dto;
    }
 
    // Convert list of Department entities to DTOs
    public List<DepartmentDTO> toDepartmentDTOList(List<Department> departments) {
        return departments.stream()
                .map(this::toDepartmentDTO)
                .collect(Collectors.toList());
    }
    public List<DepartmentBasicDTO> toBasicDepartmentDTOList(List<Department> departments) {
        return departments.stream()
                .map(this::toBasicDepartmentDTO)
                .collect(Collectors.toList());
    }
    // Convert list of Employee entities to DTOs
    public List<EmployeeDTO> toEmployeeDTOList(List<Employee> employees) {
        return employees.stream()
                .map(this::toEmployeeDTO)
                .collect(Collectors.toList());
    }
    // Convert list of Bonus entities to DTOs
    public List<BonusDTO> toBonusDTOList(List<Bonus> bonuses) {
        return bonuses.stream()
                .map(this::toBonusDTO)
                .collect(Collectors.toList());
    }
}