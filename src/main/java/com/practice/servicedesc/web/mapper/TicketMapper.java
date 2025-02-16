package com.practice.servicedesc.web.mapper;

import com.practice.servicedesc.entity.Ticket;
import com.practice.servicedesc.web.dto.ticket.TicketDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface TicketMapper {

    Ticket toEntity(TicketDto dto);

    TicketDto toDto(Ticket entity);

    List<TicketDto> toDto(List<Ticket> entityList);

    @Named("WithoutTicketWork")
    @Mapping(target = "ticketWork", ignore = true)
    TicketDto toDtoWithoutTicketWork(Ticket entity);

    @IterableMapping(qualifiedByName = "WithoutTicketWork")
    @Mapping(target = "ticketWork", ignore = true)
    List<TicketDto> toDtoWithoutTicketWork(List<Ticket> entityList);
}
