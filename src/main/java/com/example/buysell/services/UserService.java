package com.example.buysell.services;

import com.example.buysell.exception.userException.UserActiveBannedException;
import com.example.buysell.models.User;
import com.example.buysell.models.enums.Role;
import com.example.buysell.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService  {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    public User findById(Long id){
        Optional <User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User not found");
        if (!user.get().isActive()){
            throw new UserActiveBannedException("User with email " + user.get().getEmail() + " is banned");
        }
        return user.get();
    }

    public boolean createUser(User user){
        String email = user.getEmail();
        if (userRepository.findByEmail(email) != null){
            return false;
        }
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        userRepository.save(user);
        log.info("Saving new User with email: {}", email);
        return true;
    }

    public List<User> listUser(){
        return userRepository.findAll();
    }


    public void banUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user!=null){
            if (user.getRoles().contains(Role.ROLE_ADMIN))
                return;
            else if (user.isActive()){
                user.setActive(false);
                log.info("Activated Ban user with id = {}; email = {}", user.getId(), user.getEmail());
            } else {
                user.setActive(true);
                log.info("Deactivated Ban user with id = {}; email = {}", user.getId(), user.getEmail());
            }
        }
        userRepository.save(user);
    }


    public void changeUserRoles(User user, Map<String, String> form) {
        Set <String> roles = Arrays.stream(Role.values()).map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key: form.keySet()){
            System.out.println(key);
            if (roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }

    public String userLoginFailed(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session!=null) {
            AuthenticationException authenticationException = (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (authenticationException!=null){
                if (authenticationException instanceof BadCredentialsException) {
                    session.invalidate();
                    return "Неверный логин или пароль";
                }
                if (authenticationException instanceof LockedException) {
                    session.invalidate();
                    return "Пользователь заблокирован, обратитесь к администрации сайта";
                }
            }
        }
        return "Введите логин и пароль";
    }
}
