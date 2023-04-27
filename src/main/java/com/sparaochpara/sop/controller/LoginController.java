package com.sparaochpara.sop.controller;

import com.sparaochpara.sop.model.User;
import com.sparaochpara.sop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/login")
    public String showLoginForm() {
        return "index";
    }

    @PostMapping("/login")
    public String processLoginForm(@RequestParam String email, @RequestParam String password, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        Optional<User> userOptional = userRepository.findById(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                model.addAttribute("user", user);
                session.setAttribute("user", user);
                String sessionToken = generateSessionToken();
                session.setAttribute("sessionToken", sessionToken);
                redirectAttributes.addAttribute("sessionToken", sessionToken);
                return "redirect:/users";
            }
        }
        model.addAttribute("error", "Invalid Email or Password");
        return "index";

    }


    private String generateSessionToken() {
        return UUID.randomUUID().toString();
    }
}
