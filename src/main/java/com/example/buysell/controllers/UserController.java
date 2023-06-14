package com.example.buysell.controllers;


import com.example.buysell.models.User;
import com.example.buysell.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.Column;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            return "registration";
        }
        if (!userService.createUser(userForm)) {
            model.addAttribute("errorMessage",
                    "Пользователь с email: " + userForm.getEmail() + " уже существует");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("hello")
    public String securityUrl(){
        return "hello";
    }


    @GetMapping("/user/{user}")
    public String userInfo(@PathVariable ("user") User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("products", user.getProducts());
        return "user-info";
    }
}
