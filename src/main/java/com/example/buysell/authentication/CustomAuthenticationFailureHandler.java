package com.example.buysell.authentication;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        super.onAuthenticationFailure(request, response, exception);

        if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
            setDefaultFailureUrl("/login?error_account_not_activated=true&&username=" +
                    request.getParameter("username"));
            request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,
                    exception.getMessage());
        }
    }
}
