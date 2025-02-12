package com.practice.servicedesc.entity;

import com.practice.servicedesc.entity.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "process_id")
    private String processId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "description")
    private String description;

    @OneToOne(mappedBy = "ticket", fetch = FetchType.LAZY)
    @JoinColumn()
    private TicketWork ticketWork;

    @Column(name = "pc_name_or_ip")
    private String pcNameOrIp;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private TicketStatus status;

    @Column(name = "is_closed")
    private Boolean isClosed;

    @Column(name = "is_overdue")
    private Boolean isOverdue;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;

    @PrePersist
    private void onCreate() {
        this.status = TicketStatus.NEW;
        this.isClosed = false;
        this.isOverdue = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
