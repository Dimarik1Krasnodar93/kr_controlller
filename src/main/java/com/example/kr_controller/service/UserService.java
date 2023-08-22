package com.example.kr_controller.service;

import com.example.kr_controller.dto.LoginRequest;
import com.example.kr_controller.model.User;
import com.example.kr_controller.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public boolean authenticated(LoginRequest loginRequest) {
        Optional<User> user =
                userRepository.findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        return user.isPresent();

    }
}
