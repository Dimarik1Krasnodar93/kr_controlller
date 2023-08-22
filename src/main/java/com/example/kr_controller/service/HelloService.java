package com.example.kr_controller.service;

import com.example.kr_controller.model.Hello;
import com.example.kr_controller.repository.HelloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class HelloService {
    private final HelloRepository repository;

    public Hello findHello() {
        return repository.findFirstHello();
    }
}
