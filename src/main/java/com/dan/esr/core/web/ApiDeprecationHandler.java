package com.dan.esr.core.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiDeprecationHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler
    ) {
        // TODO: Deprecar a versão 1
        /*if (request.getRequestURI().startsWith("/v1/")) {
            response.addHeader("X-DanFood-Deprecated",
                    "Essa versão da API está depreciada e deixará de existir a partir de 01/01/2025. " +
                            "Use a versão atual da API.");
        }*/

        // TODO: Remover a versão 1
        if (request.getRequestURI().startsWith("/v1/")) {
            response.setStatus(HttpStatus.GONE.value());
            return false;
        }
        return true;
    }
}