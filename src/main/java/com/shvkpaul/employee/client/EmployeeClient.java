package com.shvkpaul.employee.client;

import com.shvkpaul.employee.client.model.Employee;
import com.shvkpaul.employee.client.model.EmployeeResponse;
import com.shvkpaul.employee.client.model.GenericResponse;
import com.shvkpaul.employee.model.EmployeeRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static com.shvkpaul.employee.mapper.EmployeeMapper.toEmployee;

@Component
public class EmployeeClient {

    private final RestClient restClient;

    public EmployeeClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        return restClient.post()
            .uri("/api/employees")
            .body(toEmployee(employeeRequest))
            .retrieve()
            .body(new ParameterizedTypeReference<>() {
            });
    }

    public EmployeeResponse getEmployee(Long id) {
        return restClient.get()
            .uri("/api/employees/{id}", id)
            .retrieve()
            .body(new ParameterizedTypeReference<>() {
            });
    }

    public GenericResponse deleteEmployee(Long id) {
        return restClient.delete()
            .uri("/api/employees/{id}", id)
            .retrieve()
            .body(new ParameterizedTypeReference<>() {
            });
    }

    public EmployeeResponse updateEmployee(EmployeeRequest employeeRequest,Long id) {
        return restClient.put()
            .uri("/api/employees/{id}", id)
            .body(toEmployee(employeeRequest))
            .retrieve()
            .body(new ParameterizedTypeReference<>() {
            });
    }
}
