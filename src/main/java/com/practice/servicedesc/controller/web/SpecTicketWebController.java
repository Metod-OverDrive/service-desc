package com.practice.servicedesc.controller.web;

import com.practice.servicedesc.entity.Ticket;
import com.practice.servicedesc.entity.enums.TicketStatus;
import com.practice.servicedesc.security.JwtEntity;
import com.practice.servicedesc.service.TicketService;
import com.practice.servicedesc.web.dto.ticket.TicketDto;
import com.practice.servicedesc.web.mapper.TicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("web/spec/tickets")
@RequiredArgsConstructor
public class SpecTicketWebController {

    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    @GetMapping
    public String showSpecialistTickets(@AuthenticationPrincipal JwtEntity details,
                                        @RequestParam(required = false) TicketStatus status,
                                        Model model) {
        if (status == null) {
            status = TicketStatus.IN_PROGRESS;
        }

        List<Ticket> tickets = ticketService.getTicketsBySpecialist(details.getId(), status);
        List<TicketDto> ticketsDto = ticketMapper.toDto(tickets);
        List<TicketStatus> statusList = List.of(TicketStatus.IN_PROGRESS, TicketStatus.COMPLETED);

        model.addAttribute("tickets", ticketsDto);
        model.addAttribute("statusList", statusList);
        return "spec/specialist-tickets";
    }

    @GetMapping("/not-assigned")
    public String showNotAssignedTickets(Model model) {
        List<Ticket> tickets = ticketService.getNotAssignedTickets();
        List<TicketDto> ticketsDto = ticketMapper.toDtoWithoutTicketWork(tickets);

        model.addAttribute("tickets", ticketsDto);
        return "spec/not-assigned-tickets";
    }

    @PostMapping("/select")
    public String selectTicket(@AuthenticationPrincipal JwtEntity details, @RequestParam Long ticketId) {
        Ticket ticket = ticketService.findTicketById(ticketId);
        TicketStatus status = ticket.getStatus();
        if (status == TicketStatus.NEW || status == TicketStatus.REASSIGNED) {
            ticketService.selectTicket(ticketId, details.getId());
        }
        return "redirect:/web/spec/tickets";
    }

    @PatchMapping("/status")
    public String updateTicketStatus(@RequestParam Long ticketId, @RequestParam TicketStatus newStatus) {
        ticketService.updateTicketStatus(ticketId, newStatus);
        return "spec/specialist-tickets";
    }



}
