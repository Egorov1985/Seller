package com.example.buysell.models;

import com.example.buysell.models.enums.Role;
import com.example.buysell.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class Admin implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        User admin = new User();
        admin.setActive(true);
        admin.setId(1L);
        admin.setDateOfCreated(new Date());
        admin.setRoles(Collections.singleton(Role.ROLE_ADMIN));
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setEmail("admin@admin.com");
        admin.setName("admin");
        admin.setPhoneNumber("8-495-555-05-35");
        userRepository.save(admin);
    }
}
