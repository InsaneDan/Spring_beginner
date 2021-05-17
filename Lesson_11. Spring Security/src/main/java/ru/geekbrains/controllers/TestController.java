package ru.geekbrains.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/test")
public class TestController {

    private final UserDetailsService userDetailsService;

    @GetMapping({"", "/"})
    public String homePage() {
        return "home";
    }

    @GetMapping("/unsecured")
    public String usecuredPage() {
        return "unsecured";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/user_authentication")
    public String userPageAuthentication(Authentication authentication) {
        return "Authenticated user info: " + authentication.getName() + " authorities: " + authentication.getAuthorities();
    }

    @GetMapping("/user_principal")
    public String userPagePrincipal(Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        return "Authenticated user info: " + userDetails.getUsername() + " authorities: " + userDetails.getAuthorities();
    }

}
