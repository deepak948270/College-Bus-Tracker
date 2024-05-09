package com.deepak.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.deepak.entity.SecurityUser;
import com.deepak.repository.SecurityUserRepository;

@Controller
public class HomeController {

    private final SecurityUserRepository securityUserRepository;

    public HomeController(SecurityUserRepository securityUserRepository) {
        this.securityUserRepository = securityUserRepository;
    }

    @GetMapping(value = {"/home", "/"})
    public String viewHomePage(Authentication authentication, Model model) {

        if (authentication != null) {

            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            // Extract roles from authorities
            Collection<String> roles = authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            // Now you have roles in the 'roles' collection
            // You can iterate over it or perform any other operations
            for (String role : roles) {
                if (role.equals("ROLE_USER")) {
                    model.addAttribute("role", "Logged As USER ");
                } else if (role.equals("ROLE_ADMIN")) {
                    model.addAttribute("role", "Logged As ADMIN");
                } else if (role.equals("ROLE_DRIVER")) {
                    model.addAttribute("role", "Logged As DRIVER");
                }

            }

        } else {
            model.addAttribute("role", "Yet Not Logged !! ");
        }

        return "home";
    }

    @GetMapping(value = "/about")
    public String about() {
        return "about";
    }


    @GetMapping(value = "/input")
    public String inputMap() {
        return "input";
    }

    @GetMapping(value = "/map")
    public String showMap(@RequestParam(defaultValue = "28.644800") String latitude, @RequestParam(defaultValue = "77.216721") String longitude, Model model) {
        model.addAttribute("latitude", latitude);
        model.addAttribute("longitude", longitude);
        return "map";
    }

    @GetMapping(value = "/profile")
    public String profile(Principal principal,Model model) {
        // call the repository to get the user
        SecurityUser user = securityUserRepository.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("user not found "));
        model.addAttribute("name",principal.getName());
        model.addAttribute("description",user.getDescription());
        model.addAttribute("email",user.getEmail());

        return "profile";
    }

    @GetMapping(value = "/viewUsers")
    public String viewUsers(Model model){
        List<SecurityUser> users = securityUserRepository.findByRole("ROLE_USER");
        System.out.println(users);
        model.addAttribute("users",users);

        return "users";
    }

    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }

    @GetMapping("/accessDenied")
    public String accessdenied() {
        return "accessdenied";
    }
}
