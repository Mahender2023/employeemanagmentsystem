package com.enterprise.ems.employee_management_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enterprise.ems.employee_management_system.entity.Department;


//Spring Data JPA converts method names into JPQL queries, and Hibernate executes it as SQL.

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    
    //Custom query method to find department by name  
    Optional<Department> findByDepartmentName(String departmentName);
    
    // Custom query method to check if department exists by name
    boolean existsByDepartmentName(String departmentName);
}