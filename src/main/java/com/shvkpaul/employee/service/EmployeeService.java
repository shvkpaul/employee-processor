package com.shvkpaul.employee.service;

import com.shvkpaul.employee.client.EmployeeClient;
import com.shvkpaul.employee.client.model.GenericResponse;
import com.shvkpaul.employee.model.EmployeeRequest;
import com.shvkpaul.employee.model.EmployeeResponse;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import static com.shvkpaul.employee.mapper.EmployeeMapper.toEmployeeClientRequest;
import static com.shvkpaul.employee.mapper.EmployeeMapper.toEmployeeResponse;

@Service
@Log4j2
public class EmployeeService {

    private final EmployeeClient employeeClient;

    public EmployeeService(EmployeeClient employeeClient) {
        this.employeeClient = employeeClient;
    }

    @Retryable(
        value = { Exception.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 20000)
    )
    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        return toEmployeeResponse(employeeClient.createEmployee(toEmployeeClientRequest(employeeRequest)));
    }

    @Retryable(
        value = { Exception.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 20000)
    )
    public EmployeeResponse getEmployee(Long employeeId) {
        return toEmployeeResponse(employeeClient.getEmployee(employeeId));
    }

    @Retryable(
        value = { Exception.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 20000)
    )
    public EmployeeResponse updateEmployee(Long employeeId, EmployeeRequest employeeRequest) {
        return toEmployeeResponse(employeeClient.updateEmployee(employeeId, toEmployeeClientRequest(employeeRequest)));
    }

    @Retryable(
        value = { Exception.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 20000)
    )
    public GenericResponse deleteEmployee(Long employeeId) {
        log.info("Deleting employee with id: {}", employeeId);
        return employeeClient.deleteEmployee(employeeId);
    }
}
