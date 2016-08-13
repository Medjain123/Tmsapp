package com.deep.tmsapp.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.deep.tmsapp.model.Ticket;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="USER")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="USERID")
	private Integer id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="ACTIVE")
	private boolean active = true;
	
	@OneToMany(targetEntity=User.class, mappedBy="user", fetch=FetchType.LAZY)
	@JsonManagedReference(value="user-tickets")
	private List<Ticket> tickets;
	
	public Integer getId()
	{
		return this.id;
	}
	
	public void setId(Integer argId)
	{
		this.id = argId;
	}
	
	public String getName()
	{
		return this.name;
	}

	public void setName(String argName)
	{
		this.name = argName;
	}
	
	public boolean isActive()
	{
		return this.active;
	}
	
	public void setActive(boolean argActive)
	{
		this.active = argActive;
	}
	
	public List<Ticket> getTickets()
	{
		return this.tickets;
	}
	
	public void setTickets(List<Ticket> argTickets)
	{
		this.tickets = argTickets;
	}
	

}
