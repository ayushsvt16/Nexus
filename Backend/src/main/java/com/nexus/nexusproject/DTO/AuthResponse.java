package com.nexus.nexusproject.DTO; // path of the directory

public class AuthResponse {
    private boolean success; // indicates if the authentication was successful
    private String message;// message to return to the client, e.g., error messages or success confirmation
    private Long userId;
    private String fullName;
    private String email;

    public AuthResponse() {}// used for deserialization

    public AuthResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    // response ki process successfull thi ki nahi with message
    public AuthResponse(boolean success, String message, Long userId, String fullName, String email) { 
        // constructor for successful authentication
        this.success = success;
        this.message = message;
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
}