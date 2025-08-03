package com.nexus.nexusproject.Service;

import com.nexus.nexusproject.model.LoginUser;

public interface LoginService {
    // Authenticates a user. Returns the user if credentials are correct, otherwise returns null.
    LoginUser authenticate(String username, String password);
}
