package com.practice.servicedesc.controller.web;

import com.practice.servicedesc.entity.Ticket;
import com.practice.servicedesc.service.impl.TicketServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("web/tickets")
@RequiredArgsConstructor
public class TicketWebController {

    private final TicketServiceImpl ticketService;

    @GetMapping("/list")
    public String listTickets(@RequestParam("username") String username, Model model) {
        List<Ticket> tickets = ticketService.getTicketsByEmail(username);
        model.addAttribute("tickets", tickets);
        model.addAttribute("username", username);
        return "ticket-list";
    }

    @GetMapping("/create")
    public String showCreateTicketForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "ticket-create";
    }

    @PostMapping("/create")
    public String createTicket(@ModelAttribute Ticket ticket, Model model) {
        Ticket createdTicket = ticketService.createTicket(ticket);
        model.addAttribute("ticket", createdTicket);
        return "redirect:/web/tickets/list?username=" + ticket.getUserName();
    }

    @DeleteMapping
    public String closeTicket(@RequestParam Long ticketId) {
        ticketService.closeTicket(ticketId);
        return "Ticket is closed!";
    }

}
