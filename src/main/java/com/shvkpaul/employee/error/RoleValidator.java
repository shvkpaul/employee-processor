package com.shvkpaul.employee.error;

import java.util.List;
import java.util.Map;

public class RoleValidator {

    private static final List<String> ALLOWED_ROLES = List.of("ADMIN", "USER", "MANAGER");

    public static void validateRoleHeader(Map<String, String> headers, String role) {
        if (role == null || role.length() < 3 || role.length() > 50) {
            throw new InvalidRoleException("Invalid role length: " + role);
        }
        if (!ALLOWED_ROLES.contains(role)) {
            throw new InvalidRoleException("Invalid role: " + role);
        }
        if (!headers.keySet().stream().anyMatch(k -> k.equalsIgnoreCase("Role"))) {
            throw new InvalidRoleException("Missing required header: Role");
        }
    }

    public static void checkAdminRole(String role) {
        if (!"ADMIN".equals(role)) {
            throw new AccessDeniedException("Access denied for role: " + role);
        }
    }

    public static void checkUserRole(String role) {
        if (!"USER".equals(role)) {
            throw new AccessDeniedException("Access denied for role: " + role);
        }
    }
}
