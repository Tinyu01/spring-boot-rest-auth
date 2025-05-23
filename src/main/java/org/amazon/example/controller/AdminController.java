package org.amazon.example.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller handling admin and user-specific endpoints.
 * This controller demonstrates role-based access control using
 * both URL patterns and method-level security annotations.
 */
@RestController
public class AdminController {

    /**
     * Admin dashboard endpoint accessible only to users with ADMIN role.
     * This endpoint provides administrative functionality and information
     * that should be restricted to system administrators.
     */
    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")  // Method-level security check
    public String adminDashboard() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return "Admin Dashboard - Welcome " + username + "! Access restricted to ADMIN role only.";
    }

    /**
     * User profile endpoint accessible only to users with USER role.
     * This endpoint provides user-specific functionality and should
     * be accessible to regular users but not administrators.
     */
    @GetMapping("/user/profile")
    @PreAuthorize("hasRole('USER')")  // Method-level security check
    public String userProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return "User Profile - Welcome " + username + "! Access restricted to USER role only.";
    }

    /**
     * Generic endpoint for testing authentication.
     * Any authenticated user can access this endpoint regardless of role.
     */
    @GetMapping("/admin/info")
    @PreAuthorize("isAuthenticated()")
    public String adminInfo() {
        return "Admin section - General information available to authenticated users.";
    }
}
