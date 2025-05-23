# Spring Boot Security Project

A comprehensive Spring Boot application demonstrating role-based authentication and authorization using Spring Security.

## Project Overview

This project implements a secure web application with user registration, authentication, and role-based access control. It demonstrates best practices for Spring Security configuration, password encoding, and method-level security.

## Features

- **User Authentication**: Secure login and logout functionality
- **User Registration**: New user registration with encrypted passwords
- **Role-Based Access Control**: Different access levels for ADMIN and USER roles
- **Method-Level Security**: Fine-grained authorization using `@PreAuthorize`
- **Password Encryption**: BCrypt password encoding for secure storage
- **Default Users**: Pre-configured admin and regular user accounts

## Technology Stack

- **Framework**: Spring Boot 2.7+
- **Security**: Spring Security
- **Password Encoding**: BCrypt
- **Testing**: JUnit 5
- **Build Tool**: Maven

## Project Structure

```
src/
├── main/
│   └── java/
│       └── org/amazon/example/
│           ├── ExampleApplication.java          # Main application class
│           ├── config/
│           │   ├── SecurityConfig.java         # Security configuration
│           │   └── PasswordConfig.java         # Password encoder configuration
│           ├── controller/
│           │   ├── AdminController.java        # Admin-specific endpoints
│           │   ├── HomeController.java         # Public endpoints
│           │   ├── LoginController.java        # Login page controller
│           │   ├── RegistrationController.java # User registration
│           │   └── WelcomeController.java      # Welcome page
│           ├── entity/
│           │   └── User.java                   # User entity
│           └── service/
│               ├── UserService.java            # User management service
│               └── WelcomeService.java         # Welcome message service
└── test/
    └── java/
        └── org/amazon/example/
            ├── ExampleApplicationTests.java     # Basic Spring Boot test
            └── UserServiceTest.java             # Comprehensive test suite
```

## Default Users

The application comes with two pre-configured users:

| Username | Password | Role  |
|----------|----------|-------|
| admin    | admin123 | ADMIN |
| user     | user123  | USER  |

## API Endpoints

### Public Endpoints
- `GET /` - Public home page
- `GET /login` - Login form
- `GET /register` - Registration form
- `POST /register` - User registration

### Authenticated Endpoints
- `GET /welcome` - Welcome page (any authenticated user)
- `GET /secure` - Secure page (any authenticated user)

### Role-Specific Endpoints
- `GET /admin/dashboard` - Admin dashboard (ADMIN role only)
- `GET /user/profile` - User profile (USER role only)

## Security Configuration

The application uses a multi-layered security approach:

### URL-Based Security
- Public access: `/`, `/register`, `/login`
- Admin access: `/admin/**` requires ADMIN role
- User access: `/user/**` requires USER role
- All other endpoints require authentication

### Method-Level Security
- `@PreAuthorize("hasRole('ADMIN')")` on admin methods
- `@PreAuthorize("hasRole('USER')")` on user methods

## Running the Application

### Prerequisites
- Java 11 or higher
- Maven 3.6+

### Steps
1. Clone the repository
2. Navigate to the project directory
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. Access the application at `http://localhost:8080`

## Testing

Run the test suite to verify all functionality:

```bash
mvn test
```

The test suite covers:
- Default user creation and role assignment
- Security configuration validation
- Controller method existence
- Integration scenarios

## Key Issues Resolved

### 1. Security Configuration Alignment
**Problem**: Mismatch between URL patterns and actual endpoints
**Solution**: Aligned security rules with controller mappings

### 2. User Service Structure
**Problem**: Basic user management without proper validation
**Solution**: Enhanced user service with proper error handling

### 3. Method-Level Security
**Problem**: Inconsistent authorization annotations
**Solution**: Proper `@PreAuthorize` annotations on all protected methods

### 4. Test Compatibility
**Problem**: Tests expecting specific internal implementation details
**Solution**: Refactored code to match test expectations while maintaining best practices

## Configuration Details

### Password Encoding
- Uses BCrypt with default strength (10 rounds)
- Passwords are hashed before storage
- Never stores plain text passwords

### Session Management
- Form-based authentication
- Configurable session timeout
- Proper logout handling

### CSRF Protection
- Enabled by default for state-changing operations
- Automatically configured for form-based auth

## Common Usage Scenarios

### Logging in as Admin
1. Navigate to `/login`
2. Username: `admin`
3. Password: `admin123`
4. Access admin dashboard at `/admin/dashboard`

### Logging in as User
1. Navigate to `/login`
2. Username: `user`
3. Password: `user123`
4. Access user profile at `/user/profile`

### Registering New User
1. Navigate to `/register`
2. Fill in username, password, and role
3. Submit form
4. Login with new credentials

## Development Notes

### Adding New Roles
To add new roles, modify:
1. `User.java` - Add role validation
2. `SecurityConfig.java` - Add URL patterns
3. Controllers - Add `@PreAuthorize` annotations

### Adding New Endpoints
1. Create controller method
2. Add appropriate `@PreAuthorize` annotation
3. Update `SecurityConfig` if needed
4. Add tests for new functionality

## Troubleshooting

### Common Issues

**403 Forbidden Error**
- Check if user has required role
- Verify URL patterns in SecurityConfig
- Ensure proper authentication

**Login Redirect Loop**
- Check default success URL configuration
- Verify user roles match security requirements

**Password Not Working**
- Ensure password is properly encoded
- Check BCrypt configuration
- Verify default users are created correctly

## Security Considerations

- All passwords are encrypted using BCrypt
- CSRF protection is enabled
- Session fixation protection is active
- Proper role-based authorization at multiple levels
- No sensitive information in logs or error messages

## Future Enhancements

Potential improvements for production deployment:
- Database integration for user persistence
- Email verification for registration
- Password reset functionality
- OAuth2 integration
- Rate limiting for authentication attempts
- Audit logging for security events

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Ensure all tests pass
6. Submit a pull request

## License

This project is provided as an educational example for learning Spring Security concepts.
