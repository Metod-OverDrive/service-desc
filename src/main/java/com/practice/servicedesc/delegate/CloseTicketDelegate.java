package com.practice.servicedesc.delegate;

import com.practice.servicedesc.entity.Ticket;
import com.practice.servicedesc.repository.TicketRepository;
import com.practice.servicedesc.web.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CloseTicketDelegate implements JavaDelegate {

    private final TicketRepository ticketRepository;

    @Override
    public void execute(DelegateExecution execution) {
        Long ticketId = (Long) execution.getVariable("ticketId");

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket doesn't exist"));

        ticket.setIsClosed(true);
        ticket.setClosedAt(LocalDateTime.now());
        ticketRepository.save(ticket);
    }
}
