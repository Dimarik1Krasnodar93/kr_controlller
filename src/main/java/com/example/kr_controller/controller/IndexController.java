package com.example.kr_controller.controller;

import com.example.kr_controller.dto.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;


@RestController
public class IndexController {
    @GetMapping("/users/all")
    public List<User> getAllUsers() {
        return Collections.EMPTY_LIST;
    }
}
