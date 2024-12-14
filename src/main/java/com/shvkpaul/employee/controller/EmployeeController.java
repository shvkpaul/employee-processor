package com.shvkpaul.employee.controller;

import com.shvkpaul.employee.client.model.GenericResponse;
import com.shvkpaul.employee.error.RoleValidator;
import com.shvkpaul.employee.model.EmployeeRequest;
import com.shvkpaul.employee.model.EmployeeResponse;
import com.shvkpaul.employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Find an existing employee", description = "Find an existing employee in the system")
    @ApiResponses(value =
        {
            @ApiResponse(responseCode = "200", description = "successfully fetched the employee",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            )
        }
    )
    public EmployeeResponse getEmployee(
        @PathVariable Long id,
        @RequestHeader("Role") String role,
        @RequestHeader Map<String, String> headers) {
        RoleValidator.validateRoleHeader(headers, role);
        return employeeService.getEmployee(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new employee", description = "Creates a new employee in the system")
    @ApiResponses(value =
        {
            @ApiResponse(responseCode = "201", description = "successfully created a employee",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            )
        }
    )
    public EmployeeResponse createEmployee(
        @RequestBody @Valid EmployeeRequest employeeRequest,
        @RequestHeader("Role") String role,
        @RequestHeader Map<String, String> headers) {
        RoleValidator.validateRoleHeader(headers, role);
        RoleValidator.checkAdminRole(role);
        return employeeService.createEmployee(employeeRequest);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER')")
    @Operation(summary = "Update an existing employee", description = "Updates an existing employee in the system")
    @ApiResponses(value =
        {
            @ApiResponse(responseCode = "200", description = "successfully updated the employee",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            ),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            )
        }
    )
    public EmployeeResponse updateEmployee(
        @RequestBody EmployeeRequest employeeRequest,
        @PathVariable Long id,
        @RequestHeader("Role") String role,
        @RequestHeader Map<String, String> headers) {
        RoleValidator.validateRoleHeader(headers, role);
        RoleValidator.checkUserRole(role);
        return employeeService.updateEmployee(id, employeeRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete an existing employee", description = "Delete an existing employee in the system")
    @ApiResponses(value =
        {
            @ApiResponse(responseCode = "200", description = "successfully deleted the employee",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            )
        }
    )
    public GenericResponse deleteEmployee(
        @PathVariable Long id,
        @RequestHeader("Role") String role,
        @RequestHeader Map<String, String> headers) {
        RoleValidator.validateRoleHeader(headers, role);
        RoleValidator.checkAdminRole(role);
        return employeeService.deleteEmployee(id);
    }
}
