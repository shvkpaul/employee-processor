package com.shvkpaul.employee.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shvkpaul.employee.client.model.EmployeeClientRequest;
import com.shvkpaul.employee.client.model.EmployeeClientResponse;
import com.shvkpaul.employee.client.model.GenericResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeClientTest {

    private static MockWebServer mockWebServer;
    private EmployeeClient employeeClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void startServer() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void stopServer() throws IOException {
        mockWebServer.shutdown();
    }

    @BeforeEach
    void setup() {
        String baseUrl = mockWebServer.url("/").toString();
        employeeClient = new EmployeeClient(WebClient.builder().baseUrl(baseUrl).build());
    }

    @Test
    void testCreateEmployee() throws Exception {
        EmployeeClientRequest request = new EmployeeClientRequest("S Paul", 1L);
        EmployeeClientResponse expectedResponse = new EmployeeClientResponse(1L, "S Paul", 1L);

        mockWebServer.enqueue(new MockResponse()
            .setBody(objectMapper.writeValueAsString(expectedResponse))
            .addHeader("Content-Type", "application/json"));

        EmployeeClientResponse actualResponse = employeeClient.createEmployee(request);

        // Verify
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getName(), actualResponse.getName());
        assertEquals(expectedResponse.getRoleId(), actualResponse.getRoleId());
    }

    @Test
    void testGetEmployee() throws Exception {
        Long employeeId = 1L;
        EmployeeClientResponse expectedResponse = new EmployeeClientResponse(employeeId, "S Paul", 1L);

        mockWebServer.enqueue(new MockResponse()
            .setBody(objectMapper.writeValueAsString(expectedResponse))
            .addHeader("Content-Type", "application/json"));

        EmployeeClientResponse actualResponse = employeeClient.getEmployee(employeeId);

        // Verify
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getName(), actualResponse.getName());
        assertEquals(expectedResponse.getRoleId(), actualResponse.getRoleId());
    }

    @Test
    void testDeleteEmployee() throws Exception {
        Long employeeId = 1L;
        GenericResponse expectedResponse = new GenericResponse("Employee deleted successfully");

        mockWebServer.enqueue(new MockResponse()
            .setBody(objectMapper.writeValueAsString(expectedResponse))
            .addHeader("Content-Type", "application/json"));

        GenericResponse actualResponse = employeeClient.deleteEmployee(employeeId);

        // Verify
        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
    }

    @Test
    void testUpdateEmployee() throws Exception {
        Long employeeId = 1L;
        EmployeeClientRequest request = new EmployeeClientRequest("S Paul Updated", 2L);
        EmployeeClientResponse expectedResponse = new EmployeeClientResponse(employeeId, "S Paul Updated", 2L);

        mockWebServer.enqueue(new MockResponse()
            .setBody(objectMapper.writeValueAsString(expectedResponse))
            .addHeader("Content-Type", "application/json"));

        EmployeeClientResponse actualResponse = employeeClient.updateEmployee(employeeId, request);

        // Verify
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getName(), actualResponse.getName());
        assertEquals(expectedResponse.getRoleId(), actualResponse.getRoleId());
    }
}
