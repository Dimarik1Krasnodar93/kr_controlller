package com.example.kr_controller.controller;

import com.example.kr_controller.dto.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;


@RestController
public class IndexController {
    @GetMapping("/users/all")
    public List<UserDto> getAllUsers() {
        UserDto userDto = new UserDto("Petr", "Ivanov");
        return List.of(userDto);
    }
}
