package com.shvkpaul.employee.controller;

import com.shvkpaul.employee.client.EmployeeClient;
import com.shvkpaul.employee.client.model.EmployeeResponse;
import com.shvkpaul.employee.client.model.GenericResponse;
import com.shvkpaul.employee.model.EmployeeRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeClient employeeClient;

    public EmployeeController(EmployeeClient employeeClient) {
        this.employeeClient = employeeClient;
    }

    @GetMapping("/{id}")
    public EmployeeResponse getEmployee(@PathVariable Long id) {
        return employeeClient.getEmployee(id);
    }

    @PostMapping
    public EmployeeResponse createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return employeeClient.createEmployee(employeeRequest);
    }

    @PutMapping("/{id}")
    public EmployeeResponse updateEmployee(
        @RequestBody EmployeeRequest employeeRequest,
        @PathVariable Long id) {
        return employeeClient.updateEmployee(employeeRequest,id);
    }

    @DeleteMapping("/{id}")
    public GenericResponse deleteEmployee(@PathVariable Long id) {
        return employeeClient.deleteEmployee(id);
    }
}
