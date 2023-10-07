package com.codewithdurgesh.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.codewithdurgesh.blog.entites.User;
import com.codewithdurgesh.blog.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog.repos.UserRepo;

public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	private UserRepo userRepo;	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//Loading User form Database by username(email here)
		User user= this.userRepo.findByEmail(username)
					.orElseThrow(()->new ResourceNotFoundException("User", "email:"+username, 0));
		
		return user ;
	}

}
