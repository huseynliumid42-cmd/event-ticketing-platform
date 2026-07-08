package com.eventticketing.ticket;

import com.eventticketing.user.User;
import com.eventticketing.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final UserRepository userRepository;

    @GetMapping("/my")
    public ResponseEntity<List<TicketResponse>> getMyTickets(
            Authentication authentication
    ) {

        
        String email = authentication.getName();

        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        
        return ResponseEntity.ok(
                ticketService.getMyTickets(user)
        );
    }
}