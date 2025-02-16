package com.practice.servicedesc.service.impl;

import com.practice.servicedesc.entity.Specialist;
import com.practice.servicedesc.entity.Ticket;
import com.practice.servicedesc.entity.User;
import com.practice.servicedesc.entity.enums.TicketStatus;
import com.practice.servicedesc.repository.TicketRepository;
import com.practice.servicedesc.repository.UserRepository;
import com.practice.servicedesc.service.TicketService;
import com.practice.servicedesc.service.UserService;
import com.practice.servicedesc.web.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final RuntimeService runtimeService;
    private final TicketRepository ticketRepository;
    private final UserService userService;

    public List<Ticket> getTicketsByUserId(Long userId) {
        return ticketRepository.getByUserId(userId);
    }

    @Override
    public Ticket findTicketById(Long ticketId) {
        return ticketRepository.findById(ticketId).orElseThrow(() -> new EntityNotFoundException("Ticket doesn't exist."));
    }

    public Ticket createTicket(Ticket ticket) {
        String processId = runtimeService.createMessageCorrelation("UserCreateTicket")
                .correlateStartMessage().getProcessInstanceId();
        ticket.setProcessId(processId);
        return ticketRepository.save(ticket);
    }

    public void closeTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket doesn't exist"));

        if (ticket.getIsClosed()) {
            throw new RuntimeException("Ticket is already closed");
        }

        runtimeService.createMessageCorrelation("UserCloseTicket")
                .processInstanceId(ticket.getProcessId())
                .setVariable("ticketId", ticketId)
                .correlate();
    }

    public List<Ticket> getNotAssignedTickets() {
        return ticketRepository.getNotAssignedTickets();
    }

    public List<Ticket> getTicketsBySpecialist(Long specId, TicketStatus status) {
        List<Ticket> tickets = ticketRepository.getBySpecialistId(specId, status.toString());
        if (status == TicketStatus.IN_PROGRESS) {
            tickets = tickets.stream().filter(el -> !el.getIsClosed()).toList();
        }

        return tickets;
    }

    public void selectTicket(Long ticketId, Long specialistId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket doesn't exist."));
        User specialist = userService.findById(specialistId);

        runtimeService.createMessageCorrelation("SpecialistSelectTicket")
                .processInstanceId(ticket.getProcessId())
                .setVariables(Map.of("ticketId", ticketId, "specialistId", specialistId))
                .correlate();
    }

    public void updateTicketStatus(Long ticketId, TicketStatus newStatus) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket doesn't exist."));

        runtimeService.createMessageCorrelation("SpecialistUpdateTicketStatus")
                .processInstanceId(ticket.getProcessId())
                .setVariables(Map.of("newStatus", newStatus.toString()))
                .correlate();
    }
}
