package com.codewithdurgesh.blog.services.impl;

import java.util.List;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.exceptions.*;
import com.codewithdurgesh.blog.entites.User;
import com.codewithdurgesh.blog.payloads.UserDto;
import com.codewithdurgesh.blog.repos.UserRepo;
import com.codewithdurgesh.blog.services.UserService;

import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		//To Save date of Dto into User entity 
		User u=this.dtoToUser(userDto);
		//User saved should be persisted through repo with the db:
		User savedUser=this.userRepo.save(u);
		//Again user from db is converted into userDto and returned 
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userdto, Integer userId) {
		//Fetch user from repo using corresponding Id or else throw the error with ResourceNotFoundException class declared in exceptions package:
		User user=this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		
		//Now persist the changes in db by getting data from dto and setting in user 
		user.setName(userdto.getName());
		user.setEmail(userdto.getEmail());
		user.setPassword(userdto.getPassword());
		user.setAbout(userdto.getAbout());
		
		//save the user back in db by repo: 
		User updatedUser=this.userRepo.save(user);
		
		//return updatedUser:
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		//Find user by userId through Jparepo 
		User user=this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		//return userdto:
		return userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtoList=users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return userDtoList;
		//1.First, you convert the list of User objects (users) into a Java Stream using .stream(). A Stream is a way to process a sequence of elements in a functional style.
		//2.Next, you use the .map() function to transform each User object in the Stream into a corresponding UserDto object. 
		//This transformation is accomplished by calling the userToDto method for each User. 
		//The userToDto method is responsible for converting a User object into a UserDto object.
		//3.Finally, you use .collect(Collectors.toList()) to collect the transformed UserDto objects back into a list.
		//This resulting list, now containing UserDto objects, is stored in the userDtoList variable.
	}

	@Override
	public void deleteUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		this.userRepo.delete(user);
	}
	
	//For the conversion of dto to user
	private User dtoToUser(UserDto userDto) {
	//CONVERTING THE DTO TO USER BY MODEL MAPPER:	
		User user =this.modelMapper.map(userDto, User.class);
		
	//MANUALLY CONVERTING THE DTO TO USER:
		//User user=new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		return user;
	}
	
	//For the conversion of user to dto
	private UserDto userToDto(User user) {
	//CONVERTING THE DTO TO USER BY MODEL MAPPER:	
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
		
	//MANUALLY CONVERTING THE USER TO DTO :
//		UserDto userDto=new UserDto();
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		return userDto;
	}
}
