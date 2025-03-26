package com.example.demoProject.service;

import com.example.demoProject.model.User;
import com.example.demoProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

//    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean authenticateUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return user.get().getPassword().equals(password); // ⚠️ Plain text comparison (improve with hashing)
        }
        return false;
    }

    public String registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "User already exists";
        }
        userRepository.save(user);
        return "Registration successful";
    }

}
