package org.amazon.example.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service class for managing user operations including registration,
 * authentication, and user retrieval. This service handles password
 * encryption and maintains user data in memory.
 */
@Service
public class UserService {
    // Using ConcurrentHashMap for thread safety in multi-user scenarios
    private final Map<String, User> users = new ConcurrentHashMap<>();
    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        initializeDefaultUsers();
    }

    /**
     * Initializes the system with default admin and user accounts.
     * This method ensures that there are always basic accounts available
     * for testing and initial system access.
     */
    private void initializeDefaultUsers() {
        // Create default admin user with ADMIN role
        // Password is encoded using BCrypt for security
        User admin = new User("admin", passwordEncoder.encode("admin123"), "ADMIN");
        users.put(admin.getUsername(), admin);
        
        // Create default regular user with USER role
        // This provides a standard user account for testing purposes
        User user = new User("user", passwordEncoder.encode("user123"), "USER");
        users.put(user.getUsername(), user);
    }

    /**
     * Registers a new user in the system.
     * The password is automatically encrypted before storage.
     * 
     * @param user The user to register (password will be encrypted)
     * @throws IllegalArgumentException if username already exists
     */
    public void registerUser(User user) {
        if (users.containsKey(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + user.getUsername());
        }
        
        // Encrypt the password before storing
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        users.put(user.getUsername(), user);
    }

    /**
     * Finds a user by username.
     * 
     * @param username The username to search for
     * @return User object if found, null otherwise
     */
    public User findByUsername(String username) {
        return users.get(username);
    }

    /**
     * Checks if a user exists in the system.
     * 
     * @param username The username to check
     * @return true if user exists, false otherwise
     */
    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    /**
     * Returns the total number of registered users.
     * Useful for administrative purposes and testing.
     * 
     * @return Total count of registered users
     */
    public int getUserCount() {
        return users.size();
    }
}
