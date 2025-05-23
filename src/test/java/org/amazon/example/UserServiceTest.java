package org.amazon.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for the Spring Security Demo application.
 * These tests validate all the requirements specified in the project tasks,
 * including user management, security configuration, and controller functionality.
 */
public class UserServiceTest {

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private SecurityConfig securityConfig;

    @BeforeEach
    public void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserService(passwordEncoder);
        securityConfig = new SecurityConfig(userService);
    }

    // ========================================
    // Task 1: Default User Creation Tests
    // ========================================

    @Test
    @DisplayName("Task 1: Verify default admin user exists")
    public void testDefaultAdminUserExists() {
        User admin = userService.findByUsername("admin");
        assertNotNull(admin, "Task: 1 (TODO: 1) - Expected a default admin user but got null");
        assertEquals("admin", admin.getUsername(), "Admin username should be 'admin'");
    }

    @Test
    @DisplayName("Task 1: Verify default admin user has correct role")
    public void testDefaultAdminUserRole() {
        User admin = userService.findByUsername("admin");
        assertNotNull(admin, "Admin user should exist");
        assertEquals("ADMIN", admin.getRole(), "Task: 1 (TODO: 1) - Expected role ADMIN but got " + admin.getRole());
    }

    @Test
    @DisplayName("Task 1: Verify default admin password is encrypted")
    public void testDefaultAdminPasswordEncryption() {
        User admin = userService.findByUsername("admin");
        assertNotNull(admin, "Admin user should exist");
        
        // Password should be encrypted, not plain text
        assertNotEquals("admin123", admin.getPassword(), "Password should be encrypted, not plain text");
        
        // Verify the encrypted password matches the original
        assertTrue(passwordEncoder.matches("admin123", admin.getPassword()), 
                  "Encrypted password should match original password");
    }

    @Test
    @DisplayName("Task 2: Verify default regular user exists")
    public void testDefaultRegularUserExists() {
        User user = userService.findByUsername("user");
        assertNotNull(user, "Task: 1 (TODO: 2) - Expected a default regular user but got null");
        assertEquals("user", user.getUsername(), "User username should be 'user'");
    }

    @Test
    @DisplayName("Task 2: Verify default regular user has correct role")
    public void testDefaultRegularUserRole() {
        User user = userService.findByUsername("user");
        assertNotNull(user, "Regular user should exist");
        assertEquals("USER", user.getRole(), "Task: 1 (TODO: 2) - Expected role USER but got " + user.getRole());
    }

    @Test
    @DisplayName("Task 2: Verify default user password is encrypted")
    public void testDefaultUserPasswordEncryption() {
        User user = userService.findByUsername("user");
        assertNotNull(user, "User should exist");
        
        // Password should be encrypted, not plain text
        assertNotEquals("user123", user.getPassword(), "Password should be encrypted, not plain text");
        
        // Verify the encrypted password matches the original
        assertTrue(passwordEncoder.matches("user123", user.getPassword()),
                  "Encrypted password should match original password");
    }

    // ========================================
    // Task 3: Security Configuration Tests
    // ========================================

    @Test
    @DisplayName("Task 3: Verify UserDetailsService can load admin user")
    public void testAdminAccess() throws Exception {
        UserDetailsService userDetailsService = securityConfig.userDetailsService();
        UserDetails adminDetails = userDetailsService.loadUserByUsername("admin");
        
        assertNotNull(adminDetails, "Task: 2 (TODO: 3) - Expected to find user details for admin but got null");
        assertEquals("admin", adminDetails.getUsername(), "Admin username should match");
        assertTrue(adminDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")),
                "Admin should have ROLE_ADMIN authority");
    }

    @Test
    @DisplayName("Task 4: Verify UserDetailsService can load regular user")
    public void testUserAccess() throws Exception {
        UserDetailsService userDetailsService = securityConfig.userDetailsService();
        UserDetails userDetails = userDetailsService.loadUserByUsername("user");
        
        assertNotNull(userDetails, "Task: 2 (TODO: 4) - Expected to find user details for user but got null");
        assertEquals("user", userDetails.getUsername(), "User username should match");
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")),
                "User should have ROLE_USER authority");
    }

    @Test
    @DisplayName("Task 3: Verify UserDetailsService throws exception for non-existent user")
    public void testUserNotFound() {
        UserDetailsService userDetailsService = securityConfig.userDetailsService();
        
        assertThrows(UsernameNotFoundException.class, 
                () -> userDetailsService.loadUserByUsername("nonexistent"),
                "Should throw UsernameNotFoundException for non-existent user");
    }

    // ========================================
    // Task 5-7: Controller and Method Tests
    // ========================================

    @Test
    @DisplayName("Task 5: Verify AdminController class exists")
    public void testAdminControllerClassExists() {
        try {
            Class<?> adminControllerClass = Class.forName("org.amazon.example.AdminController");
            assertNotNull(adminControllerClass, "Task: 3 (TODO: 5) - Expected AdminController class but it was not found");
            
            // Verify it's annotated with @RestController
            assertTrue(adminControllerClass.isAnnotationPresent(org.springframework.web.bind.annotation.RestController.class),
                    "AdminController should be annotated with @RestController");
        } catch (ClassNotFoundException e) {
            fail("Task: 3 (TODO: 5) - Expected AdminController class but it was not found");
        }
    }

    @Test
    @DisplayName("Task 6: Verify adminDashboard method exists in AdminController")
    public void testAdminDashboardMethodExists() {
        try {
            Class<?> adminControllerClass = Class.forName("org.amazon.example.AdminController");
            Method adminDashboardMethod = adminControllerClass.getMethod("adminDashboard");
            
            assertNotNull(adminDashboardMethod, "Task: 3 (TODO: 6) - Expected adminDashboard method but got null");
            
            // Verify method has correct annotations
            assertTrue(adminDashboardMethod.isAnnotationPresent(org.springframework.web.bind.annotation.GetMapping.class),
                    "adminDashboard method should have @GetMapping annotation");
            
            assertTrue(adminDashboardMethod.isAnnotationPresent(org.springframework.security.access.prepost.PreAuthorize.class),
                    "adminDashboard method should have @PreAuthorize annotation");
            
            // Verify return type is String
            assertEquals(String.class, adminDashboardMethod.getReturnType(),
                    "adminDashboard method should return String");
            
        } catch (ClassNotFoundException e) {
            fail("Task: 3 (TODO: 6) - Expected AdminController class but it was not found");
        } catch (NoSuchMethodException e) {
            fail("Task: 3 (TODO: 6) - Expected adminDashboard method but it was not found");
        }
    }

    @Test
    @DisplayName("Task 7: Verify userProfile method exists in AdminController")
    public void testUserProfileMethodExists() {
        try {
            Class<?> adminControllerClass = Class.forName("org.amazon.example.AdminController");
            Method userProfileMethod = adminControllerClass.getMethod("userProfile");
            
            assertNotNull(userProfileMethod, "Task: 3 (TODO: 7) - Expected userProfile method but got null");
            
            // Verify method has correct annotations
            assertTrue(userProfileMethod.isAnnotationPresent(org.springframework.web.bind.annotation.GetMapping.class),
                    "userProfile method should have @GetMapping annotation");
            
            assertTrue(userProfileMethod.isAnnotationPresent(org.springframework.security.access.prepost.PreAuthorize.class),
                    "userProfile method should have @PreAuthorize annotation");
            
            // Verify return type is String
            assertEquals(String.class, userProfileMethod.getReturnType(),
                    "userProfile method should return String");
            
        } catch (ClassNotFoundException e) {
            fail("Task: 3 (TODO: 7) - Expected AdminController class but it was not found");
        } catch (NoSuchMethodException e) {
            fail("Task: 3 (TODO: 7) - Expected userProfile method but it was not found");
        }
    }

    // ========================================
    // Task 8-10: Integration and Compilation Tests
    // ========================================

    @Test
    @DisplayName("Task 8: Verify project compiles successfully")
    public void testProjectCompiles() {
        // This test verifies that all classes can be instantiated without errors
        assertDoesNotThrow(() -> {
            // Test main application class
            ExampleApplication app = new ExampleApplication();
            assertNotNull(app, "ExampleApplication should be instantiable");
            
            // Test all controller classes
            AdminController adminController = new AdminController();
            assertNotNull(adminController, "AdminController should be instantiable");
            
            HomeController homeController = new HomeController();
            assertNotNull(homeController, "HomeController should be instantiable");
            
            // Test service classes
            WelcomeService welcomeService = new WelcomeService();
            assertNotNull(welcomeService, "WelcomeService should be instantiable");
            
        }, "Task: 4 (TODO: 8) - All classes should compile and be instantiable");
    }

    @Test
    @DisplayName("Task 9: Verify user registration functionality")
    public void testUserRegistration() {
        // Test user registration process
        User newUser = new User("testuser", "testpass", "USER");
        
        assertDoesNotThrow(() -> userService.registerUser(newUser),
                "User registration should not throw exceptions");
        
        // Verify user was registered
        User retrievedUser = userService.findByUsername("testuser");
        assertNotNull(retrievedUser, "Registered user should be retrievable");
        assertEquals("testuser", retrievedUser.getUsername(), "Username should match");
        assertEquals("USER", retrievedUser.getRole(), "Role should match");
        
        // Verify password is encrypted
        assertNotEquals("testpass", retrievedUser.getPassword(), "Password should be encrypted");
        assertTrue(passwordEncoder.matches("testpass", retrievedUser.getPassword()),
                "Encrypted password should match original");
    }

    @Test
    @DisplayName("Task 10: Verify authentication flow works")
    public void testAuthenticationFlow() {
        // Test that UserDetailsService can authenticate default users
        UserDetailsService userDetailsService = securityConfig.userDetailsService();
        
        // Test admin authentication
        UserDetails adminDetails = userDetailsService.loadUserByUsername("admin");
        assertNotNull(adminDetails, "Admin user should be loadable");
        assertTrue(passwordEncoder.matches("admin123", adminDetails.getPassword()),
                "Admin password should match");
        
        // Test regular user authentication
        UserDetails userDetails = userDetailsService.loadUserByUsername("user");
        assertNotNull(userDetails, "Regular user should be loadable");
        assertTrue(passwordEncoder.matches("user123", userDetails.getPassword()),
                "User password should match");
    }

    // ========================================
    // Additional Validation Tests
    // ========================================

    @Test
    @DisplayName("Verify duplicate user registration is prevented")
    public void testDuplicateUserRegistration() {
        User duplicateUser = new User("admin", "newpass", "USER");
        
        assertThrows(IllegalArgumentException.class,
                () -> userService.registerUser(duplicateUser),
                "Should throw exception when registering duplicate username");
    }

    @Test
    @DisplayName("Verify user count is correct")
    public void testUserCount() {
        // Should have 2 default users (admin and user)
        assertEquals(2, userService.getUserCount(), "Should have 2 default users");
        
        // Add a new user
        User newUser = new User("newuser", "newpass", "USER");
        userService.registerUser(newUser);
        
        assertEquals(3, userService.getUserCount(), "Should have 3 users after registration");
    }

    @Test
    @DisplayName("Verify user exists functionality")
    public void testUserExists() {
        assertTrue(userService.userExists("admin"), "Admin user should exist");
        assertTrue(userService.userExists("user"), "Regular user should exist");
        assertFalse(userService.userExists("nonexistent"), "Non-existent user should not exist");
    }

    @Test
    @DisplayName("Verify password encoder configuration")
    public void testPasswordEncoderConfiguration() {
        assertNotNull(passwordEncoder, "Password encoder should be configured");
        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder,
                "Should use BCryptPasswordEncoder");
        
        // Test encoding functionality
        String plainPassword = "testpassword";
        String encodedPassword = passwordEncoder.encode(plainPassword);
        
        assertNotEquals(plainPassword, encodedPassword, "Encoded password should differ from plain text");
        assertTrue(passwordEncoder.matches(plainPassword, encodedPassword),
                "Encoded password should match original");
    }

    @Test
    @DisplayName("Verify all required controllers exist")
    public void testAllControllersExist() {
        assertDoesNotThrow(() -> {
            Class.forName("org.amazon.example.AdminController");
            Class.forName("org.amazon.example.HomeController");
            Class.forName("org.amazon.example.LoginController");
            Class.forName("org.amazon.example.RegistrationController");
            Class.forName("org.amazon.example.WelcomeController");
        }, "All required controller classes should exist");
    }

    @Test
    @DisplayName("Verify all required services exist")
    public void testAllServicesExist() {
        assertDoesNotThrow(() -> {
            Class.forName("org.amazon.example.UserService");
            Class.forName("org.amazon.example.WelcomeService");
        }, "All required service classes should exist");
    }

    @Test
    @DisplayName("Verify all required configuration classes exist")
    public void testAllConfigClassesExist() {
        assertDoesNotThrow(() -> {
            Class.forName("org.amazon.example.SecurityConfig");
            Class.forName("org.amazon.example.PasswordConfig");
        }, "All required configuration classes should exist");
    }
}