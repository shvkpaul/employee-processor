package com.shvkpaul.employee.service;

import com.shvkpaul.employee.client.EmployeeClient;
import com.shvkpaul.employee.client.model.GenericResponse;
import com.shvkpaul.employee.model.EmployeeRequest;
import com.shvkpaul.employee.model.EmployeeResponse;
import org.springframework.stereotype.Service;

import static com.shvkpaul.employee.mapper.EmployeeMapper.toEmployeeClientRequest;
import static com.shvkpaul.employee.mapper.EmployeeMapper.toEmployeeResponse;

@Service
public class EmployeeService {

    private final EmployeeClient employeeClient;

    public EmployeeService(EmployeeClient employeeClient) {
        this.employeeClient = employeeClient;
    }

    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        return toEmployeeResponse(employeeClient.createEmployee(toEmployeeClientRequest(employeeRequest)));
    }

    public EmployeeResponse getEmployee(Long employeeId) {
        return toEmployeeResponse(employeeClient.getEmployee(employeeId));
    }

    public EmployeeResponse updateEmployee(Long employeeId, EmployeeRequest employeeRequest) {
        return toEmployeeResponse(employeeClient.updateEmployee(employeeId, toEmployeeClientRequest(employeeRequest)));
    }

    public GenericResponse deleteEmployee(Long employeeId) {
        return employeeClient.deleteEmployee(employeeId);
    }
}
