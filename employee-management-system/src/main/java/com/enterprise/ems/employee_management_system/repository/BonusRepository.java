package com.enterprise.ems.employee_management_system.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.enterprise.ems.employee_management_system.entity.Bonus;


//Spring Data JPA converts method names into JPQL queries, and Hibernate executes it as SQL.
@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long> {
	
     //Find all bonuses for a specific employee (nested property traversal through relationship(_))
    List<Bonus> findByEmployee_EmployeeId(Long employeeId);
    
    // Find all bonuses by bonus type
    List<Bonus> findByBonusType(String bonusType);
    
   // Delete all bonuses for a specific employee
    void deleteByEmployee_EmployeeId(Long employeeId);
}

//JpaRepository extends PagingAndSortingRepository extends CrudRepository
//CrudRepository Purpose: Provides basic CRUD operations (Create, Read, Update, Delete) for an entity.
//save(S entity) → Insert or update entity
//findById(ID id) → Find entity by primary key
//findAll() → Retrieve all entities
//deleteById(ID id) → Delete by primary key
//existsById(ID id) → Check if entity exists
