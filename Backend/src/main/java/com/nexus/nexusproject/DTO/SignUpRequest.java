package com.nexus.nexusproject.DTO;
// path of the directory

public class SignUpRequest {
    private String fullName;
    private String email;
    private String password;
    
    public SignUpRequest() {}
    // Used when an empty SignUpRequest object is created and values are set later using setters.
    
    public SignUpRequest(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
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
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}