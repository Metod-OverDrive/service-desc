package com.practice.servicedesc.delegate;

import com.practice.servicedesc.entity.Ticket;
import com.practice.servicedesc.entity.TicketWork;
import com.practice.servicedesc.entity.enums.TicketStatus;
import com.practice.servicedesc.entity.enums.TicketWorkStatus;
import com.practice.servicedesc.repository.TicketRepository;
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
class UpdateTicketStatusDelegateTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private DelegateExecution execution;

    @InjectMocks
    private UpdateTicketStatusDelegate updateTicketStatusDelegate;

    private Ticket mockTicket;
    private TicketWork mockWork;

    @BeforeEach
    void setUp() {
        mockWork = new TicketWork();
        mockWork.setTicketWorkStatus(TicketWorkStatus.ASSIGN);

        mockTicket = new Ticket();
        mockTicket.setId(100L);
        mockTicket.setStatus(TicketStatus.NEW);
        mockTicket.setIsClosed(false);
        mockTicket.setTicketWork(mockWork);
    }

    @Test
    void WhenValidStatusThenUpdateTicket() {
        when(execution.getVariable("ticketId")).thenReturn(100L);
        when(execution.getVariable("newStatus")).thenReturn("IN_PROGRESS");
        when(ticketRepository.findById(100L)).thenReturn(Optional.of(mockTicket));

        updateTicketStatusDelegate.execute(execution);

        assertEquals(TicketStatus.IN_PROGRESS, mockTicket.getStatus());
    }

    @Test
    void WhenCompletedThenCloseTicketAndUpdateWorkStatus() {
        when(execution.getVariable("ticketId")).thenReturn(100L);
        when(execution.getVariable("newStatus")).thenReturn("COMPLETED");
        when(ticketRepository.findById(100L)).thenReturn(Optional.of(mockTicket));

        updateTicketStatusDelegate.execute(execution);

        assertTrue(mockTicket.getIsClosed());
        assertNotNull(mockTicket.getClosedAt());
        assertEquals(TicketWorkStatus.COMPLETE, mockWork.getTicketWorkStatus());
    }

    @Test
    void WhenReassignedThenUnsetSpecialistAndUpdateWorkStatus() {
        when(execution.getVariable("ticketId")).thenReturn(100L);
        when(execution.getVariable("newStatus")).thenReturn("REASSIGNED");
        when(ticketRepository.findById(100L)).thenReturn(Optional.of(mockTicket));

        updateTicketStatusDelegate.execute(execution);

        assertNull(mockWork.getSpecialist());
        assertEquals(TicketWorkStatus.UNASSIGN, mockWork.getTicketWorkStatus());
        assertNotNull(mockWork.getUnassignedAt());
    }

    @Test
    void WhenTicketNotFoundThenThrowException() {
        when(execution.getVariable("ticketId")).thenReturn(100L);
        when(ticketRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> updateTicketStatusDelegate.execute(execution));
    }
}