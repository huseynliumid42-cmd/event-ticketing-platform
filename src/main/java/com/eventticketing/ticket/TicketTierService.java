package com.eventticketing.ticket;

import com.eventticketing.event.Event;
import com.eventticketing.event.EventRepository;
import com.eventticketing.ticket.TicketTier;
import com.eventticketing.ticket.TicketTierRepository;
import com.eventticketing.ticket.TicketTierRequest;
import com.eventticketing.ticket.TicketTierResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketTierService {

    private final TicketTierRepository ticketTierRepository;
    private final EventRepository eventRepository;

    public TicketTierResponse createTicketTier(Long eventId, TicketTierRequest request) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        TicketTier ticketTier = TicketTier.builder()
                .name(request.getName())
                .price(request.getPrice())
                .capacity(request.getCapacity())
                .salesStart(request.getSalesStart())
                .salesEnd(request.getSalesEnd())
                .event(event)
                .build();

        TicketTier saved = ticketTierRepository.save(ticketTier);

        return mapToResponse(saved);
    }

    public List<TicketTierResponse> getTicketTiersByEvent(Long eventId) {
        return ticketTierRepository.findByEventId(eventId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public TicketTierResponse getTicketTierById(Long id) {
        TicketTier ticketTier = ticketTierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket tier not found"));

        return mapToResponse(ticketTier);
    }

    public TicketTierResponse updateTicketTier(Long id, TicketTierRequest request) {

        TicketTier ticketTier = ticketTierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket tier not found"));

        ticketTier.setName(request.getName());
        ticketTier.setPrice(request.getPrice());
        ticketTier.setCapacity(request.getCapacity());
        ticketTier.setSalesStart(request.getSalesStart());
        ticketTier.setSalesEnd(request.getSalesEnd());

        TicketTier updated = ticketTierRepository.save(ticketTier);

        return mapToResponse(updated);
    }

    public void deleteTicketTier(Long id) {
        ticketTierRepository.deleteById(id);
    }

    private TicketTierResponse mapToResponse(TicketTier ticketTier) {
        return TicketTierResponse.builder()
                .id(ticketTier.getId())
                .name(ticketTier.getName())
                .price(ticketTier.getPrice())
                .capacity(ticketTier.getCapacity())
                .salesStart(ticketTier.getSalesStart())
                .salesEnd(ticketTier.getSalesEnd())
                .eventId(ticketTier.getEvent().getId())
                .build();
    }
}