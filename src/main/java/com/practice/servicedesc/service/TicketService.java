package com.practice.servicedesc.service;

import com.practice.servicedesc.entity.Ticket;
import com.practice.servicedesc.entity.enums.TicketStatus;

import java.util.List;

public interface TicketService {

    List<Ticket> getTicketsByEmail(String username);

    Ticket createTicket(Ticket ticket);

    void closeTicket(Long ticketId);

    List<Ticket> getNotAssignedTickets();

    void selectTicket(Long ticketId, Long specialistId);

    void updateTicketStatus(Long ticketId, TicketStatus newStatus);
}
