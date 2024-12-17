package com.example.biblio.controller;

import com.example.biblio.obj.LoginForm;
import com.example.biblio.obj.User;
import com.example.biblio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login"; // This should return the login.html view
    }

    @PostMapping("/login")
    public String processLoginForm(@ModelAttribute("loginForm") LoginForm loginForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "login"; // Return to login if there are validation errors
        }

        User user = userService.findByUsername(loginForm.getUsername());

        if (user != null && passwordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
            // Successful authentication
            return "redirect:/a"; // Redirect to mainpage endpoint
        } else {
            // Unsuccessful authentication
            model.addAttribute("errorMessage", "Invalid username or password");
            return "login"; // Return to login with error message
        }
    }
}