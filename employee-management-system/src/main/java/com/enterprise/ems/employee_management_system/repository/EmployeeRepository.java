package com.enterprise.ems.employee_management_system.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.enterprise.ems.employee_management_system.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    // Find employee by email
    Optional<Employee> findByEmail(String email);
    
    // Find all employees by department ID (nested property traversal through relationship(_)) department.departmentID
    List<Employee> findByDepartment_DepartmentId(Long departmentId);
    
    //Find employees by designation
    List<Employee> findByDesignation(String designation);
    
    // Check if employee exists by email
    boolean existsByEmail(String email);
}
