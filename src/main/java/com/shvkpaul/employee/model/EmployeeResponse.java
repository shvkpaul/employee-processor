package com.shvkpaul.employee.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeResponse {

    private Long id;

    @JsonProperty("first_name")
    private String firstname;

    private String surname;

    @JsonProperty("role_id")
    private Long roleId;
}
