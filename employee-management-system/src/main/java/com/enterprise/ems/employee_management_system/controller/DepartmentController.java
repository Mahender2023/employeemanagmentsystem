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

import com.enterprise.ems.employee_management_system.dto.DepartmentBasicDTO;
import com.enterprise.ems.employee_management_system.dto.DepartmentDTO;
import com.enterprise.ems.employee_management_system.dto.DepartmentRequestDTO;
import com.enterprise.ems.employee_management_system.service.DepartmentService;

@RestController // @Controller(Marks the class as web controller and @RequestBody Automatically serializes the returned objects into JSON)
@RequestMapping("/api/departments") //Base path for all department endpoints
public class DepartmentController {

    private static final Logger logger = LogManager.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    // CREATE - Add new department
    // POST http://localhost:8080/api/departments
    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentRequestDTO requestDTO) {
        logger.info("API http://localhost:8080/api/departments Request: Create department - {}", requestDTO.getDepartmentName());
        DepartmentDTO createdDepartment = departmentService.createDepartment(requestDTO);
        logger.info("API Response: Department created successfully with ID: {}", createdDepartment.getDepartmentId());
        return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
    }

    //READ - Get all departments
    // GET http://localhost:8080/api/departments
     
    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        logger.info("API http://localhost:8080/api/departments Request: Get all departments");
        List<DepartmentDTO> departments = departmentService.getAllDepartments();
        logger.info("API Response: Returning {} departments", departments.size());
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }
    
    //READ - Get all departments
    // GET http://localhost:8080/api/departments
    @GetMapping("/basic") //Lazy fetch
    public ResponseEntity<List<DepartmentBasicDTO>> getAllDepartmentsBasic() {
        logger.info("API http://localhost:8080/api/departments Request: Get all departments (basic)");
        List<DepartmentBasicDTO> departments = departmentService.getAllDepartmentsBasic();
        logger.info("API Response: Returning {} basic departments", departments.size());
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    // READ - Get department by ID
    // GET http://localhost:8080/api/departments/{id}
     
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id) {
        logger.info("API http://localhost:8080/api/departments/{id} Request: Get department by ID: {}", id);
        DepartmentDTO department = departmentService.getDepartmentById(id);
        logger.info("API Response: Department found with ID: {}", id);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    // UPDATE - Update existing department
    // PUT http://localhost:8080/api/departments/{id}
     
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(
            @PathVariable Long id,
            @RequestBody DepartmentRequestDTO requestDTO) {
        logger.info("API http://localhost:8080/api/departments/{id} Request: Update department with ID: {}", id);
        DepartmentDTO updatedDepartment = departmentService.updateDepartment(id, requestDTO);
        logger.info("API Response: Department updated successfully with ID: {}", id);
        return new ResponseEntity<>(updatedDepartment, HttpStatus.OK);
    }

    // DELETE - Delete department by ID
    // DELETE http://localhost:8080/api/departments/{id}
     
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long id) {
        logger.info("API http://localhost:8080/api/departments/{id} Request: Delete department with ID: {}", id);
        departmentService.deleteDepartment(id);
        logger.info("API Response: Department deleted successfully with ID: {}", id);
        return ResponseEntity.ok("Department deleted successfully with ID: " + id);
    }
}