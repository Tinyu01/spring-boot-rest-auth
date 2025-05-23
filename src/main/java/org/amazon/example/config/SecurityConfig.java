package org.amazon.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User.UserBuilder;

/**
 * Central security configuration class that defines authentication
 * and authorization rules for the entire application. This configuration
 * enables both URL-based and method-level security.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Enable @PreAuthorize annotations
public class SecurityConfig {
    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    /**
     * Configures the security filter chain that handles HTTP requests.
     * This method defines which URLs require authentication and what roles
     * are needed to access specific endpoints.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Public endpoints that don't require authentication
                .requestMatchers("/", "/register", "/login", "/css/**", "/js/**").permitAll()
                
                // Admin-only endpoints - requires ADMIN role
                .requestMatchers("/admin/**").hasRole("ADMIN")
                
                // User-only endpoints - requires USER role  
                .requestMatchers("/user/**").hasRole("USER")
                
                // All other endpoints require authentication
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")  // Custom login page
                .defaultSuccessUrl("/welcome", true)  // Redirect after successful login
                .failureUrl("/login?error=true")  // Redirect after failed login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout=true")  // Redirect after logout
                .permitAll()
            )
            .csrf(csrf -> csrf.disable()); // Disabled for simplicity - enable in production

        return http.build();
    }

    /**
     * Custom UserDetailsService that integrates with our UserService.
     * This service loads user details for Spring Security authentication.
     * It converts our custom User objects into Spring Security UserDetails.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userService.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found: " + username);
            }
            
            // Build Spring Security UserDetails from our User entity
            UserBuilder builder = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())  // Already encrypted
                .roles(user.getRole());  // Spring Security will add ROLE_ prefix
                
            return builder.build();
        };
    }
}