package com.practice.servicedesc.repository;

import com.practice.servicedesc.entity.Ticket;
import com.practice.servicedesc.entity.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> getByUserId(Long id);
    List<Ticket> getTicketByUserNameIgnoreCase(String username);

    @Query(value = """
        SELECT * FROM tickets
        WHERE is_closed = false and status = 'NEW' or status = 'REASSIGNED'
        """, nativeQuery = true)
    List<Ticket> getNotAssignedTickets();

    @Query(value = """
        SELECT t.* FROM tickets t
        JOIN tickets_work tw ON t.id = tw.ticket_id
        WHERE tw.specialist_id = :specId and t.status = :status;
        """, nativeQuery = true)
    List<Ticket> getBySpecialistId(@Param("specId") Long specId, @Param("status") String status);

}
