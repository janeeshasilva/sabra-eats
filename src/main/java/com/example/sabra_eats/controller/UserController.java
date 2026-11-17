package com.example.sabra_eats.controller;

import com.example.sabra_eats.entity.User;
import com.example.sabra_eats.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Register Page එක පෙන්වීම
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    // අලුත් User කෙනෙක් සේව් කිරීම
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "මේ Email එකෙන් දැනටමත් Account එකක් ඇත!");
            return "register";
        }
        userRepository.save(user);
        return "redirect:/login?registered=true";
    }

    // Login Page එක පෙන්වීම
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // Login වීම (Session එකක් හැදීම)
    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user != null) {
            session.setAttribute("loggedInUser", user); // යූසර්ව මතක තියාගන්නවා
            return "redirect:/";
        } else {
            model.addAttribute("error", "Email හෝ Password වැරදියි!");
            return "login";
        }
    }

    // Logout වීම (Session එක මකා දැමීම)
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // යූසර්ව අමතක කරනවා
        return "redirect:/login?logout=true";
    }
}