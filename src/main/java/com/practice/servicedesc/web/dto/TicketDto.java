package com.practice.servicedesc.web.dto;

import com.practice.servicedesc.entity.enums.TicketStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketDto {

    private Long id;
    private String userName;
    private String description;
    private String pcNameOrIp;
    private TicketStatus status;
    private Boolean isClosed;
    private Boolean isOverdue;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime closedAt;
}
