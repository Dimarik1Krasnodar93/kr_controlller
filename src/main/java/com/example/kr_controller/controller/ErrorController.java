package com.example.kr_controller.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/404")
    public ResponseEntity<?> t() {
        return ResponseEntity.status(404).build();
    }
}
