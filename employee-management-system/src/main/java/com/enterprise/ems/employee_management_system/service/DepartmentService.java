package com.enterprise.ems.employee_management_system.service;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enterprise.ems.employee_management_system.dto.DepartmentBasicDTO;
import com.enterprise.ems.employee_management_system.dto.DepartmentDTO;
import com.enterprise.ems.employee_management_system.dto.DepartmentRequestDTO;
import com.enterprise.ems.employee_management_system.entity.Department;
import com.enterprise.ems.employee_management_system.exception.DepartmentDeletionException;
import com.enterprise.ems.employee_management_system.exception.DepartmentNotFoundException;
import com.enterprise.ems.employee_management_system.exception.DuplicateDepartmentException;
import com.enterprise.ems.employee_management_system.mapper.EntityMapper;
import com.enterprise.ems.employee_management_system.repository.DepartmentRepository;

@Service
@Transactional 
public class DepartmentService {

    private static final Logger logger = LogManager.getLogger(DepartmentService.class);

    @Autowired
    private final DepartmentRepository departmentRepository;
    
    @Autowired
    private final  EntityMapper mapper;
    
    public DepartmentService(DepartmentRepository departmentRepository,EntityMapper mapper) {
    	this.departmentRepository=departmentRepository;
    	this.mapper=mapper;
    }

    //Create a new department
    public DepartmentDTO createDepartment(DepartmentRequestDTO requestDTO) {
        logger.info("Creating new department: {}", requestDTO.getDepartmentName());

        if (departmentRepository.existsByDepartmentName(requestDTO.getDepartmentName())) {
            logger.warn("Duplicate department: {}", requestDTO.getDepartmentName());
            throw new DuplicateDepartmentException("Department already exists with name: " + requestDTO.getDepartmentName());
        }

        Department department = mapper.toDepartmentEntity(requestDTO);
        Department savedDepartment = departmentRepository.save(department);
        logger.info("Department created successfully with ID: {}", savedDepartment.getDepartmentId());
        return mapper.toDepartmentDTO(savedDepartment);
    }

    // Get all departments   
    @Transactional(readOnly = true)
    public List<DepartmentDTO> getAllDepartments() {
        logger.trace("Entering getAllDepartments()");
        List<Department> departments = departmentRepository.findAll();
        logger.info("Fetched {} departments", departments.size());
        return mapper.toDepartmentDTOList(departments);
    }
    
    //Get all departments without employees
    //Read all (basic)
    @Transactional(readOnly = true)
    public List<DepartmentBasicDTO> getAllDepartmentsBasic() {
        logger.trace("Entering getAllDepartmentsBasic()");
        List<Department> departments = departmentRepository.findAll();
        logger.info("Fetched {} basic departments", departments.size());
        return mapper.toBasicDepartmentDTOList(departments);
    }

    // Get department by ID   
    @Transactional(readOnly = true)
    public DepartmentDTO getDepartmentById(Long id) {
        logger.debug("Fetching department by ID: {}", id);
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with ID: " + id));
        logger.info("Department found with ID: {}", id);
        return mapper.toDepartmentDTO(department);
    }

    //Update existing department  
    public DepartmentDTO updateDepartment(Long id, DepartmentRequestDTO requestDTO) {
        logger.info("Updating department with ID: {}", id);

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with ID: " + id));

        department.setDepartmentName(requestDTO.getDepartmentName());
        department.setLocation(requestDTO.getLocation());
        department.setManagerName(requestDTO.getManagerName());

        Department updatedDepartment = departmentRepository.save(department);
        logger.info("Department updated successfully with ID: {}", id);
        return mapper.toDepartmentDTO(updatedDepartment);
    }

    //Delete department by ID
    public void deleteDepartment(Long id) {
        logger.info("Deleting department with ID: {}", id);

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with ID: " + id));

        if (department.getEmployees() != null && !department.getEmployees().isEmpty()) {
        	logger.error("Attempting to delete department with {} active employees! ID: {}",
        	        department.getEmployees().size(), id);
            throw new DepartmentDeletionException(" Failed to delete department. It may have associated employees.");
        }

        departmentRepository.deleteById(id);
        logger.info("Department deleted successfully with ID: {}", id);
    }
}