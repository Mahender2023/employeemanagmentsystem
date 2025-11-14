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
import com.enterprise.ems.employee_management_system.dto.EmployeeDTO;
import com.enterprise.ems.employee_management_system.dto.EmployeeRequestDTO;
import com.enterprise.ems.employee_management_system.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")  //Base path for all department endpoints
public class EmployeeController {

    private static final Logger logger = LogManager.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    // CREATE - Add new employee
    // POST http://localhost:8080/api/employees
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeRequestDTO requestDTO) {
        logger.info("API http://localhost:8080/api/employees Request: Create employee - {} for department ID: {}", 
                    requestDTO.getEmployeeName(), requestDTO.getDepartmentId());
        EmployeeDTO createdEmployee = employeeService.createEmployee(requestDTO);
        logger.info("API Response: Employee created successfully with ID: {}", createdEmployee.getEmployeeId());
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    //READ - Get all employees
    //GET http://localhost:8080/api/employees
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
    	logger.info("Fetching the details of all employees");
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        logger.info("API Response: Returning {} employees", employees.size());
        return ResponseEntity.ok(employees);
    }

    //READ - Get employee by ID
    //GET http://localhost:8080/api/employees/{id}
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        logger.info("API Response: Employee found with ID: {}", id);
        return ResponseEntity.ok(employee);
    }
    
    //READ - Get all employees by department
    //GET http://localhost:8080/api/employees/department/{departmentId}    
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByDepartment(@PathVariable Long departmentId) {
        List<EmployeeDTO> employees = employeeService.getEmployeesByDepartment(departmentId);
        logger.info("API Response: Returning {} employees for department ID: {}", employees.size(), departmentId);
        return ResponseEntity.ok(employees);
    }

    //UPDATE - Update existing employee
    //PUT http://localhost:8080/api/employees/{id}
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequestDTO requestDTO) {
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(id, requestDTO);
        logger.info("API Response: Employee updated successfully with ID: {}", id);
        return ResponseEntity.ok(updatedEmployee);
    }

    //DELETE - Delete employee by ID
    //DELETE http://localhost:8080/api/employees/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        logger.info("API Response: Employee deleted successfully with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}