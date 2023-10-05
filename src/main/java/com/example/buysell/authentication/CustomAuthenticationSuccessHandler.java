package com.example.buysell.authentication;

import com.example.buysell.models.enums.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        String redirectUrl = (String) session.getAttribute("SESSION_REDIRECT_URL");

        if (redirectUrl==null){
            for (GrantedAuthority authority: authentication.getAuthorities()) {
                if (authority.equals(Role.ROLE_ADMIN))
                    redirectUrl = "/admin";
                if (authority.equals(Role.ROLE_USER))
                    redirectUrl = "/product/products";
            }
        }
        response.sendRedirect(redirectUrl);
    }
}
