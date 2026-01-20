package com.example.hvbackend.ticketManagement;


import com.example.hvbackend.api.TicketsApi;
import com.example.hvbackend.dto.TicketCreateDTO;
import com.example.hvbackend.dto.TicketDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TickerController implements TicketsApi {

    private final TicketService ticketService;

    @Override
    public ResponseEntity<TicketDTO> createTicket(TicketCreateDTO ticketCreateDTO) {
        return ResponseEntity.ok(ticketService.createTicket(ticketCreateDTO));
    }

    @Override
    public ResponseEntity<Void> deleteTicketById(Long id) {
        ticketService.deleteTicketById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @Override
    public ResponseEntity<TicketDTO> getTicketById(Long id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @Override
    public ResponseEntity<TicketDTO> updateTicket(Long id, TicketDTO ticketDTO) {
        return ResponseEntity.ok(ticketService.updateTicketById(id, ticketDTO));
    }
}
