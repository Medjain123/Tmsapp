package com.deep.tmsapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.deep.tmsapp.model.Ticket;
import com.deep.tmsapp.model.TicketPriority;
import com.deep.tmsapp.model.TicketStatus;
import com.deep.tmsapp.model.User;
import com.deep.tmsapp.repository.TicketPriorityRepository;
import com.deep.tmsapp.repository.TicketRepository;
import com.deep.tmsapp.repository.TicketStatusRepository;
import com.deep.tmsapp.service.UserService;

@Service
@Transactional
public class TicketService {
	@Autowired private TicketRepository ticketRepo;
	@Autowired private TicketStatusRepository ticketStatusRepo;
	@Autowired private TicketPriorityRepository ticketPriorityRepo;
	@Autowired private UserService userService;
	
	public List<Ticket> listAll()
	{
		return this.ticketRepo.findAll();
	}

	public List<Ticket> listUnassigned()
	{
		return this.ticketRepo.findUnassigned();
	}

	public Ticket create(Ticket argTicket)
	{
		this.setStatus(argTicket);
		this.setPriority(argTicket);
		
		argTicket.setIssueId(null);
		argTicket.setCreationDate(Calendar.getInstance().getTime());
		
		Ticket newTicket = this.ticketRepo.saveAndFlush(argTicket);
		if(argTicket.getUser() != null && argTicket.getUser().getId() != null) {
			User user = this.userService.findBy(argTicket.getUser().getId());
			newTicket.setUser(user);
			this.assign(Arrays.asList(newTicket.getIssueId()), user.getId());
		}
		
		return newTicket;
	}

	private void setUser(Ticket argTicket)
	{
		if(argTicket.getUser() != null && argTicket.getUser().getId() != null) {
			User user = this.userService.findBy(argTicket.getUser().getId());
			argTicket.setUser(user);
		}
	}

	@SuppressWarnings("unused")
	private void assignToUser(User argUser, Ticket argTicket)
	{
		if(argUser != null) {
			this.assign(Arrays.asList(argTicket.getIssueId()), argUser.getId());			
		}
	}
	
	private void setStatus(Ticket argTicket)
	{
		if(argTicket.getStatus() == null) {
			throw new IllegalArgumentException("Choose correct status!");
		}
		
		TicketStatus status = this.ticketStatusRepo.findOne(argTicket.getStatus().getId());
		if(status == null) {
			throw new IllegalArgumentException("Choose correct status!");
		}
		argTicket.setStatus(status);
	}

	private void setPriority(Ticket argTicket)
	{
		if(argTicket.getPriority() == null) {
			throw new IllegalArgumentException("Choose correct priority!");
		}
		
		TicketPriority priority = this.ticketPriorityRepo.findOne(argTicket.getPriority().getId());
		if(priority == null) {
			throw new IllegalArgumentException("Choose correct priority!");
		}
		argTicket.setPriority(priority);
	}

	public Ticket findBy(Integer argTicketId)
	{
		return this.ticketRepo.findOne(argTicketId);
	}

	public Ticket update(Integer argTicketId, Ticket argTicket)
	{
		Ticket ticket = this.ticketRepo.findOne(argTicketId);
		if(ticket == null) {
			throw new IllegalArgumentException("Choose correct ticket!");			
		}
		ticket.setIssueId(argTicketId);
		ticket.setStatus(argTicket.getStatus());
		ticket.setPriority(argTicket.getPriority());
		ticket.setUser(argTicket.getUser());
		ticket.setDescription(argTicket.getDescription());
		ticket.setTitle(argTicket.getTitle());
		
		this.setStatus(ticket);
		this.setPriority(ticket);
		this.setUser(ticket);

		return this.ticketRepo.saveAndFlush(ticket);
	}

	public List<TicketStatus> listStatus()
	{
		return this.ticketStatusRepo.findAll();
	}

	public TicketStatus createStatus(TicketStatus argTicketStatus)
	{
		argTicketStatus.setId(null);
		return this.ticketStatusRepo.saveAndFlush(argTicketStatus);
	}

	public List<TicketPriority> listPriority()
	{
		return this.ticketPriorityRepo.findAll();
	}

	public TicketPriority createPriority(TicketPriority argTicketPriority)
	{
		argTicketPriority.setId(null);
		return this.ticketPriorityRepo.saveAndFlush(argTicketPriority);
	}

	public void assign(List<Integer> argTicketIdList, Integer argUserId)
	{
		User user = this.userService.findBy(argUserId);
		if(user == null) {
			throw new IllegalArgumentException("Choose correct user!");			
		}
		
		List<Ticket> ticketListToAssign = new ArrayList<>();
		
		for(Integer itemId: argTicketIdList ){
			Ticket ticket = this.findBy(itemId);
			if(ticket == null) {
				throw new IllegalArgumentException("Choose correct tickets!");	
			}
			ticket.setUser(user);
			this.update(ticket.getIssueId(), ticket);
			ticketListToAssign.add(ticket);
		}
			
		user.setTickets(ticketListToAssign);
		this.userService.update(argUserId, user);
	}

	public List<Ticket> getTicketsBy(Integer argUserId)
	{
		return this.ticketRepo.getTicketsBy(argUserId);
	}

}
