package com.enterprise.ems.employee_management_system.entity;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity  // Marks this class as a JPA entity
@Table(name = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "employee_name", nullable = false, length = 100)
    private String employeeName;

    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @Column(name = "designation", length = 100)
    private String designation;

    @Column(name = "salary")
    private Double salary;
    
 
    // Many-to-One Relationship with Department
    //EAGER fetch - loads department when employee is loaded
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    // One-to-Many Relationship with Bonus
    // LAZY fetch - only loads bonuses when explicitly accessed. Query does not join tables(no join).
    //It Runs two querys -> session,isintialization=false/true, parent class id
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<Bonus> bonuses;
}