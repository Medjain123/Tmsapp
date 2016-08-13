package com.deep.tmsapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deep.tmsapp.model.User;
import com.deep.tmsapp.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController
{
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<User> listAllUsers()
	{
		return this.userService.findAll();
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public User createUser(@RequestBody User argUser)
	{
		return this.userService.create(argUser);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public User userInformation(@PathVariable(value = "id") Integer argUserId)
	{
		return this.userService.findBy(argUserId);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable(value = "id") Integer argUserId)
	{
		this.userService.delete(argUserId);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public User updateUser(@RequestBody User argUser,
									 @PathVariable(value = "id") Integer argUserId)
	{
		return this.userService.update(argUserId, argUser);
	}
}
