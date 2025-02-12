package com.practice.servicedesc.delegate;

import com.practice.servicedesc.entity.Ticket;
import com.practice.servicedesc.entity.TicketWork;
import com.practice.servicedesc.entity.enums.TicketStatus;
import com.practice.servicedesc.entity.enums.TicketWorkStatus;
import com.practice.servicedesc.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UpdateTicketStatusDelegate implements JavaDelegate {

    private final TicketRepository ticketRepository;

    @Override
    @Transactional
    public void execute(DelegateExecution execution) {
        Long ticketId = (Long) execution.getVariable("ticketId");
        TicketStatus newStatus = TicketStatus.valueOf((String) execution.getVariable("newStatus"));

        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        ticket.setStatus(newStatus);

        if (newStatus.equals(TicketStatus.COMPLETED)) {
            TicketWork work = ticket.getTicketWork();
            work.setTicketWorkStatus(TicketWorkStatus.COMPLETE);
            ticket.setIsClosed(true);
            ticket.setClosedAt(LocalDateTime.now());

        } else if (newStatus.equals(TicketStatus.REASSIGNED)) {
            TicketWork work = ticket.getTicketWork();
            work.setSpecialist(null);
            work.setTicketWorkStatus(TicketWorkStatus.UNASSIGN);
            work.setUnassignedAt(LocalDateTime.now());
            execution.removeVariable("specialistId");
        }
    }
}
