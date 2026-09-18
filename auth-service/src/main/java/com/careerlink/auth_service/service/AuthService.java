package com.careerlink.auth_service.service;

import com.careerlink.auth_service.dto.AuthResponse;
import com.careerlink.auth_service.model.User;
import com.careerlink.auth_service.repository.UserRepository;
import com.careerlink.auth_service.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String signup(String email, String password, String role) {
        User u = new User();
        u.setEmail(email);
        u.setPassword(encoder.encode(password));
        u.setRole(role);
        userRepository.save(u);
        return "Signup successful";
    }

    public AuthResponse login(String email, String password) {
        User u = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(password, u.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(u.getEmail(), u.getRole());

        AuthResponse res = new AuthResponse();
        res.setToken(token);
        res.setRole(u.getRole());
        return res;
    }
}