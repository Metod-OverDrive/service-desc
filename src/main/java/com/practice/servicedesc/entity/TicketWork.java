package com.practice.servicedesc.entity;

import com.practice.servicedesc.entity.enums.TicketWorkStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets_work")
@Getter
@Setter
public class TicketWork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @OneToOne
    @JoinColumn(name = "specialist_id")
    private User specialist;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private TicketWorkStatus ticketWorkStatus;

    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;

    @Column(name = "unassigned_at")
    private LocalDateTime unassignedAt;
}
