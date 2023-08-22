package com.example.kr_controller.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.kr_controller.Component.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtDecoder {
    private final JwtProperties jwtProperties;
    public DecodedJWT decode(String token) {
        return JWT.require(Algorithm.HMAC256(jwtProperties.getSecretKey()))
                .build().verify(token);
    }
}
