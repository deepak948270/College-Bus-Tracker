package com.deepak.controller;

import com.deepak.entity.SecurityUser;
import com.deepak.service.SecurityUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/signup")
public class SignupController {

    private final SecurityUserService securityUserService;

    public SignupController(SecurityUserService securityUserService) {
        this.securityUserService = securityUserService;
    }

    @GetMapping
    public String signup() {
        return "signup";
    }


    @PostMapping(value = "/handler")

    public String signupHandler(SecurityUser user) {

        SecurityUser savedUser = securityUserService.createUser(user);
        System.out.println(savedUser);


        return "redirect:/login";

    }
}
