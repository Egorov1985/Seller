package com.example.buysell.configurations;

import com.example.buysell.models.User;
import com.example.buysell.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/login" ,
                        "/login-error",
                        "/registration",
                        "/images/**",
                        "/public/css/**",
                        "/public/js/**",
                        "/product/**",
                        "/user/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin(form -> form.loginPage("/login")
                        .defaultSuccessUrl("/product/products")
                        .failureHandler((request, response, exception) -> {
                            if (exception instanceof BadCredentialsException) {
                                response.sendRedirect("/login-error");
                            }
                            if (exception instanceof LockedException){
                                response.addHeader("error", exception.getMessage());
                                response.sendRedirect("/login-error");
                            }
                        }))
                .logout().logoutSuccessUrl("/login?logout").permitAll();


        return http.build();
    }


   /* @Override
    protected void configure(HttpSecurity http) throws Exception{
        http

                .authorizeRequests()
                .antMatchers("/",
                        "/login",
                        "/product/**",
                        "/registration",
                        "/images/**",
                        "/public/css/**",
                        "/public/js/**")
                .permitAll()
                        .antMatchers("/admin/**")
                        .hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                .and()
                        .formLogin()
                        .loginPage("/login")
                        .defaultSuccessUrl("/product")// страница на которую перенаправляет после успешного входа
                        .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                        .failureUrl("/login")
                        .permitAll()
                .and()
                        .exceptionHandling()
                       .authenticationEntryPoint(getAuthenticationEntryPoint())
                .and()
                       .logout()
                       .logoutSuccessUrl("/")
                       .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                       .permitAll();

    }


    public AuthenticationEntryPoint getAuthenticationEntryPoint(){
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request,
                                 HttpServletResponse response,
                                 AuthenticationException authException) throws IOException, ServletException{

                log.info("User not unauthorized");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "Unauthorized");
            }
        };
    }
    @Override
    protected void configure (AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }*/


}



