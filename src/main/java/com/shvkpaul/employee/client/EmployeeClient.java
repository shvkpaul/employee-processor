package com.shvkpaul.employee.client;

import com.shvkpaul.employee.client.model.EmployeeClientRequest;
import com.shvkpaul.employee.client.model.EmployeeClientResponse;
import com.shvkpaul.employee.client.model.GenericResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class EmployeeClient {

    private final WebClient webClient;

    public EmployeeClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public EmployeeClientResponse createEmployee(EmployeeClientRequest employeeClientRequest) {
        return webClient.post()
            .uri("/api/employees")
            .bodyValue(employeeClientRequest)
            .retrieve()
            .bodyToMono(EmployeeClientResponse.class).block();
    }

    public EmployeeClientResponse getEmployee(Long id) {
        return webClient.get()
            .uri("/api/employees/{id}", id)
            .retrieve()
            .bodyToMono(EmployeeClientResponse.class).block();
    }

    public GenericResponse deleteEmployee(Long id) {
        return webClient.delete()
            .uri("/api/employees/{id}", id)
            .retrieve()
            .bodyToMono(GenericResponse.class).block();
    }

    public EmployeeClientResponse updateEmployee(Long id, EmployeeClientRequest employeeClientRequest) {
        return webClient.put()
            .uri("/api/employees/{id}", id)
            .bodyValue(employeeClientRequest)
            .retrieve()
            .bodyToMono(EmployeeClientResponse.class).block();
    }
}
