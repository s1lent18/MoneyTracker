package com.example.MoneyTracker.service;

import com.example.MoneyTracker.models.model.Users;
import com.example.MoneyTracker.models.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Users> users = userRepository.findByEmail(email);

        if (users.isPresent()) {
            return createUserDetails(users.get());
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }

    private UserDetails createUserDetails(Object user) {
        if (user instanceof Users users) {
            return new org.springframework.security.core.userdetails.User(
                    users.getEmail(),
                    users.getPassword(),
                    List.of(new SimpleGrantedAuthority("USER"))
            );
        }
        throw new IllegalArgumentException("Unknown user type");
    }
}