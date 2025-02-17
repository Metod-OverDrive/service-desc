package com.practice.servicedesc.controller.api;

import com.practice.servicedesc.entity.Ticket;
import com.practice.servicedesc.security.JwtEntity;
import com.practice.servicedesc.service.TicketService;
import com.practice.servicedesc.web.dto.ticket.TicketDto;
import com.practice.servicedesc.web.mapper.TicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users/tickets")
@RequiredArgsConstructor
public class UserTicketApiController {

    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    @GetMapping
    public List<Ticket> getTicketsByUserId(@AuthenticationPrincipal JwtEntity details) {
        return ticketService.getTicketsByUserId(details.getId());
    }

    @PostMapping
    public TicketDto createTicket(@RequestBody TicketDto dto) {
        Ticket ticket = ticketMapper.toEntity(dto);
        Ticket createdTicket = ticketService.createTicket(ticket);
        return ticketMapper.toDto(createdTicket);
    }

    @DeleteMapping
    public String closeTicket(@RequestParam Long ticketId) {
        ticketService.closeTicket(ticketId);
        return "Ticket is closed!";
    }

}
