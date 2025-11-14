package com.enterprise.ems.employee_management_system.service;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enterprise.ems.employee_management_system.dto.BonusDTO;
import com.enterprise.ems.employee_management_system.dto.BonusRequestDTO;
import com.enterprise.ems.employee_management_system.entity.Bonus;
import com.enterprise.ems.employee_management_system.entity.Employee;
import com.enterprise.ems.employee_management_system.exception.BonusNotFoundException;
import com.enterprise.ems.employee_management_system.exception.EmployeeNotFoundForBonusException;
import com.enterprise.ems.employee_management_system.mapper.EntityMapper;
import com.enterprise.ems.employee_management_system.repository.BonusRepository;
import com.enterprise.ems.employee_management_system.repository.EmployeeRepository;

@Service
@Transactional
public class BonusService {

    private static final Logger logger = LogManager.getLogger(BonusService.class);

    @Autowired
    private final BonusRepository bonusRepository;

    @Autowired
    private final EmployeeRepository employeeRepository;
    
    @Autowired
    private final EntityMapper mapper;
    
    public BonusService(BonusRepository bonusRepository,EmployeeRepository employeeRepository,EntityMapper mapper) {
    	this.bonusRepository=bonusRepository;
    	this.employeeRepository=employeeRepository;
    	this.mapper=mapper;
    }

    // Create a new bonus
    public BonusDTO createBonus(BonusRequestDTO requestDTO) {
        logger.info("Creating bonus of type '{}' with amount {} for employee ID: {}",
                requestDTO.getBonusType(), requestDTO.getAmount(), requestDTO.getEmployeeId());

        Employee employee = employeeRepository.findById(requestDTO.getEmployeeId())
                .orElseThrow(() -> {
                    logger.error("Employee not found for bonus creation with ID: {}", requestDTO.getEmployeeId());
                    return new EmployeeNotFoundForBonusException(
                            "Employee not found with ID: " + requestDTO.getEmployeeId());
                });

        Bonus bonus = mapper.toBonusEntity(requestDTO);
        bonus.setEmployee(employee);
        Bonus saved = bonusRepository.save(bonus);

        logger.info("Bonus created successfully with ID: {} for employee: {}", saved.getBonusId(), employee.getEmployeeName());
        return mapper.toBonusDTO(saved);
    }

    // Get all bonuses
    @Transactional(readOnly = true)
    public List<BonusDTO> getAllBonuses() {
        logger.info("Fetching all bonuses");
        List<BonusDTO> bonuses = mapper.toBonusDTOList(bonusRepository.findAll());
        logger.info("Total bonuses fetched: {}", bonuses.size());
        return bonuses;
    }

    // Get bonus by ID
    @Transactional(readOnly = true)
    public BonusDTO getBonusById(Long id) {
        logger.info("Fetching bonus with ID: {}", id);
        Bonus bonus = bonusRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Bonus not found with ID: {}", id);
                    return new BonusNotFoundException("Bonus not found with ID: " + id);
                });
        logger.info("Bonus fetched successfully: ID {} for employee {}", bonus.getBonusId(), bonus.getEmployee().getEmployeeName());
        return mapper.toBonusDTO(bonus);
    }

    // Get all bonuses for an employee
    @Transactional(readOnly = true)
    public List<BonusDTO> getBonusesByEmployee(Long employeeId) {
        logger.info("Fetching all bonuses for employee ID: {}", employeeId);
        List<BonusDTO> bonuses = mapper.toBonusDTOList(bonusRepository.findByEmployee_EmployeeId(employeeId));
        logger.info("Total bonuses fetched for employee ID {}: {}", employeeId, bonuses.size());
        return bonuses;
    }

    // Update existing bonus
    public BonusDTO updateBonus(Long id, BonusRequestDTO requestDTO) {
        logger.info("Updating bonus with ID: {}", id);
        Bonus bonus = bonusRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Bonus not found for update with ID: {}", id);
                    return new BonusNotFoundException("Bonus not found with ID: " + id);
                });

        bonus.setAmount(requestDTO.getAmount());
        bonus.setBonusType(requestDTO.getBonusType());
        bonus.setAwardedDate(requestDTO.getAwardedDate());

        Bonus updated = bonusRepository.save(bonus);
        logger.info("Bonus updated successfully with ID: {}", id);
        return mapper.toBonusDTO(updated);
    }

    // Delete bonus by ID
    public void deleteBonus(Long id) {
        logger.info("Deleting bonus with ID: {}", id);

        try {
            if (!bonusRepository.existsById(id)) {
                logger.warn("Cannot delete - Bonus not found with ID: {}", id);
                throw new RuntimeException("Bonus not found with ID: " + id);
            }

            bonusRepository.deleteById(id);
            logger.info("Bonus deleted successfully with ID: {}", id);

        } catch (Exception e) {
            logger.error("Error deleting bonus with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }
}
