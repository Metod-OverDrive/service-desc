package com.practice.servicedesc.service.impl;

import com.practice.servicedesc.entity.Specialist;
import com.practice.servicedesc.entity.Ticket;
import com.practice.servicedesc.entity.enums.TicketStatus;
import com.practice.servicedesc.repository.SpecialistRepository;
import com.practice.servicedesc.repository.TicketRepository;
import com.practice.servicedesc.web.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl {

    private final RuntimeService runtimeService;
    private final TicketRepository ticketRepository;
    private final SpecialistRepository specialistRepository;

    public List<Ticket> getTicketsByEmail(String username) {
        return ticketRepository.getTicketByUserNameIgnoreCase(username);
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

    public void selectTicket(Long ticketId, Long specialistId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket doesn't exist."));
        Specialist specialist = specialistRepository.findById(specialistId)
                .orElseThrow(() -> new EntityNotFoundException("Specialist doesn't exist."));

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
