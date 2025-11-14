package com.enterprise.ems.employee_management_system.service;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.enterprise.ems.employee_management_system.dto.EmployeeDTO;
import com.enterprise.ems.employee_management_system.dto.EmployeeRequestDTO;
import com.enterprise.ems.employee_management_system.entity.Department;
import com.enterprise.ems.employee_management_system.entity.Employee;
import com.enterprise.ems.employee_management_system.exception.DuplicateEmployeeException;
import com.enterprise.ems.employee_management_system.exception.EmployeeDeletionException;
import com.enterprise.ems.employee_management_system.exception.EmployeeNotFoundException;
import com.enterprise.ems.employee_management_system.mapper.EntityMapper;
import com.enterprise.ems.employee_management_system.repository.DepartmentRepository;
import com.enterprise.ems.employee_management_system.repository.EmployeeRepository;


@Service
@Transactional
public class EmployeeService {

    private static final Logger logger = LogManager.getLogger(EmployeeService.class);

    @Autowired
    private final EmployeeRepository employeeRepository;

    @Autowired
    private final DepartmentRepository departmentRepository;
    
    @Autowired
    private final EntityMapper mapper;
    
    public EmployeeService(EmployeeRepository employeeRepository,DepartmentRepository departmentRepository,EntityMapper mapper) {
    	this.employeeRepository=employeeRepository;
    	this.departmentRepository=departmentRepository;
    	this.mapper=mapper;
    }
    
   
    //Create a new employee 
    public EmployeeDTO createEmployee(EmployeeRequestDTO requestDTO) {
        logger.info("Creating employee with email: {}", requestDTO.getEmail());
        
        
        if (employeeRepository.existsByEmail(requestDTO.getEmail())) {
            logger.warn("Duplicate employee creation attempt for email: {}", requestDTO.getEmail());
            throw new DuplicateEmployeeException("Employee already exists with email: " + requestDTO.getEmail());
        }

        Department department = departmentRepository.findById(requestDTO.getDepartmentId())
                .orElseThrow(() -> {
                    logger.error("Department not found with ID: {}", requestDTO.getDepartmentId());
                    return new EmployeeNotFoundException("Department not found with ID: " + requestDTO.getDepartmentId());
                });

        Employee employee = mapper.toEmployeeEntity(requestDTO);
        employee.setDepartment(department);
        Employee saved = employeeRepository.save(employee);

        logger.info("Employee created successfully with ID: {} in department ID: {}", saved.getEmployeeId(), department.getDepartmentId());
        return mapper.toEmployeeDTO(saved);
    }

    // Get all employees    
    @Transactional(readOnly = true)
    public List<EmployeeDTO> getAllEmployees() {
        logger.info("Fetching all employees");
        List<EmployeeDTO> employees = mapper.toEmployeeDTOList(employeeRepository.findAll());
        logger.info("Total employees fetched: {}", employees.size());
        return employees;
    }

    // Get employee by ID 
    @Transactional(readOnly = true)
    public EmployeeDTO getEmployeeById(Long id) {
        logger.info("Fetching employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Employee not found with ID: {}", id);
                    return new EmployeeNotFoundException("Employee not found with ID: " + id);
                });
        logger.info("Employee fetched successfully: {} ({})", employee.getEmployeeName(), employee.getEmployeeId());
        return mapper.toEmployeeDTO(employee);
    }

    // Get all employees by department
    @Transactional(readOnly = true)
    public List<EmployeeDTO> getEmployeesByDepartment(Long departmentId) {
        logger.info("Fetching employees for department ID: {}", departmentId);
        List<EmployeeDTO> employees = mapper.toEmployeeDTOList(employeeRepository.findByDepartment_DepartmentId(departmentId));
        logger.info("Total employees fetched for department {}: {}", departmentId, employees.size());
        return employees;
    }

    // Update employee
    public EmployeeDTO updateEmployee(Long id, EmployeeRequestDTO requestDTO) {
        logger.info("Updating employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Employee not found with ID: {}", id);
                    return new EmployeeNotFoundException("Employee not found with ID: " + id);
                });

        employee.setEmployeeName(requestDTO.getEmployeeName());
        employee.setEmail(requestDTO.getEmail());
        employee.setDesignation(requestDTO.getDesignation());
        employee.setSalary(requestDTO.getSalary());

        if (requestDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findById(requestDTO.getDepartmentId())
                    .orElseThrow(() -> {
                        logger.error("Department not found with ID: {}", requestDTO.getDepartmentId());
                        return new EmployeeNotFoundException("Department not found with ID: " + requestDTO.getDepartmentId());
                    });
            employee.setDepartment(department);
            logger.info("Employee department updated to ID: {}", department.getDepartmentId());
        }

        Employee updated = employeeRepository.save(employee);
        logger.info("Employee updated successfully: {} ({})", updated.getEmployeeName(), updated.getEmployeeId());
        return mapper.toEmployeeDTO(updated);
    }

    // Delete employee by ID
    public void deleteEmployee(Long id) {
        logger.info("Deleting employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Employee not found with ID: {}", id);
                    return new EmployeeNotFoundException("Employee not found with ID: " + id);
                });

        if (employee.getBonuses() != null && !employee.getBonuses().isEmpty()) {
            logger.error("Cannot delete employee with active bonuses. Employee ID: {}", id);
            throw new EmployeeDeletionException("Cannot delete employee with active bonuses. Please remove bonuses first.");
        }

        employeeRepository.deleteById(id);
        logger.info("Employee deleted successfully with ID: {}", id);
    }
}
