package com.winterdevcamp.authentication.token;

import com.winterdevcamp.authentication.authority.AdminOnly;
import com.winterdevcamp.authentication.authority.NoAuth;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.security.SignatureException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final String tokenType = "X-AUTH-TOKEN";
    private final String preflight = "OPTIONS";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod hm = (HandlerMethod) handler;

        if(hm.hasMethodAnnotation(NoAuth.class)) {
            log.info("권한이 필요없는 요청입니다.");
            return true;
        }

        if (request.getMethod().equals(preflight)) {
            return true;
        }

        String token = request.getHeader(tokenType);

        log.info("경로 : {}, 토큰 : {}", request.getServletPath(), token);

        if (token == null) {
            response.sendError(401, "AccessToken이 없습니다");
            throw new ServletException("AccessToken이 없습니다");
        }

        try {
            jwtTokenProvider.getSubject(token);
        } catch (MalformedJwtException e) {
            response.sendError(401, "잘못된 형식의 Token입니다");
            log.info(e.toString());
        } catch (ExpiredJwtException e) {
            response.sendError(401, "Token의 기간이 만료되었습니다");
            log.info(e.toString());
        } catch (SignatureException e) {
            response.sendError(401, "Token의 Signarture가 잘못되었습니다.");
            log.info(e.toString());
        } catch (UnsupportedJwtException e) {
            response.sendError(401, "올바른 형식의 Token이 아닙니다.");
            log.info(e.toString());
        }

        if(hm.hasMethodAnnotation(AdminOnly.class) && (!jwtTokenProvider.getAuthority(token).equals("ADMIN"))) {
            response.sendError(401, "ADMIN 권한이 없습니다");
            log.info("ADMIN 권한이 없습니다.");
            return false;
        }

        return true;
    }
}
