package com.eventticketing.auth;

import com.eventticketing.user.User;
import com.eventticketing.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service 
@RequiredArgsConstructor 
public class AuthService {

    private final UserRepository userRepository; 
    private final PasswordEncoder passwordEncoder; 
    private final JwtService jwtService; 

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail()) 
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(), 
                user.getPassword() 
        );

        if (!passwordMatches) { 
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail()); 

        return new LoginResponse(token); 
    }
}