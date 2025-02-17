package com.practice.servicedesc.controller.web;

import com.practice.servicedesc.entity.Ticket;
import com.practice.servicedesc.security.JwtEntity;
import com.practice.servicedesc.service.impl.TicketServiceImpl;
import com.practice.servicedesc.web.dto.ticket.TicketDto;
import com.practice.servicedesc.web.mapper.TicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("web/users/tickets")
@RequiredArgsConstructor
public class UserTicketWebController {

    private final TicketServiceImpl ticketService;
    private final TicketMapper ticketMapper;

    @GetMapping
    public String getTicketsByUserId(@AuthenticationPrincipal JwtEntity details, Model model) {
        Long userId = details.getId();
        List<Ticket> tickets = ticketService.getTicketsByUserId(userId);
        List<TicketDto> ticketsDto = ticketMapper.toDtoWithoutTicketWork(tickets);

        model.addAttribute("tickets", ticketsDto);
        return "users/ticket-list";
    }

    @GetMapping("/create")
    public String showCreateTicketForm(Model model) {
        model.addAttribute("ticket", new TicketDto());
        return "users/ticket-create";
    }

    @PostMapping("/create")
    public String createTicket(@Validated @ModelAttribute TicketDto dto, @AuthenticationPrincipal JwtEntity details, Model model) {
        Ticket ticket = ticketMapper.toEntity(dto);
        ticket.setUserId(details.getId());
        ticket.setUserName(details.getUsername());
        Ticket createdTicket = ticketService.createTicket(ticket);

        model.addAttribute("ticket", createdTicket);
        return "redirect:/web/users/tickets";
    }

    @DeleteMapping
    public String closeTicket(@RequestParam Long ticketId) {
        ticketService.closeTicket(ticketId);
        return "users/ticket-list";
    }
}
