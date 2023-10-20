package com.example.buysell.authentication;

import com.example.buysell.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final CustomUserDetailsService detailsService;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        HttpSession session = request.getSession();
        System.out.println(authException.getMessage());

        if (session != null) {
            String redirectUrl = request.getRequestURI();
            request.getSession().setAttribute("SESSION_REDIRECT_URL", redirectUrl);
            if (!detailsService.loadUserByUsername(request.getParameter("username")).isEnabled() &&
            detailsService.loadUserByUsername(request.getParameter("username"))!=null) {
                response.sendRedirect("/login?error_account_not_activated=true&&username=" +
                        request.getParameter("username"));
                request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, "Error authentication.");
            } else
                response.sendRedirect("/login?error");
        }
    }
}
