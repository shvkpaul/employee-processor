package com.shvkpaul.employee.mapper;

import com.shvkpaul.employee.client.model.Employee;
import com.shvkpaul.employee.model.EmployeeRequest;

public class EmployeeMapper {

    public static Employee toEmployee(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        employee.setName(employeeRequest.getFirstname() + " " + employeeRequest.getSurname());
        employee.setRoleId(1L); // Assuming roleId is set to a default value
        return employee;
    }
}
