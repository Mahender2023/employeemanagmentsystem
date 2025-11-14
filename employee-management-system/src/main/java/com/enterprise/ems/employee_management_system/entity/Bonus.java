package com.enterprise.ems.employee_management_system.entity;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//JPA (Java Persistence API) is just a specification — a set  annotations (like @Entity, @OneToMany)
//Hibernate is one of the implementations of that specification.

@Entity  // Marks this class as a JPA entity
@Table(name = "bonus")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Bonus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bonus_id")
    private Long bonusId;

    @Column(name = "amount", nullable = false)// Constraints for database columns.
    private Double amount;

    @Column(name = "bonus_type", length = 50)// Constraints for database columns.
    private String bonusType;

    @Column(name = "awarded_date")
    private LocalDate awardedDate;

    // Many-to-One Relationship with Employee
    //EAGER fetch - loads employee when bonus is loaded
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
}