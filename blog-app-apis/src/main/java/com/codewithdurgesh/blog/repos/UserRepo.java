package com.codewithdurgesh.blog.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithdurgesh.blog.entites.User;
import java.util.List;


public interface UserRepo extends JpaRepository<User, Integer>{
	//For Spring Security:
	//We are making email as a username for security 
	//Now emailId is username :
	Optional<User> findByEmail(String email);
	//This method is used in CustomDetailService.
}
