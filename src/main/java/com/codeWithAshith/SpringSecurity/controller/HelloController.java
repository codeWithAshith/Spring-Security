package com.codeWithAshith.SpringSecurity.controller;

import com.codeWithAshith.SpringSecurity.model.Role;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class HelloController {

    @GetMapping("/hello")
//    @Secured(Role.ROLE_USER)
    public String hello() {
        return "Hello World";
    }

}
