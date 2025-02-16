package com.practice.servicedesc.service;

import com.practice.servicedesc.entity.Ticket;
import com.practice.servicedesc.entity.enums.TicketStatus;

import java.util.List;

public interface TicketService {

    Ticket findTicketById(Long ticketId);

    Ticket createTicket(Ticket ticket);

    void closeTicket(Long ticketId);

    List<Ticket> getNotAssignedTickets();

    public List<Ticket> getTicketsBySpecialist(Long specId, TicketStatus status);

    void selectTicket(Long ticketId, Long specialistId);

    void updateTicketStatus(Long ticketId, TicketStatus newStatus);
}
