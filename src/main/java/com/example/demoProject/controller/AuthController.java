package com.example.demoProject.controller;

import com.example.demoProject.model.User;
import com.example.demoProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@Controller
@RequestMapping("/auth")
public class AuthController {


    private final UserService userService;

    @Inject
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String showSignupPage() {
        return "signup";
    }

    @GetMapping("/signin")
    public String showSigninPage() {
        return "signin";
    }

    @PostMapping("/home")
    public String handleLogin(@RequestParam String email,
                              @RequestParam String password,
                              Model model) {
        // Check if user exists and password matches
        if (userService.authenticateUser(email, password)) {
            return "redirect:/auth/home"; // Redirect to home page on success
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "signin"; // Return to login page with an error message
        }
    }
    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }
    @PostMapping("/signup")
    public String register(@ModelAttribute User user, Model model) {
        String result = userService.registerUser(user);

        if (result.equals("User already exists")) {  // Ensure your service returns this message
            model.addAttribute("error", "User with this email already exists!");
            return "signup";  // Stay on signup page and show error
        }

        model.addAttribute("message", "Registration successful! You can now sign in.");
        return "signin"; // Redirect to sign-in after successful registration
    }

    @PostMapping("/signin")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        if (userService.authenticateUser(email, password)) {
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "signin";
        }
    }
}
