package com.example.kr_controller.controller;

import com.example.kr_controller.Component.JwtIssue;
import com.example.kr_controller.dto.LoginRequest;
import com.example.kr_controller.dto.LoginResponse;
import com.example.kr_controller.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;


import org.apache.catalina.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final JwtIssue jwtIssue;
    private final UserService userService;

    @GetMapping("/hello")
    public String hello() {
        return "ok";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated LoginRequest loginRequest, HttpServletResponse response) throws Exception {
        if (!userService.authenticated(loginRequest)) {
            throw new Exception("no authenticated");
        }
        var token = jwtIssue.issue(1, loginRequest.getUsername(), List.of("USER"));
        Cookie cookie = new Cookie("JWT", token);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok("Login Successful");
    }
}
