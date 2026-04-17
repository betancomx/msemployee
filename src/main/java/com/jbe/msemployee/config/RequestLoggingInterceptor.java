package com.jbe.msemployee.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.util.Enumeration;


@Slf4j
@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // imprimiendo ruta y metodo HTPP
        log.info("--- Petición entrante: [{}] {} ---------", request.getMethod(), request.getRequestURI());
        // imprimir headers
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            // recorro cada header y lo imprimo en la consola
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                log.info("Header: {} = {}", headerName, request.getHeader(headerName));
            }
        }
        log.info("---------------------------------------------------");
        return true;
    }
}