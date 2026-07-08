package com.eventticketing.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public EventResponse createEvent(EventRequest request) {

        Event event = Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .location(request.getLocation())
                .startDateTime(request.getStartDateTime())
                .endDateTime(request.getEndDateTime())
                .capacity(request.getCapacity())
                .status(EventStatus.DRAFT)
                .createdAt(LocalDateTime.now())
                .build();

        Event savedEvent = eventRepository.save(event);

        return mapToResponse(savedEvent);
    }

    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public EventResponse getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        return mapToResponse(event);
    }

    private EventResponse mapToResponse(Event event) {
        return EventResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .location(event.getLocation())
                .startDateTime(event.getStartDateTime())
                .endDateTime(event.getEndDateTime())
                .capacity(event.getCapacity())
                .status(event.getStatus())
                .build();
    }
    public EventResponse updateEvent(Long id, EventRequest request) { 

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found")); 

        event.setTitle(request.getTitle()); 
        event.setDescription(request.getDescription()); 
        event.setLocation(request.getLocation()); 
        event.setStartDateTime(request.getStartDateTime()); 
        event.setEndDateTime(request.getEndDateTime()); 
        event.setCapacity(request.getCapacity()); 

        Event updatedEvent = eventRepository.save(event); 

        return mapToResponse(updatedEvent); 
    }
    public void deleteEvent(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        eventRepository.delete(event);
    }
}