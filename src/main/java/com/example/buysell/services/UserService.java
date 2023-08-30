package com.example.buysell.services;

import com.example.buysell.exception.UserBanException;
import com.example.buysell.models.User;
import com.example.buysell.models.enums.Role;
import com.example.buysell.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService  {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    public User findById(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> {
            return new UsernameNotFoundException("User not found");
        });
        if (!user.isActive()){
            throw new UserBanException("User with email " + user.getEmail() + " is banned");
        }
        return user;
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
            if (user.getRoles().contains("ROLE_ADMIN"))
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
}
