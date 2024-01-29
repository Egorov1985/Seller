package com.example.buysell;

import com.example.buysell.dto.ProductDto;
import com.example.buysell.models.Product;
import com.example.buysell.models.User;
import com.example.buysell.models.enums.Role;
import com.example.buysell.repositories.ProductRepository;
import com.example.buysell.repositories.UserRepository;
import com.example.buysell.services.ProductService;
import com.example.buysell.services.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;

@SpringBootApplication
@AllArgsConstructor
@Slf4j
public class BuysellApplication {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(BuysellApplication.class, args);
    }

}
