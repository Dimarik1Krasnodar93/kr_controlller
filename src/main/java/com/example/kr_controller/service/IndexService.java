package com.example.kr_controller.service;

import com.example.kr_controller.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IndexService {
    @Value("${source-api-uri}")
    private String uri;
    private final WebClient client;

    public List<UserDto> findAll() {
        System.out.println("try " + uri);
        List list
         = client
                .get()
                .uri("%s/users/findAll".formatted(uri))
                .retrieve()
                .bodyToMono(List.class)
                .block();
        return list;
    }
}
