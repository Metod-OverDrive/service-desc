package com.practice.servicedesc.controller.api;

import com.practice.servicedesc.entity.Ticket;
import com.practice.servicedesc.entity.enums.TicketStatus;
import com.practice.servicedesc.security.JwtEntity;
import com.practice.servicedesc.service.TicketService;
import com.practice.servicedesc.web.dto.ticket.TicketDto;
import com.practice.servicedesc.web.mapper.TicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/spec/tickets")
@RequiredArgsConstructor
public class SpecTicketApiController {

    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    @GetMapping
    public List<TicketDto> showSpecialistTickets(@AuthenticationPrincipal JwtEntity details,
                                        @RequestParam(required = false) TicketStatus status) {
        if (status == null) {
            status = TicketStatus.IN_PROGRESS;
        }

        List<Ticket> tickets = ticketService.getTicketsBySpecialist(details.getId(), status);
        return ticketMapper.toDto(tickets);
    }

    @GetMapping("/not-assigned")
    public List<TicketDto> showNotAssignedTickets() {
        List<Ticket> tickets = ticketService.getNotAssignedTickets();
        return ticketMapper.toDtoWithoutTicketWork(tickets);
    }

    @PostMapping("/select")
    public ResponseEntity<?> selectTicket(@RequestParam Long ticketId, @RequestParam Long specialistId) {
        ticketService.selectTicket(ticketId, specialistId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/status")
    public ResponseEntity<?> updateTicketStatus(@RequestParam Long ticketId, @RequestParam TicketStatus newStatus) {
        ticketService.updateTicketStatus(ticketId, newStatus);
        return ResponseEntity.ok().build();
    }

}
