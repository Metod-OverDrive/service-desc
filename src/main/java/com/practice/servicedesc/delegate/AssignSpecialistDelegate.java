package com.practice.servicedesc.delegate;

import com.practice.servicedesc.entity.Specialist;
import com.practice.servicedesc.entity.enums.TicketStatus;
import com.practice.servicedesc.entity.enums.TicketWorkStatus;
import com.practice.servicedesc.repository.SpecialistRepository;
import com.practice.servicedesc.entity.Ticket;
import com.practice.servicedesc.entity.TicketWork;
import com.practice.servicedesc.repository.TicketRepository;
import com.practice.servicedesc.repository.TicketWorkRepository;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AssignSpecialistDelegate implements JavaDelegate {

    private final TicketWorkRepository ticketWorkRepository;
    private final TicketRepository ticketRepository;
    private final SpecialistRepository specialistRepository;

    @Override
    public void execute(DelegateExecution execution) {
        Long ticketId = (Long) execution.getVariable("ticketId");
        Long specialistId = (Long) execution.getVariable("specialistId");

        Specialist specialist = specialistRepository.findById(specialistId).orElse(null);
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);

        if (specialist == null || ticket == null) {
            throw new RuntimeException("Ticket or specialist isn't exist.");
        }

        ticket.setStatus(TicketStatus.IN_PROGRESS);

        TicketWork work = ticket.getTicketWork();

        if (work == null) {
            work = new TicketWork();
            work.setTicket(ticket);
        }

        work.setSpecialist(specialist);
        work.setTicketWorkStatus(TicketWorkStatus.ASSIGN);
        work.setAssignedAt(LocalDateTime.now());

        ticketWorkRepository.save(work);
    }
}
