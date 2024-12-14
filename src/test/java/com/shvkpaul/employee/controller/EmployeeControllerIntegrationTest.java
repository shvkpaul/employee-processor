package com.shvkpaul.employee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shvkpaul.employee.client.model.GenericResponse;
import com.shvkpaul.employee.model.EmployeeRequest;
import com.shvkpaul.employee.model.EmployeeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String ADMIN_AUTH = "Basic YWRtaW46YWRtaW4="; // admin:admin
    private static final String USER_AUTH = "Basic dXNlcjp1c2Vy"; // user:user

    @Test
    void shouldGetEmployee() {
        Long employeeId = 1L;

        this.webTestClient
            .get()
            .uri("/employees/{id}", employeeId)
            .header(HttpHeaders.AUTHORIZATION, USER_AUTH)
            .header("Role", "USER")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(EmployeeResponse.class)
            .consumeWith(response -> {
                EmployeeResponse employeeResponse = response.getResponseBody();
                assertThat(employeeResponse).isNotNull();
                assertThat(employeeResponse.getId()).isEqualTo(employeeId);
                assertThat(employeeResponse.getFirstname()).isNotEmpty();
                assertThat(employeeResponse.getSurname()).isNotEmpty();
            });
    }

    @Test
    void shouldCreateEmployee() throws Exception {
        EmployeeRequest employeeRequest = new EmployeeRequest("S", "Paul");

        this.webTestClient
            .post()
            .uri("/employees")
            .header(HttpHeaders.AUTHORIZATION, ADMIN_AUTH)
            .header("Role", "ADMIN")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(objectMapper.writeValueAsString(employeeRequest))
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(EmployeeResponse.class)
            .consumeWith(response -> {
                EmployeeResponse employeeResponse = response.getResponseBody();
                assertThat(employeeResponse).isNotNull();
                assertThat(employeeResponse.getFirstname()).isEqualTo(employeeRequest.getFirstname());
                assertThat(employeeResponse.getSurname()).isEqualTo(employeeRequest.getSurname());
            });
    }

    @Test
    void shouldCreateAndUpdateEmployee() throws Exception {
        // Create Employee
        EmployeeRequest employeeRequest = new EmployeeRequest("S", "Paul");
        EmployeeResponse createdEmployee = this.webTestClient
            .post()
            .uri("/employees")
            .header(HttpHeaders.AUTHORIZATION, ADMIN_AUTH)
            .header("Role", "ADMIN")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(objectMapper.writeValueAsString(employeeRequest))
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(EmployeeResponse.class)
            .returnResult()
            .getResponseBody();

        assertThat(createdEmployee).isNotNull();
        Long employeeId = createdEmployee.getId();
        EmployeeRequest updatedRequest = new EmployeeRequest("M", "Das");

        this.webTestClient
            .put()
            .uri("/employees/{id}", employeeId)
            .header(HttpHeaders.AUTHORIZATION, USER_AUTH)
            .header("Role", "USER")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(objectMapper.writeValueAsString(updatedRequest))
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(EmployeeResponse.class)
            .consumeWith(response -> {
                EmployeeResponse employeeResponse = response.getResponseBody();
                assertThat(employeeResponse).isNotNull();
                assertThat(employeeResponse.getId()).isEqualTo(employeeId);
                assertThat(employeeResponse.getFirstname()).isEqualTo(updatedRequest.getFirstname());
                assertThat(employeeResponse.getSurname()).isEqualTo(updatedRequest.getSurname());
            });
    }

    @Test
    void shouldCreateAndDeleteEmployee() throws Exception {
        // Create Employee
        EmployeeRequest employeeRequest = new EmployeeRequest("S", "Paul");
        EmployeeResponse createdEmployee = this.webTestClient
            .post()
            .uri("/employees")
            .header(HttpHeaders.AUTHORIZATION, ADMIN_AUTH)
            .header("Role", "ADMIN")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(objectMapper.writeValueAsString(employeeRequest))
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(EmployeeResponse.class)
            .returnResult()
            .getResponseBody();

        assertThat(createdEmployee).isNotNull();
        Long employeeId = createdEmployee.getId();

        // Delete Employee
        this.webTestClient
            .delete()
            .uri("/employees/{id}", employeeId)
            .header(HttpHeaders.AUTHORIZATION, ADMIN_AUTH)
            .header("Role", "ADMIN")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(GenericResponse.class)
            .consumeWith(response -> {
                GenericResponse genericResponse = response.getResponseBody();
                assertThat(genericResponse).isNotNull();
                assertThat(genericResponse.getMessage()).contains("successfully");
            });
    }

    @Test
    void shouldRejectUnauthorizedAccess() {
        Long employeeId = 7L;

        this.webTestClient
            .delete()
            .uri("/employees/{id}", employeeId)
            .exchange()
            .expectStatus().isUnauthorized();
    }

    @Test
    void shouldRejectForbiddenAccess() {
        Long employeeId = 7L;

        this.webTestClient
            .delete()
            .uri("/employees/{id}", employeeId)
            .header(HttpHeaders.AUTHORIZATION, USER_AUTH) // User trying to delete
            .header("Role", "USER")
            .exchange()
            .expectStatus().isForbidden();
    }

    @Test
    void shouldRejectRequestForNameValidation() throws Exception {
        EmployeeRequest employeeRequest = new EmployeeRequest("", "");
        this.webTestClient
            .post()
            .uri("/employees")
            .header(HttpHeaders.AUTHORIZATION, ADMIN_AUTH)
            .header("Role", "ADMIN")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(objectMapper.writeValueAsString(employeeRequest))
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    void shouldRejectRequestForRoleValidation() throws Exception {
        EmployeeRequest employeeRequest = new EmployeeRequest("S", "Paul");
        this.webTestClient
            .post()
            .uri("/employees")
            .header(HttpHeaders.AUTHORIZATION, ADMIN_AUTH)
            .header("Role", "AD")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(objectMapper.writeValueAsString(employeeRequest))
            .exchange()
            .expectStatus().isBadRequest();
    }
}
