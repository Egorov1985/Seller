package com.example.buysell.controllers;

import com.example.buysell.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationController {
    private final UserRepository userRepository;
}
