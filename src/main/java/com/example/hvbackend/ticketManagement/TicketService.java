package com.example.hvbackend.ticketManagement;

import com.example.hvbackend.dto.TicketCreateDTO;
import com.example.hvbackend.dto.TicketDTO;
import com.example.hvbackend.exception.BusinessException;
import com.example.hvbackend.exception.ErrorCode;
import com.example.hvbackend.ticketManagement.entity.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    private final TicketMapper ticketMapper;

    public TicketDTO createTicket(TicketCreateDTO ticket) {
        Ticket ticketEntity = ticketMapper.toEntity(ticket);
        return ticketMapper.toDto(ticketRepository.save(ticketEntity));
    }

    public List<TicketDTO> getAllTickets() {
        return ticketRepository.findAll().stream()
                .map(ticketMapper::toDto)
                .toList();
    }

    public TicketDTO getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.BUILDING_NOT_FOUND));

        return ticketMapper.toDto(ticket);
    }

    public void deleteTicketById(Long id) {
        ticketRepository.deleteById(id);
    }

    public TicketDTO updateTicketById(Long id, TicketDTO ticketDTO) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow();
        ticketMapper.updateEntityFromDto(ticketDTO, ticket);
        return ticketMapper.toDto(ticketRepository.save(ticket));
    }


}
