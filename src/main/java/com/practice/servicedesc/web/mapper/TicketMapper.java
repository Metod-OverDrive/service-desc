package com.practice.servicedesc.web.mapper;

import com.practice.servicedesc.entity.Ticket;
import com.practice.servicedesc.web.dto.TicketDto;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    public Ticket toEntity(TicketDto dto) {
        return Ticket.builder()
                .id(dto.getId())
                .userName(dto.getUserName())
                .description(dto.getDescription())
                .pcNameOrIp(dto.getPcNameOrIp())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .status(dto.getStatus())
                .isClosed(dto.getIsClosed())
                .isOverdue(dto.getIsOverdue())
                .closedAt(dto.getClosedAt())
                .build();
    }

    public TicketDto toDto(Ticket entity) {
        return TicketDto.builder()
                .id(entity.getId())
                .userName(entity.getUserName())
                .description(entity.getDescription())
                .pcNameOrIp(entity.getPcNameOrIp())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .status(entity.getStatus())
                .isClosed(entity.getIsClosed())
                .isOverdue(entity.getIsOverdue())
                .closedAt(entity.getClosedAt())
                .build();
    }

}
