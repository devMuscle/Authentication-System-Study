package com.winterdevcamp.authentication.token;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final String tokenType = "X-AUTH-TOKEN";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(request.getMethod().equals("OPTIONS")) {
            return true;
        }

        String authToken = request.getHeader(tokenType);

        log.info("경로 : {}, 토큰 : {}", request.getServletPath(), authToken);

        if(authToken==null) {
            throw new RuntimeException("AccessToken이 없습니다.");
        }

        jwtTokenProvider.getSubject(authToken);

        return true;
    }
}
