package com.eventticketing.user;

import com.eventticketing.auth.RegisterRequest;
import com.eventticketing.auth.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public RegisterResponse register (@RequestBody RegisterRequest request){
        return userService.register(request);
    }
}
