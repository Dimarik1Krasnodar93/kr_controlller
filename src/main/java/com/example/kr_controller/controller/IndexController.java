package com.example.kr_controller.controller;

import com.example.kr_controller.dto.UserDto;
import com.example.kr_controller.service.IndexService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;


@RestController
@AllArgsConstructor
public class IndexController {
    private final IndexService indexService;
    @GetMapping("/users/findAll")
    public List<UserDto> getAllUsers() {
        try {
            List list = indexService.findAll();
            return list;
        } catch (Exception ex) {
            UserDto userDto = new UserDto("Petr", "Ivanov");
            return List.of(userDto);
        }
    }
}
