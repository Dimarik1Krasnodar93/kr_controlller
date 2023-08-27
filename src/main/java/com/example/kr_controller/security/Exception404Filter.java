package com.example.kr_controller.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class Exception404Filter extends OncePerRequestFilter {
    private final AntPathMatcher pathMatcher;

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var handle = getHandlerMethod(request.getRequestURI());
        if (handle == null) {
            response.setStatus(404);
        }
        filterChain.doFilter(request, response);
    }

    private HandlerMethod getHandlerMethod(String requestURI) {
        for (RequestMappingInfo info : requestMappingHandlerMapping.getHandlerMethods().keySet()) {
            PathPatternsRequestCondition patternsCondition = info.getPathPatternsCondition();
            if (patternsCondition != null) {
                for (PathPattern pattern : patternsCondition.getPatterns()) {
                    if (pathMatcher.match(pattern.getPatternString(), requestURI)) {
                        return requestMappingHandlerMapping.getHandlerMethods().get(info);
                    }
                }
            }
        }
        return null;
    }
}
