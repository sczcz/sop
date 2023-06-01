package com.sparaochpara.sop.controller;

import com.sparaochpara.sop.dto.UserDto;
import com.sparaochpara.sop.model.Transaction;
import com.sparaochpara.sop.model.User;
import com.sparaochpara.sop.repository.TransactionRepository;
import com.sparaochpara.sop.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@Controller
public class UserController {
    private final UserService userService;
    private final TransactionRepository transactionRepository;

    @Autowired
    public UserController(UserService userService, TransactionRepository transactionRepository) {
        this.userService = userService;
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/users")
    public String showLatestUserTransactions(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        else {
            String userEmail = userDetails.getUsername();
            String firstName = userService.findUserByEmail(userEmail).getFirstName();
            List<Transaction> userTransactions = transactionRepository.findTopNByUserEmailOrderByCreatedOnDesc(userEmail, 10);
            model.addAttribute("userTransactions", userTransactions);
            model.addAttribute("firstName", firstName);
            return "home";
        }

    }

    @GetMapping("/users/new")
    public String createUserForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "users-create";
    }

    @PostMapping("/users/new")
    public String saveUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("user", userDto);
            return "users-create";
        }
        else {
            List<UserDto> exist = userService.findAllUsers();
            for (UserDto user : exist) {
                if (user.getEmail().equals(userDto.getEmail())) {
                    model.addAttribute("error", "Email already exists");
                    return "users-create";
                }
            }
        }
        String encodedPassword = new BCryptPasswordEncoder().encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);

        userService.saveUser(userDto);
        return "redirect:/users";
    }

}
