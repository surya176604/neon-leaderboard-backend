package com.neongames.leaderboard.controller;

import com.neongames.leaderboard.entity.User;
import com.neongames.leaderboard.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder;

    public AuthController(UserRepository userRepo,
                          BCryptPasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    // REGISTER
    @PostMapping("/register")
    public String register(@RequestBody User user) {

        if (userRepo.existsByUsername(user.getUsername())) {
            return "Username already exists";
        }

        // 🔐 HASH PASSWORD
        user.setPassword(encoder.encode(user.getPassword()));

        userRepo.save(user);
        return "User registered successfully";
    }

    // LOGIN
    @PostMapping("/login")
    public String login(@RequestBody User user) {

        return userRepo.findByUsername(user.getUsername())
                .filter(u -> encoder.matches(user.getPassword(), u.getPassword()))
                .map(u -> "Login successful")
                .orElse("Invalid username or password");
    }
}
