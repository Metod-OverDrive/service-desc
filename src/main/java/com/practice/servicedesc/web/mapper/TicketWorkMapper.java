package com.practice.servicedesc.web.mapper;

import com.practice.servicedesc.entity.TicketWork;
import com.practice.servicedesc.web.dto.ticket.TicketWorkDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketWorkMapper extends Mappable<TicketWork, TicketWorkDto>{
}
