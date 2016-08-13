package com.deep.tmsapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deep.tmsapp.model.TicketStatus;

public interface TicketStatusRepository extends JpaRepository<TicketStatus, Integer> {

}
