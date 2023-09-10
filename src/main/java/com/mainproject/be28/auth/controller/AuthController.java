package com.mainproject.be28.auth.controller;

import com.mainproject.be28.auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @DeleteMapping("/logOut")
    public ResponseEntity logOut(){

        return new ResponseEntity(HttpStatus.OK);
    }
}