package com.mainproject.be28.domain.member.controller;

import com.mainproject.be28.domain.member.service.Layer1.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
public class AuthController {

    public AuthController(AuthService authService) {
    }

    @DeleteMapping("/logOut")
    public ResponseEntity logOut(){

        return new ResponseEntity(HttpStatus.OK);
    }
}