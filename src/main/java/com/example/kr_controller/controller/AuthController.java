package com.example.kr_controller.controller;

import com.example.kr_controller.Component.JwtIssue;
import com.example.kr_controller.dto.LoginRequest;
import com.example.kr_controller.dto.LoginResponse;
import com.example.kr_controller.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final JwtIssue jwtIssue;
    private final UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest) throws Exception {
        if (!userService.authenticated(loginRequest)) {
            throw new Exception("no authenticated");
        }
        var token = jwtIssue.issue(1, loginRequest.getUsername(), List.of("USER"));
        return  LoginResponse.builder()
                .accessToken(token)
                .build();
    }
}
