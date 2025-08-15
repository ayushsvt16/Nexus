package com.nexus.nexusproject.DTO;
// path to the directory

// receive login data from the frontend

public class LoginRequest {
    private String email;
    private String password;

    public LoginRequest() {}
    // default constructor needed because
    // so Spring can automatically create an empty LoginRequest object and then set values from the request body.

    public LoginRequest(String email, String password) { // store login data
        this.email = email;
        this.password = password;
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