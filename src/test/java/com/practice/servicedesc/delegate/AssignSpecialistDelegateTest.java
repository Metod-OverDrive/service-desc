package com.practice.servicedesc.delegate;

import com.practice.servicedesc.entity.Ticket;
import com.practice.servicedesc.entity.TicketWork;
import com.practice.servicedesc.entity.User;
import com.practice.servicedesc.entity.enums.TicketStatus;
import com.practice.servicedesc.entity.enums.TicketWorkStatus;
import com.practice.servicedesc.repository.TicketRepository;
import com.practice.servicedesc.repository.TicketWorkRepository;
import com.practice.servicedesc.repository.UserRepository;
import com.practice.servicedesc.web.exception.EntityNotFoundException;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssignSpecialistDelegateTest {

    @Mock
    private TicketWorkRepository ticketWorkRepository;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private DelegateExecution execution;
    @InjectMocks
    private AssignSpecialistDelegate assignSpecialistDelegate;

    private User mockUser;
    private Ticket mockTicket;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);

        mockTicket = new Ticket();
        mockTicket.setId(100L);
        mockTicket.setStatus(TicketStatus.NEW);
    }

    @Test
    void WhenValidDataThenUpdateStatus() {
        when(execution.getVariable("ticketId")).thenReturn(100L);
        when(execution.getVariable("specialistId")).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(ticketRepository.findById(100L)).thenReturn(Optional.of(mockTicket));
        when(ticketWorkRepository.save(any(TicketWork.class))).thenReturn(new TicketWork());

        assignSpecialistDelegate.execute(execution);

        assertThat(mockTicket.getStatus()).isEqualTo(TicketStatus.IN_PROGRESS);
    }

    @Test
    void WhenTicketNotExistThenThrowException() {
        when(execution.getVariable("ticketId")).thenReturn(100L);
        when(execution.getVariable("specialistId")).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(ticketRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> assignSpecialistDelegate.execute(execution));
    }

    @Test
    void WhenSpecialistNotExistThenThrowException() {
        when(execution.getVariable("ticketId")).thenReturn(100L);
        when(execution.getVariable("specialistId")).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        when(ticketRepository.findById(100L)).thenReturn(Optional.of(mockTicket));

        assertThrows(EntityNotFoundException.class, () -> assignSpecialistDelegate.execute(execution));
    }

    @Test
    void WhenTicketWorkExistThenNotCreateNew() {
        TicketWork existingWork = new TicketWork();
        existingWork.setSpecialist(new User());
        existingWork.setAssignedAt(LocalDateTime.now().minusDays(1));
        existingWork.setTicketWorkStatus(TicketWorkStatus.ASSIGN);

        mockTicket.setTicketWork(existingWork);

        when(execution.getVariable("ticketId")).thenReturn(100L);
        when(execution.getVariable("specialistId")).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(ticketRepository.findById(100L)).thenReturn(Optional.of(mockTicket));

        assignSpecialistDelegate.execute(execution);

        assertThat(existingWork.getSpecialist()).isEqualTo(mockUser);
        assertThat(existingWork.getTicketWorkStatus()).isEqualTo(TicketWorkStatus.ASSIGN);
    }
}