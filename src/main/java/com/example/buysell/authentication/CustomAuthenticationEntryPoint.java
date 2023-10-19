package com.example.buysell.authentication;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        HttpSession session = request.getSession(true);
        
        if (session != null) {
            String redirectUrl = request.getRequestURI();
            request.getSession().setAttribute("SESSION_REDIRECT_URL", redirectUrl);
            response.sendRedirect("/login?error");
        }
    }
}
