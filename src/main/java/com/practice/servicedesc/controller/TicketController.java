package com.practice.servicedesc.controller;

import com.practice.servicedesc.entity.Ticket;
import com.practice.servicedesc.entity.enums.TicketStatus;
import com.practice.servicedesc.service.impl.TicketServiceImpl;
import com.practice.servicedesc.web.dto.TicketDto;
import com.practice.servicedesc.web.mapper.TicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketServiceImpl ticketService;
    private final TicketMapper ticketMapper;

    @GetMapping
    public List<Ticket> getTicketsByUsername(@RequestParam("username") String username) {
        return ticketService.getTicketsByEmail(username);
    }

    @PostMapping
    public TicketDto createTicket(@RequestBody TicketDto dto) {
        Ticket ticket = ticketMapper.toEntity(dto);
        Ticket createdTicket = ticketService.createTicket(ticket);
        return ticketMapper.toDto(createdTicket);
    }

    @PostMapping("/select")
    public String selectTicket(@RequestParam Long ticketId, @RequestParam Long specialistId) {
        ticketService.selectTicket(ticketId, specialistId);
        return "Ticket selected by specialist!";
    }

    @PatchMapping("/status")
    public String updateTicketStatus(@RequestParam Long ticketId, @RequestParam TicketStatus newStatus) {
        ticketService.updateTicketStatus(ticketId, newStatus);
        return "Ticket status is updated!";
    }

    @DeleteMapping
    public String closeTicket(@RequestParam Long ticketId) {
        ticketService.closeTicket(ticketId);
        return "Ticket is closed!";
    }
}
