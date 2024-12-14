package com.shvkpaul.employee.common;

import com.shvkpaul.employee.client.model.EmployeeClientRequest;
import com.shvkpaul.employee.client.model.EmployeeClientResponse;
import com.shvkpaul.employee.client.model.GenericResponse;
import com.shvkpaul.employee.model.EmployeeRequest;
import com.shvkpaul.employee.model.EmployeeResponse;
import org.junit.jupiter.api.BeforeEach;

public class TestData {

    public EmployeeRequest employeeRequest;
    public EmployeeResponse employeeResponse;
    public GenericResponse genericResponse;
    public EmployeeClientRequest employeeClientRequest;
    public EmployeeClientResponse employeeClientResponse;

    @BeforeEach
    void setUp() {
        employeeRequest = new EmployeeRequest();
        employeeRequest.setFirstname("S");
        employeeRequest.setSurname("Paul");

        employeeResponse = new EmployeeResponse();
        employeeResponse.setId(1L);
        employeeResponse.setRoleId(1L);
        employeeResponse.setFirstname("S");
        employeeResponse.setSurname("Paul");

        genericResponse = new GenericResponse();
        genericResponse.setMessage("Employee deleted successfully");

        employeeClientRequest = new EmployeeClientRequest();
        employeeClientRequest.setName("S Paul");
        employeeClientRequest.setRoleId(1L);

        employeeClientResponse = new EmployeeClientResponse();
        employeeClientResponse.setId(1L);
        employeeClientResponse.setName("S Paul");
        employeeClientResponse.setRoleId(1L);
    }
}
