package org.launchcode.demo.controllers;

import org.launchcode.demo.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class SecurityController {

    @Autowired
    UserService userService;



    @PreAuthorize("hasRole('admin')")
    @GetMapping("/admin")
    public String adminEndpoint(){
        return "Hello, Admin";
    }


    @GetMapping("/user")
    public String userEndpoint() { return "Hello, User"; }
}
