package com.nexus.nexusproject.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexus.nexusproject.DTO.AuthResponse;
import com.nexus.nexusproject.DTO.LoginRequest;
import com.nexus.nexusproject.DTO.SignUpRequest;
import com.nexus.nexusproject.Repository.UserRepository;
import com.nexus.nexusproject.model.User;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    private static final String ALLOWED_EMAIL_DOMAIN = "@iiitbh.ac.in";
    
    public AuthResponse signup(SignUpRequest signupRequest) {
        try {
            // Validate input
            if (signupRequest.getFullName() == null || signupRequest.getFullName().trim().isEmpty()) {
                return new AuthResponse(false, "Full name is required");
            }
            
            if (signupRequest.getEmail() == null || signupRequest.getEmail().trim().isEmpty()) {
                return new AuthResponse(false, "Email is required");
            }
            
            // Validate email domain
            if (!signupRequest.getEmail().toLowerCase().endsWith(ALLOWED_EMAIL_DOMAIN)) {
                return new AuthResponse(false, "Only IIIT Bhilai email addresses (@iiitbh.ac.in) are allowed");
            }
            
            // Check if email already exists
            if (userRepository.existsByEmail(signupRequest.getEmail())) {
                return new AuthResponse(false, "Email already exists");
            }
            
            if (signupRequest.getPassword() == null || signupRequest.getPassword().length() < 6) {
                return new AuthResponse(false, "Password must be at least 6 characters long");
            }
            
            // Create new user
            User user = new User();
            user.setFullName(signupRequest.getFullName().trim());
            user.setEmail(signupRequest.getEmail().trim().toLowerCase());
            user.setPassword(signupRequest.getPassword()); // In real app, hash this password
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            
            User savedUser = userRepository.save(user);
            
            return new AuthResponse(true, "User registered successfully", 
                                  savedUser.getId(), savedUser.getFullName(), savedUser.getEmail());
            
        } catch (Exception e) {
            return new AuthResponse(false, "Registration failed: " + e.getMessage());
        }
    }
    
    public AuthResponse login(LoginRequest loginRequest) {
        try {
            // Validate input
            if (loginRequest.getEmail() == null || loginRequest.getEmail().trim().isEmpty()) {
                return new AuthResponse(false, "Email is required");
            }
            
            if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
                return new AuthResponse(false, "Password is required");
            }
            
            // Find user by email
            Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail().trim().toLowerCase());
            
            if (userOptional.isEmpty()) {
                return new AuthResponse(false, "Invalid email or password");
            }
            
            User user = userOptional.get();
            
            // Check password (In real app, compare hashed passwords)
            if (!user.getPassword().equals(loginRequest.getPassword())) {
                return new AuthResponse(false, "Invalid email or password");
            }
            
            // Update last login time
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
            
            return new AuthResponse(true, "Login successful", user.getId(), user.getFullName(), user.getEmail());
            
        } catch (Exception e) {
            return new AuthResponse(false, "Login failed: " + e.getMessage());
        }
    }
    
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}