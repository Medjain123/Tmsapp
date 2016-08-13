package com.deep.tmsapp.service;

import java.util.List;





import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;


import com.deep.tmsapp.model.Ticket;
import com.deep.tmsapp.model.User;
import com.deep.tmsapp.repository.UserRepository;
import com.deep.tmsapp.service.TicketService;

@Service
@Transactional
public class UserService {
	@Autowired private UserRepository userRepo;
	@Autowired private TicketService ticketService;

	public List<User> findAll()
	{
		return this.userRepo.findAll();
	}

	public User create(User argUser)
	{
		if(userExist(argUser)) {
			throw new RuntimeException("User Exists"); 
		}
		
		argUser.setId(null);
		argUser.setActive(true);
		return this.userRepo.saveAndFlush(argUser);
	}

	private boolean userExist(User argUser)
	{
		return this.userRepo.findByName(argUser.getName()) != null ? true : false;
	}

	public User findBy(Integer argUserId)
	{
		if(argUserId == null) {
			throw new RuntimeException("Choose correct user!");
		}
		return this.userRepo.findOne(argUserId);
	}

	public void delete(Integer argUserId)
	{
		User user = checkAndReturnUser(argUserId);
		user.setActive(false);
		//this.unassignTickets(user);
		
		this.userRepo.saveAndFlush(user);
	}

	/*private void unassignTickets(User argUser)
	{
		List<Bug> userTickets = this.getTicketsBy(argUser.getId());
		userTickets.stream().forEach(bug -> {
			bug.setUser(null);
			this.ticketService.update(bug.getIssueId(), bug);
		});
	}*/

	private List<Ticket> getTicketsBy(Integer argUserId)
	{
		return this.ticketService.getTicketsBy(argUserId);
	}

	public User update(Integer argUserId, User argUser)
	{
		checkUser(argUserId);
		
		argUser.setId(argUserId);
		return this.userRepo.saveAndFlush(argUser);
	}

	private void checkUser(Integer argUserId)
	{
		User user = this.userRepo.findOne(argUserId);
		if(user == null) {
			throw new RuntimeException("User not exists");
		}
	}
	
	private User checkAndReturnUser(Integer argUserId)
	{
		User user = this.userRepo.findOne(argUserId);
		if(user == null) {
			throw new RuntimeException("User not exists");
		}
		return user;
	}

	public Integer count()
	{
		return this.userRepo.findAll().size();
	}

}
