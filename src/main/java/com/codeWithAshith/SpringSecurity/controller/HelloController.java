package com.codeWithAshith.SpringSecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/user")
    public String hello() {
        return "Hello World";
    }
    @RequestMapping("/admin")
    public String helloAdmin() {
        return "Hello World Admin";
    }

}
