package com.shvkpaul.employee.service;

import com.shvkpaul.employee.client.EmployeeClient;
import com.shvkpaul.employee.client.model.GenericResponse;
import com.shvkpaul.employee.common.TestData;
import com.shvkpaul.employee.model.EmployeeResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest extends TestData {

    @Mock
    private EmployeeClient employeeClient;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void testCreateEmployee() {
        when(employeeClient.createEmployee(any())).thenReturn(employeeClientResponse);

        EmployeeResponse response = employeeService.createEmployee(employeeRequest);

        assertEquals(employeeResponse.getId(), response.getId());
        assertEquals(employeeResponse.getFirstname(), response.getFirstname());
        assertEquals(employeeResponse.getSurname(), response.getSurname());
        assertEquals(employeeResponse.getRoleId(), response.getRoleId());
    }

    @Test
    void testGetEmployee() {
        when(employeeClient.getEmployee(anyLong())).thenReturn(employeeClientResponse);

        EmployeeResponse response = employeeService.getEmployee(1L);

        assertEquals(employeeResponse.getId(), response.getId());
        assertEquals(employeeResponse.getFirstname(), response.getFirstname());
        assertEquals(employeeResponse.getSurname(), response.getSurname());
        assertEquals(employeeResponse.getRoleId(), response.getRoleId());
    }

    @Test
    void testUpdateEmployee() {
        when(employeeClient.updateEmployee(anyLong(), any())).thenReturn(employeeClientResponse);

        EmployeeResponse response = employeeService.updateEmployee(1L, employeeRequest);

        assertEquals(employeeResponse.getId(), response.getId());
        assertEquals(employeeResponse.getFirstname(), response.getFirstname());
        assertEquals(employeeResponse.getSurname(), response.getSurname());
        assertEquals(employeeResponse.getRoleId(), response.getRoleId());
    }

    @Test
    void testDeleteEmployee() {
        when(employeeClient.deleteEmployee(anyLong())).thenReturn(genericResponse);

        GenericResponse response = employeeService.deleteEmployee(1L);

        assertEquals(genericResponse, response);
        verify(employeeClient, times(1)).deleteEmployee(1L);
    }
}
