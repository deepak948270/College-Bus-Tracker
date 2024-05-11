package com.deepak.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.deepak.entity.User;
import com.deepak.service.UserService;

@Controller
@RequestMapping(value = "/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signup() {
        return "signup";
    }

    @PostMapping(value = "/handler")

    public String signupHandler(User user) {

        User savedUser = userService.createUser(user);

        System.out.println(savedUser);

        return "redirect:/newLogin";

    }
}
