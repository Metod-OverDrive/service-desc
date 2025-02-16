package com.practice.servicedesc.web.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.practice.servicedesc.entity.Ticket;
import com.practice.servicedesc.entity.User;
import com.practice.servicedesc.entity.enums.TicketWorkStatus;
import com.practice.servicedesc.web.dto.user.UserSpecDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class TicketWorkDto {

    private Long id;
    private Ticket ticket;
    private UserSpecDto specialist;
    private TicketWorkStatus ticketWorkStatus;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime assignedAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime unassignedAt;
}
