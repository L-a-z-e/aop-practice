package com.laze.aopboard.aop;

import com.laze.aopboard.exception.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class AuthAspect {

    @Before("@annotation(com.laze.aopboard.aop.annotation.AuthRequired)")
    public void validateAuthorization() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String authToken = request.getHeader("Authorization");
        log.info("Authorization Token: {}", authToken);

        if (!StringUtils.hasText(authToken)) {
            throw new AuthException("Authorization token is required.");
        }
    }
}
