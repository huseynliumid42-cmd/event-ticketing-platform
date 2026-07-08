package com.eventticketing.waitlist;

import com.eventticketing.event.Event;
import com.eventticketing.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "waitlist",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"event_id", "user_id"})
        }
)
@Getter
@Setter
public class Waitlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WaitlistStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime promotedAt;
}