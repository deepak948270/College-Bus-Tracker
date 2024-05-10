package com.deepak.controller;

import java.security.Principal;
import java.util.Collection;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(value = "/testing")
    public String testing(Principal principal, Authentication authentication) {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        System.out.println(authorities);

        return """
                <h1 style='color:blue'>welcome %s to my application </h1>
                """.formatted(principal.getName());
    }

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @GetMapping("/user")
    public String normalUser() {
        return "normal user";
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String adminUser() {
        return "admin user";
    }

}
