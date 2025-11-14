package com.enterprise.ems.employee_management_system.entity;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//JPA (Java Persistence API) is just a specification — a set  annotations (like @Entity, @OneToMany)
//Hibernate is one of the implementations of that specification.

@Entity // Marks this class as a JPA entity
@Table(name = "department") 
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "department_name", nullable = false, length = 100)
    private String departmentName;

    @Column(name = "location", length = 100)
    private String location;

    @Column(name = "manager_name", length = 100)
    private String managerName;
    
    @Column(name = "budget")
    private Double budget;

    // One-to-Many Relationship with Employee
    // LAZY fetch - only loads bonuses when explicitly accessed. Query does not join tables(no join).
    //SELECT * FROM employee WHERE department_id = ? it holds the employee entity but not yet loadded data
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Employee> employees;
}
