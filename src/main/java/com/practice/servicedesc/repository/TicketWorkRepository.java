package com.practice.servicedesc.repository;

import com.practice.servicedesc.entity.TicketWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketWorkRepository extends JpaRepository<TicketWork, Long> {
}
