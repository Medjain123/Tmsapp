package com.deep.tmsapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deep.tmsapp.model.Ticket;
import com.deep.tmsapp.model.User;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
	@Query(value="from Ticket b where b.user.id is null order by b.issueId asc")
	public List<Ticket> findUnassigned();

	@Query(value="from Ticket b order by b.issueId asc")
	public List<Ticket> findAll();

	@Query(value="from Ticket b where b.user.id = :userId order by b.issueId asc")
	public List<Ticket> getTicketsBy(@Param(value = "userId") Integer argDeveloperId);

}
