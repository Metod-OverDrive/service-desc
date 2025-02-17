package com.practice.servicedesc.delegate;

import com.practice.servicedesc.entity.Ticket;
import com.practice.servicedesc.repository.TicketRepository;
import com.practice.servicedesc.web.exception.EntityNotFoundException;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CloseTicketDelegateTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private DelegateExecution execution;

    @InjectMocks
    private CloseTicketDelegate closeTicketDelegate;

    private Ticket mockTicket;

    @BeforeEach
    void setUp() {
        mockTicket = new Ticket();
        mockTicket.setId(100L);
        mockTicket.setIsClosed(false);
        mockTicket.setClosedAt(null);
    }

    @Test
    void WhenValidDataThenCloseTicket() {
        when(execution.getVariable("ticketId")).thenReturn(100L);
        when(ticketRepository.findById(100L)).thenReturn(Optional.of(mockTicket));

        closeTicketDelegate.execute(execution);

        assertTrue(mockTicket.getIsClosed());
        assertNotNull(mockTicket.getClosedAt());
    }

    @Test
    void WhenTicketNotExistThenThrowException() {
        when(execution.getVariable("ticketId")).thenReturn(100L);
        when(ticketRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            closeTicketDelegate.execute(execution);
        });
    }
}