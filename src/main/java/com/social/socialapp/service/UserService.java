package com.social.socialapp.service;

import com.social.socialapp.model.User;
import com.social.socialapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return "Email already exists!";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }
}
