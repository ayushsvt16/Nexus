package com.nexus.nexusproject.Controller;// path of the directory 

import org.springframework.beans.factory.annotation.Autowired; // dependency injection 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; // ye dono http response ko handle krne k liye
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexus.nexusproject.DTO.AuthResponse;
import com.nexus.nexusproject.DTO.LoginRequest;
import com.nexus.nexusproject.DTO.SignUpRequest;
import com.nexus.nexusproject.Service.UserService;

@RestController // contains controller +  response body
@RequestMapping("/api/auth") // base path for all methods down
@CrossOrigin(origins = "*") // cors enable to allow all origins mtb frontend se request aa sakti hai
public class AuthController {

    @Autowired //dependency injection
    private UserService userService; // inject userService dependency bean

    @PostMapping("/signup") // signup call for user registration 
    public ResponseEntity<AuthResponse> signup(@RequestBody SignUpRequest signupRequest) {
        // response entity authresponse object return krega jo automatically spring json me convert kr degi
        // plus 200/400 exit code generate krega

        // @RequestBody SignUpRequest signupRequest - this will automatically convert the JSON request body to SignUpRequest object
        AuthResponse response = userService.signup(signupRequest);
        // user service me logic hoga jaha pr request kr ke response milega (authresponse)
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthResponse response = userService.login(loginRequest);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
    
    @GetMapping("/test")
    public ResponseEntity<String> test() { //string output chahiye tha isiliye reponseentity <string>
        // Simple test endpoint to verify API is working
        return ResponseEntity.ok("Auth API is working!");
    }
}