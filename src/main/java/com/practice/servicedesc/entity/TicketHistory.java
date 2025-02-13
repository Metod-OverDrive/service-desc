package com.practice.servicedesc.entity;

import com.practice.servicedesc.entity.enums.TicketWorkStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets_history")
@Getter
@Setter
public class TicketHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "specialist_id", referencedColumnName = "id")
    private User specialist;

    @Column(name = "previous_status")
    private String previousStatus;

    @Column(name = "new_status")
    private String newStatus;

    @Column(name = "action_type")
    private TicketWorkStatus actionType;

    @Column(name = "action_at")
    private LocalDateTime actionAt;
}
