package com.example.MoneyTracker.service;

import com.example.MoneyTracker.models.model.Users;
import com.example.MoneyTracker.models.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Setter @Getter @NoArgsConstructor @AllArgsConstructor
    public static class AddUserRequest {
        public String email;
        public String password;
        public String name;
        public Integer budget;
        public Integer total;
    }

    @Setter @Getter @NoArgsConstructor
    public static class AddUserResponse {
        public String email;
        public Integer id;
        public String name;
        public Integer budget;
        public Integer total;
        public String token;

        public AddUserResponse(String email, Integer id, String name, Integer budget, Integer total) {
            this.email = email;
            this.id = id;
            this.name = name;
            this.budget = budget;
            this.total = total;
        }
    }

    @Setter @Getter @NoArgsConstructor @AllArgsConstructor
    public static class UserLoginRequest {
        public String email;
        public String password;
    }

    public AddUserResponse addUser(AddUserRequest request) {
        if (userRepository.findByEmail(request.email).isPresent()) {
            throw new RuntimeException("User Already Exist");
        }

        Users user = new Users(
                request.name,
                request.email,
                request.password,
                request.budget,
                request.total
        );

        Users savedUser = userRepository.save(user);

        savedUser.setPassword(passwordEncoder.encode(savedUser.getPassword()));

        userRepository.save(savedUser);

        return new AddUserResponse(
                user.getEmail(),
                user.getId(),
                user.getName(),
                user.getBudget(),
                user.getTotal()
        );
    }

    public AddUserResponse getUser(String email) {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User Not Found"));

        return new AddUserResponse(
                user.getEmail(),
                user.getId(),
                user.getName(),
                user.getBudget(),
                user.getTotal()
        );
    }
}
