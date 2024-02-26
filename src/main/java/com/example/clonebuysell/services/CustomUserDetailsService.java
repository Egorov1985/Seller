package com.example.clonebuysell.services;


import com.example.clonebuysell.models.User;
import com.example.clonebuysell.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user==null) {
            log.info("User with email: {} not found.", email);
            throw new UsernameNotFoundException("User with email " + email + " not found.");
        }
        if (!user.isActive()){
            log.info("User with email: {} is not activated.", email);
        } else {
            log.info("Load by username with email: {}.", email);
        }
        return user;
    }
}
