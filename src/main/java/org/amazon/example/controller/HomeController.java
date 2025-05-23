package org.amazon.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Controller for public and general access endpoints.
 * These endpoints demonstrate different levels of access control
 * from completely public to authenticated-user-only.
 */
@RestController
public class HomeController {

    /**
     * Public home page accessible to everyone.
     * No authentication required for this endpoint.
     */
    @GetMapping("/")
    public String home() {
        return "Welcome to the Spring Security Demo! This is a public page accessible to everyone.";
    }

    /**
     * Secure page requiring authentication but no specific role.
     * Any authenticated user can access this page regardless of their role.
     */
    @GetMapping("/secure")
    public String secure() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : "Anonymous";
        return "Secure Page - Hello " + username + "! You are viewing a secure page (Authenticated users only).";
    }

    /**
     * Public information endpoint that provides system status.
     * Useful for health checks and basic system information.
     */
    @GetMapping("/info")
    public String info() {
        return "System Information - Spring Security Demo Application v1.0";
    }
}
