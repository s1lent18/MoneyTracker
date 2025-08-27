package com.example.MoneyTracker.controller;

import com.example.MoneyTracker.JWT.JwtUtil;
import com.example.MoneyTracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping(name = "/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserDetailsService userDetailsService;

    @PostMapping("/signUp")
    public ResponseEntity<?> addUser(@RequestBody UserService.AddUserRequest request) {

        try {
            UserService.AddUserResponse userSignInResponse = userService.addUser(request);

            return ResponseEntity.status(HttpStatus.CREATED).body(userSignInResponse);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserService.UserLoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

            UserService.AddUserResponse getResponse = userService.getUser(request.email);

            getResponse.token = jwtUtil.generateToken(userDetails);

            Map<String, UserService.AddUserResponse> response = new HashMap<>();
            response.put("userData", getResponse);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            Map<String, UserService.AddUserResponse> response = new HashMap<>();

            return ResponseEntity.badRequest().body(response);
        }
    }
}
