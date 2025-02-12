package com.practice.servicedesc.repository;

import com.practice.servicedesc.entity.Ticket;
import com.practice.servicedesc.entity.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> getTicketByUserNameIgnoreCase(String username);

    @Query(value = """
        SELECT * FROM tickets
        WHERE status = 'NEW' or status = 'REASSIGNED'
        """, nativeQuery = true)
    List<Ticket> getNotAssignedTickets();

}
