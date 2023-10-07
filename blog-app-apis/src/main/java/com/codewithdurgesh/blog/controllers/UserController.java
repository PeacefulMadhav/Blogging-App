package com.codewithdurgesh.blog.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithdurgesh.blog.payloads.ApiResponse;
import com.codewithdurgesh.blog.payloads.UserDto;
import com.codewithdurgesh.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//CREATE - USER
	@PostMapping("/") // Handle HTTP POST requests at the root ("/") endpoint
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userdto) {
	    // Extract user data from the request body using @RequestBody annotation
	    // The 'userdto' parameter will be populated with the data sent in the request body

	    // Create a new user based on the provided userdto using a service method
	    UserDto createdUserDto = this.userService.createUser(userdto);

	    // Construct a ResponseEntity to send as the HTTP response
	    // Include the newly created user data in the response body
	    // Set the HTTP status code to 201 (HttpStatus.CREATED) indicating successful creation
	    return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
	}
	
	//UPDATE - USER
	// This API is used to update a user's information.
	@PutMapping("/{userId}") // Handle HTTP PUT requests with a user ID parameter in the URL
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userdto, @PathVariable Integer userId) {
	    // Extract user information from the request body (userdto) and the user ID from the URL (userId).
	    // The userDto parameter is automatically populated with data from the request body.

	    // Call a service method to update the user's information based on the provided data and user ID.
	    UserDto updatedUser = this.userService.updateUser(userdto, userId);

	    // Respond with the updated user information and HTTP status code 200 (OK).
	    return ResponseEntity.ok(updatedUser);
	}

	// DELETE - USER API
	@DeleteMapping("/{userId}") // Handle HTTP DELETE requests with a user ID parameter in the URL
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
	    // Extract the user ID from the URL (provided as a path variable).

	    // Call a service method to delete the user based on the specified user ID.
	    this.userService.deleteUser(userId);

	    // Respond with a message indicating successful user deletion and HTTP status code 200 (OK).
	    return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully",true), HttpStatus.OK);
	}

	
	// GET - ALL USERS API
	@GetMapping("/") // Handle HTTP GET requests at the root ("/") endpoint
	public ResponseEntity<List<UserDto>> getAllUsers() {
	    // Call a service method to retrieve a list of all users.

	    // Respond with the list of users as the response body and HTTP status code 200 (OK).
	    return ResponseEntity.ok(this.userService.getAllUsers());
	}

	
	// GET - USER API
	@GetMapping("/{userId}") // Handle HTTP GET requests with a user ID parameter in the URL
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId) {
	    // Extract the user ID from the URL (provided as a path variable).

	    // Call a service method to retrieve a single user based on the specified user ID.

	    // Respond with the user data as the response body and HTTP status code 200 (OK).
	    return ResponseEntity.ok(this.userService.getUserById(userId));
	}

}
