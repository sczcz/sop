package com.sparaochpara.sop.controller;

import com.sparaochpara.sop.dto.CategoryDto;
import com.sparaochpara.sop.dto.TransactionDto;
import com.sparaochpara.sop.dto.UserDto;
import com.sparaochpara.sop.model.User;
import com.sparaochpara.sop.repository.TransactionRepository;
import com.sparaochpara.sop.repository.UserRepository;
import com.sparaochpara.sop.service.TransactionService;
import com.sparaochpara.sop.service.UserService;
import com.sparaochpara.sop.service.impl.TransactionServiceImpl;
import com.sparaochpara.sop.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;



@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String listUsers(@AuthenticationPrincipal Principal principal, Model model) {
        if (principal == null) {
            System.out.println("ERROR PRINCIPAL NULL");
            return "redirect:/login";
        }
        String userEmail = principal.getName();
        List<UserDto> users = userService.findAllUsers();
        String firstName = userService.findUserByEmail(userEmail).getFirstName();
        model.addAttribute("users", users);
        model.addAttribute("firstName", firstName);
        System.out.println("PRINCIPAL CREATED!!!");
        return "test";
    }

    @GetMapping("/users/{email}")
    public String userDetail(@PathVariable("email") String email, Model model){
        UserDto userDto = userService.findUserByEmail(email);
        model.addAttribute("user", userDto);
        return "users-detail";
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

    @GetMapping("/users/{email}/edit")
    public String editUserForm(@PathVariable("email") String email, Model model){
        UserDto user = userService.findUserByEmail(email);
        model.addAttribute("user", user);
        return "users-edit";
    }

    @PostMapping("/users/{email}/edit")
    public String updateUser(@PathVariable("email") String email,@Valid @ModelAttribute("user") UserDto user
    , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "users-edit";
        }
        user.setEmail(email);
        userService.updateUser(user);
        return "redirect:/users";
    }
}
