package com.example.kr_controller.controller;

import com.example.kr_controller.service.HelloService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/start/")
@RequiredArgsConstructor
public class MainRESTController {
    private final HelloService userService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello, " + userService.findHello().getText());
    }

}
