package com.codeWithAshith.SpringSecurity.controller;

import com.codeWithAshith.SpringSecurity.model.ContactUser;
import com.codeWithAshith.SpringSecurity.repository.ContactUserService;
import com.codeWithAshith.SpringSecurity.response.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private APIResponse apiResponse;

    @Autowired
    private ContactUserService contactUserService;

    @PostMapping("/login")
    public ResponseEntity<APIResponse> login(@RequestBody ContactUser contactUser) {

        System.out.println(contactUser.toString());
        ContactUser user = contactUserService.login(contactUser);

        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(user);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<APIResponse> register(@RequestBody ContactUser contactUser) {

        ContactUser user = contactUserService.register(contactUser);

        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(user);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
