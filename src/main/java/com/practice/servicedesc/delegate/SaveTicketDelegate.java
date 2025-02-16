package com.practice.servicedesc.delegate;

import com.practice.servicedesc.entity.Ticket;
import com.practice.servicedesc.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveTicketDelegate implements JavaDelegate {

    private final TicketRepository ticketRepository;

    @Override
    public void execute(DelegateExecution execution) {
        Long userId = (Long) execution.getVariable("userId");
        String userName = (String) execution.getVariable("userName");
        String description = (String) execution.getVariable("description");
        Object pcNameOrIp = execution.getVariable("pcNameOrIp");
        String processId = execution.getProcessInstanceId();

        Ticket ticket = new Ticket();
        ticket.setUserId(userId);
        ticket.setUserName(userName);
        ticket.setDescription(description);
        ticket.setProcessId(processId);
        if (pcNameOrIp != null) {
            ticket.setPcNameOrIp((String) pcNameOrIp);
        }

        ticketRepository.save(ticket);

        execution.setVariable("ticketId", ticket.getId());
    }
}
