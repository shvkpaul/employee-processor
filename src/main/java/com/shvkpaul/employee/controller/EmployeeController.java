package com.shvkpaul.employee.controller;

import com.shvkpaul.employee.client.model.GenericResponse;
import com.shvkpaul.employee.error.RoleValidator;
import com.shvkpaul.employee.model.EmployeeRequest;
import com.shvkpaul.employee.model.EmployeeResponse;
import com.shvkpaul.employee.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    public EmployeeResponse getEmployee(
        @PathVariable Long id,
        @RequestHeader("Role") String role,
        @RequestHeader Map<String, String> headers) {
        RoleValidator.validateRoleHeader(headers, role);
        RoleValidator.checkUserRole(role);
        return employeeService.getEmployee(id);
    }

    @PostMapping
    public EmployeeResponse createEmployee(
        @RequestBody EmployeeRequest employeeRequest,
        @RequestHeader("Role") String role,
        @RequestHeader Map<String, String> headers) {
        RoleValidator.validateRoleHeader(headers, role);
        RoleValidator.checkAdminRole(role);
        return employeeService.createEmployee(employeeRequest);
    }

    @PutMapping("/{id}")
    public EmployeeResponse updateEmployee(
        @RequestBody EmployeeRequest employeeRequest,
        @PathVariable Long id,
        @RequestHeader("Role") String role,
        @RequestHeader Map<String, String> headers) {
        RoleValidator.validateRoleHeader(headers, role);
        RoleValidator.checkUserRole(role);
        return employeeService.updateEmployee(id, employeeRequest);
    }

    @DeleteMapping("/{id}")
    public GenericResponse deleteEmployee(
        @PathVariable Long id,
        @RequestHeader("Role") String role,
        @RequestHeader Map<String, String> headers) {
        RoleValidator.validateRoleHeader(headers, role);
        RoleValidator.checkAdminRole(role);
        return employeeService.deleteEmployee(id);
    }
}
