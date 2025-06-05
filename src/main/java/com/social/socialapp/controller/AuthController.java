package com.social.socialapp.controller;

import com.social.socialapp.model.User;
import com.social.socialapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 👉 SIGNUP
    @PostMapping("/signup")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        if (userRepository.findByUsername(username) != null) {
            return "Username already exists!";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        return "User registered successfully!";
    }

    // 👉 LOGIN
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return "User not found!";
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "Invalid credentials!";
        }

        // Normally you'd return a JWT token here
        return "Login successful!";
    }
}
