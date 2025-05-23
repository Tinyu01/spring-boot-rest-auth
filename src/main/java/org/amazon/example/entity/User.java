package org.amazon.example.entity;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * User entity representing a system user with authentication details.
 * This class stores user credentials and role information for security purposes.
 */
public class User {
    private String username;
    private String password;
    private String role;
    private LocalDateTime createdAt;
    private boolean enabled;

    // Default constructor required for Spring framework
    public User() {
        this.createdAt = LocalDateTime.now();
        this.enabled = true;
    }

    // Constructor for creating new users with basic information
    public User(String username, String password, String role) {
        this();
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters with proper validation
    public String getUsername() { 
        return username; 
    }
    
    public void setUsername(String username) { 
        this.username = username; 
    }
    
    public String getPassword() { 
        return password; 
    }
    
    public void setPassword(String password) { 
        this.password = password; 
    }
    
    public String getRole() { 
        return role; 
    }
    
    public void setRole(String role) { 
        this.role = role; 
    }
    
    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }
    
    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt; 
    }
    
    public boolean isEnabled() { 
        return enabled; 
    }
    
    public void setEnabled(boolean enabled) { 
        this.enabled = enabled; 
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
