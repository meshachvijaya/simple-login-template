package com.meshach.login_template_with_database.controller;

import com.meshach.login_template_with_database.entity.User;
import com.meshach.login_template_with_database.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {

    // Handle login authentication with Spring Security
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    // Hash password
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData, HttpServletRequest request) {
        try {
            String username = loginData.get("username");
            String password = loginData.get("password");

            // Create an authentication token using the provided credentials
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

            // Authenticate the user
            Authentication auth = authenticationManager.authenticate(token);

            // Store authentication result in the security context
            SecurityContextHolder.getContext().setAuthentication(auth);

            request.getSession(true)
                    .setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

            System.out.println("Login Success");
            return ResponseEntity.ok(Map.of("message", "Login Success"));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid Credentials"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> userData) {
        String email = userData.get("email");
        String username = userData.get("username");
        String password = userData.get("password");
        String confirmPassword = userData.get("confirmPassword");

        // Validate, username, email and password can't be empty
        if (email == null || email.trim().isEmpty() || username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty() || confirmPassword == null || confirmPassword.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email, Username and Password can't be empty"));
        }

        // Check password match
        if (!password.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Password does not match!"));
        }

        // Password must contain at least 8 char, uppercase, lowercase, number and symbol
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$";
        if (!password.matches(passwordPattern)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Password must contain at least 8 charater, uppercase, lowercase and number"));
        }

        // Validate registered email
        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Email already exist"));
        }

        // Validate registered username
        if (userRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "User already exist"));
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));

        userRepository.save(newUser);

        return ResponseEntity.ok(Map.of("message", "Register Success"));
    }

}