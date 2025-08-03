package com.nexus.nexusproject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexus.nexusproject.Service.LoginService;
import com.nexus.nexusproject.model.LoginUser;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public String login(@RequestBody LoginUser user) {
        LoginUser authenticated = loginService.authenticate(user.getUsername(), user.getPassword());
        if (authenticated != null) {
            return "Login successful!";
        } else {
            return "Invalid username or password";
        }
    }
}
