package com.shvkpaul.employee.client;

import com.shvkpaul.employee.client.model.EmployeeClientRequest;
import com.shvkpaul.employee.client.model.EmployeeClientResponse;
import com.shvkpaul.employee.client.model.GenericResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class EmployeeClient {

    private final RestClient restClient;

    public EmployeeClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public EmployeeClientResponse createEmployee(EmployeeClientRequest employeeClientRequest) {
        return restClient.post()
            .uri("/api/employees")
            .body(employeeClientRequest)
            .retrieve()
            .body(new ParameterizedTypeReference<>() {
            });
    }

    public EmployeeClientResponse getEmployee(Long id) {
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

    public EmployeeClientResponse updateEmployee(Long id, EmployeeClientRequest employeeClientRequest) {
        return restClient.put()
            .uri("/api/employees/{id}", id)
            .body(employeeClientRequest)
            .retrieve()
            .body(new ParameterizedTypeReference<>() {
            });
    }
}
