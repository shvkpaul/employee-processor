package com.shvkpaul.employee.mapper;

import com.shvkpaul.employee.client.model.EmployeeClientRequest;
import com.shvkpaul.employee.client.model.EmployeeClientResponse;
import com.shvkpaul.employee.model.EmployeeRequest;
import com.shvkpaul.employee.model.EmployeeResponse;

public class EmployeeMapper {

    public static EmployeeClientRequest toEmployeeClientRequest(EmployeeRequest employeeRequest) {
        EmployeeClientRequest employeeClientRequest = new EmployeeClientRequest();
        employeeClientRequest.setName(employeeRequest.getFirstname() + " " + employeeRequest.getSurname());
        employeeClientRequest.setRoleId(1L); // Assuming roleId is set to a default value
        return employeeClientRequest;
    }

    public static EmployeeResponse toEmployeeResponse(EmployeeClientResponse employeeClientResponse) {
        EmployeeResponse employeeResponse = new EmployeeResponse();

        employeeResponse.setId(employeeClientResponse.getId());
        String[] nameParts = employeeClientResponse.getName().trim().split(" ");
        if (nameParts.length > 1) {
            employeeResponse.setFirstname(nameParts[0]);
            employeeResponse.setSurname(nameParts[1]);
        } else {
            employeeResponse.setFirstname(nameParts[0]);
            employeeResponse.setSurname("");
        }
        employeeResponse.setRoleId(employeeClientResponse.getRoleId());
        return employeeResponse;
    }
}
