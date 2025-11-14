package com.enterprise.ems.employee_management_system.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.enterprise.ems.employee_management_system.dto.BonusDTO;
import com.enterprise.ems.employee_management_system.dto.BonusRequestDTO;
import com.enterprise.ems.employee_management_system.service.BonusService;

@RestController
@RequestMapping("/api/bonuses")  //Base path for all department endpoints
public class BonusController {

    private static final Logger logger = LogManager.getLogger(BonusController.class);

    @Autowired
    private BonusService bonusService;

    // CREATE - Add new bonus
    // POST http://localhost:8080/api/bonuses  
    @PostMapping
    public ResponseEntity<BonusDTO> createBonus(@RequestBody BonusRequestDTO requestDTO) {
        logger.info("API Request: Create bonus - {} for employee ID: {}", requestDTO.getBonusType(), requestDTO.getEmployeeId());
        BonusDTO createdBonus = bonusService.createBonus(requestDTO);
        logger.info("API Response: Bonus created with ID: {}", createdBonus.getBonusId());
        //Exception handile by GlobalHandler(exception->service->controller->springboot->globalhandler(Handils))
        return new ResponseEntity<>(createdBonus, HttpStatus.CREATED);
    }


    // READ - Get all bonuses
    // GET http://localhost:8080/api/bonuses
     
    @GetMapping
    public ResponseEntity<List<BonusDTO>> getAllBonuses() {
        List<BonusDTO> bonuses = bonusService.getAllBonuses();
        logger.info("API Response: Returning {} bonuses", bonuses.size());
        return ResponseEntity.ok(bonuses);
    }

    // READ - Get bonus by ID
    // GET http://localhost:8080/api/bonuses/{id}   
    @GetMapping("/{id}")
    public ResponseEntity<BonusDTO> getBonusById(@PathVariable Long id) {
        BonusDTO bonus = bonusService.getBonusById(id);
        logger.info("API Response: Bonus found with ID: {}", id);
        return ResponseEntity.ok(bonus);
    }

    // READ - Get all bonuses for an employee
    // GET http://localhost:8080/api/bonuses/employee/{employeeId}     
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<BonusDTO>> getBonusesByEmployee(@PathVariable Long employeeId) {
        List<BonusDTO> bonuses = bonusService.getBonusesByEmployee(employeeId);
        logger.info("API Response: Returning {} bonuses for employee ID: {}", bonuses.size(), employeeId);
        return ResponseEntity.ok(bonuses);
    }

    // UPDATE - Update existing bonus
    // PUT http://localhost:8080/api/bonuses/{id}  
    @PutMapping("/{id}")
    public ResponseEntity<BonusDTO> updateBonus(@PathVariable Long id, @RequestBody BonusRequestDTO requestDTO) {
        BonusDTO updatedBonus = bonusService.updateBonus(id, requestDTO);
        logger.info("API Response: Bonus updated with ID: {}", id);
        return ResponseEntity.ok(updatedBonus);
    }

    // DELETE - Delete bonus by ID
    // DELETE http://localhost:8080/api/bonuses/{id}    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBonus(@PathVariable Long id) {
        bonusService.deleteBonus(id);
        logger.info("API Response: Bonus deleted with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}