package com.deep.tmsapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deep.tmsapp.model.Ticket;
import com.deep.tmsapp.model.*;
import com.deep.tmsapp.service.*;

@RestController
@RequestMapping("/ticket")
public class TicketController
{
	@Autowired private TicketService ticketService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<Ticket> listAllTickets()
	{
		return this.ticketService.listAll();
	}
	
	@RequestMapping(value = "/unassigned/list", method = RequestMethod.GET)
	public List<Ticket> listUnassignedTickets()
	{
		return this.ticketService.listUnassigned();
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Ticket createTicket(@RequestBody Ticket argTicket)
	{
		return this.ticketService.create(argTicket);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Ticket ticketInformation(@PathVariable(value = "id") Integer argTicketId)
	{
		return this.ticketService.findBy(argTicketId);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Ticket updateTicket(@RequestBody Ticket argTicket, @PathVariable(value = "id") Integer argTicketId)
	{
		return this.ticketService.update(argTicketId, argTicket);
	}
	
	@RequestMapping(value = "/status/list", method = RequestMethod.GET)
	public List<TicketStatus> listAllTicketStatus()
	{
		return this.ticketService.listStatus();
	}
	
	@RequestMapping(value = "/status/create", method = RequestMethod.POST)
	public TicketStatus createTicketStatus(@RequestBody TicketStatus argTicketStatus)
	{
		return this.ticketService.createStatus(argTicketStatus);
	}
	
	@RequestMapping(value = "/priority/list", method = RequestMethod.GET)
	public List<TicketPriority> listAllTicketPriority()
	{
		return this.ticketService.listPriority();
	}
	
	@RequestMapping(value = "/priority/create", method = RequestMethod.POST)
	public TicketPriority createTicketPriority(@RequestBody TicketPriority argTicketPriority)
	{
		return this.ticketService.createPriority(argTicketPriority);
	}
	
	@RequestMapping(value = "/assign/{ticketIds}/user/{userId}", method = RequestMethod.POST)
	public void assignToDeveloper(@PathVariable(value = "ticketIds") List<Integer> argTicketIds, 
								  @PathVariable(value = "userId") Integer argDeveloperId) 
	{
		this.ticketService.assign(argTicketIds, argDeveloperId);
	}
}

