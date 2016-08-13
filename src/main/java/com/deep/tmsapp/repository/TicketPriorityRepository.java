package com.deep.tmsapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deep.tmsapp.model.TicketPriority;

public interface TicketPriorityRepository extends JpaRepository<TicketPriority, Integer> {

}
