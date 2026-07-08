package com.eventticketing.user;

import com.eventticketing.auth.RegisterRequest;
import com.eventticketing.auth.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service 
@RequiredArgsConstructor 
public class UserService {

    private final UserRepository userRepository; 
    private final PasswordEncoder passwordEncoder; 

    public RegisterResponse register(RegisterRequest request) {

        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        
        User user = User.builder()
                .fullName(request.getFullName()) 
                .email(request.getEmail()) 
                .password(passwordEncoder.encode(request.getPassword())) 
                .role(request.getRole()) 
                .createdAt(LocalDateTime.now()) 
                .build();

        
        User savedUser = userRepository.save(user);

        
        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getFullName(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }
}