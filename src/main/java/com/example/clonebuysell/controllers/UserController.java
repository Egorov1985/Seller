package com.example.clonebuysell.controllers;


import com.example.clonebuysell.exception.userException.UserNotFoundException;
import com.example.clonebuysell.models.User;
import com.example.clonebuysell.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error_account_not_activated",
            required = false) boolean error, @RequestParam(value = "username", required = false) String username,
                        Model model, HttpServletRequest request) {
        if (error)
            model.addAttribute("errorActivation",
                    "The account with email " +username +" is not activated.");
        return "/login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!userService.createUser(user)) {
            model.addAttribute("errorMessage",
                    "Пользователь с email: " + user.getEmail() + " уже существует.");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate (@PathVariable String code, Model model, HttpServletRequest request){
        boolean isActivated = userService.activateUser(code);
        if (isActivated){
            model.addAttribute("messageActivatedCode",
                    "User successfully activated.");
            request.getSession().setAttribute("SESSION_REDIRECT_URL", null);
        } else {
            model.addAttribute("messageActivatedCode",
                    "Account activation has already been completed!");
            request.getSession().setAttribute("SESSION_REDIRECT_URL", "http://localhost:8080/products");
        }
        return "/login";
    }



    @GetMapping("/user/{id}")
    public String userInfo(@PathVariable("id") Long id, Model model) {
        try {
            User user = userService.findById(id);
            model.addAttribute("user", user);
            model.addAttribute("products", user.getProducts());
        } catch (UserNotFoundException exception){
            model.addAttribute("userException", exception.getMessage());
        }
        return "user-info";
    }

    @GetMapping("/user/profile")
    public String profile(Principal principal, Model model){
        model.addAttribute("user", principal.getName());
        return "/profile";
    }

}
