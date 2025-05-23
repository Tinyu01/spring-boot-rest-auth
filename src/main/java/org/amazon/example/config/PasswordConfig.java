package org.amazon.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for password encoding.
 * BCrypt is used for secure password hashing with salt generation.
 * This provides protection against rainbow table attacks.
 */
@Configuration
public class PasswordConfig {
    
    /**
     * Creates a BCrypt password encoder bean.
     * BCrypt automatically handles salt generation and provides
     * configurable computational cost to adapt to hardware improvements.
     * 
     * @return BCryptPasswordEncoder with default strength (10 rounds)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Using default strength of 10 - provides good security/performance balance
        return new BCryptPasswordEncoder();
    }
}