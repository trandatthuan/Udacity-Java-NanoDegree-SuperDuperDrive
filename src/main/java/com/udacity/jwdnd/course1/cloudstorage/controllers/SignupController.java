package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("signup")
public class SignupController {
    private final UserService userService;

    public SignupController(UserService userService) { this.userService = userService; }

    @GetMapping()
    public String getSignUpView() { return "signup"; }

    @PostMapping()
    public String signUp(@ModelAttribute User user, Model model) {
        String signUpError = null;

        if (!userService.isUsernameAvailable(user.getUsername())) {
            signUpError = "The username already exists.";
        }

        if (signUpError == null) {
            int rowAdded = userService.createUser(user);

            if (rowAdded < 0) {
                signUpError = "There was an error registering your new account. Please try again.";
            }
        }

        if (signUpError == null) {
            model.addAttribute("signUpSuccess", true);
        } else {
            model.addAttribute("signUpError", signUpError);
        }
        return "signup";
    }

}
