package com.nexus.nexusproject.Service; // path of the directory

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexus.nexusproject.DTO.AuthResponse;
import com.nexus.nexusproject.DTO.LoginRequest;
import com.nexus.nexusproject.DTO.SignUpRequest;
import com.nexus.nexusproject.Repository.UserRepository;
import com.nexus.nexusproject.model.User;

@Service // this tells that it contains logic related to user management
public class UserService {

    @Autowired // this allows Spring to inject the UserRepository bean
    private UserRepository userRepository;
    
    private static final String ALLOWED_EMAIL_DOMAIN = "@iiitbh.ac.in";
    
    public AuthResponse signup(SignUpRequest signupRequest) {
        try {
            // Validate input
            // agr getFullName() jo signup request dto me hai woh null hua or empty hai toh error return karo
            // dto se return hoga " shivansh " with space trim krke "shivansh" unnecessary hata diya
            if (signupRequest.getFullName() == null || signupRequest.getFullName().trim().isEmpty()) {
                return new AuthResponse(false, "Full name is required");
            }
            
            if (signupRequest.getEmail() == null || signupRequest.getEmail().trim().isEmpty()) {
                return new AuthResponse(false, "Email is required");
            }
            
            // Validate email domain
            // same process ki email @iiitbh.ac.in se end ho baad me ye hata duga
            if (!signupRequest.getEmail().toLowerCase().endsWith(ALLOWED_EMAIL_DOMAIN)) {
                return new AuthResponse(false, "Only IIIT Bhagalpur email addresses (@iiitbh.ac.in) are allowed");
            }

            // Check if email already exists
            // ye check karta hai ki email already database me hai ya nahi
            // userRepository me existsByEmail method use karke check kar raha hai
            if (userRepository.existsByEmail(signupRequest.getEmail())) {
                return new AuthResponse(false, "Email already exists");
            }
            // Password validation
            // agr getPassword() jo signup request dto me hai woh null hua or empty hai toh error return karo
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
            // userRepository me save karne ke baad savedUser me woh user aayega jo database me save hua hai

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
            // db me check krega email ko
            if (userOptional.isEmpty()) {
                return new AuthResponse(false, "Invalid email or password");
            }
            // agr email ni mila to false response dega 
            
            User user = userOptional.get();
            // if user me email match krta hai
            
            //check krega password ko 
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