package com.example.demo.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Userentity;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Userentity registerUser(Userentity user) {
        Optional<Userentity> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(user);
    }

    public Userentity loginUser(String email, String password) {
        Optional<Userentity> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            Userentity foundUser = user.get();
            if (foundUser.getPassword().equals(password)) {
                return foundUser;
            }
        }
        throw new RuntimeException("Invalid email or password");
    }

    public Userentity getUserById(Long id) { // Changed int → Long
        return userRepository.findById(id).orElse(null);
    }

    public Userentity getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}