package com.example.kr_controller.service;

import com.example.kr_controller.model.Hello;
import com.example.kr_controller.model.User;
import com.example.kr_controller.repository.HelloRepository;
import com.example.kr_controller.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HelloService {
    private final HelloRepository repository;

    public Hello findHello() {
        return repository.findFirstHello();
    }
}
