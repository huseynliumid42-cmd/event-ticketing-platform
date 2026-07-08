package com.eventticketing.event;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String location;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private Integer capacity;

    @Enumerated(EnumType.STRING)
    private EventStatus status;

    private LocalDateTime createdAt;
}
