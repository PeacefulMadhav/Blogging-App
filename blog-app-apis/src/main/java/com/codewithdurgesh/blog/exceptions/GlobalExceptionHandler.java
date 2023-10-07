package com.codewithdurgesh.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.codewithdurgesh.blog.payloads.ApiResponse;


//This class serves as a global exception handler for the entire Spring Boot application.
@RestControllerAdvice
public class GlobalExceptionHandler {
	
 // This method handles exceptions of type ResourceNotFoundException.
 @ExceptionHandler(ResourceNotFoundException.class)
 public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
     // Extract the error message from the exception.
     String message = ex.getMessage();
     
     // Create an ApiResponse object with the error message and a "false" flag to indicate failure.
     ApiResponse apiResponse = new ApiResponse(message, false);
     
     // Return a ResponseEntity with the ApiResponse as the response body and an HTTP status code of 404 (NOT FOUND).
     return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
 }
 
 @ExceptionHandler(MethodArgumentNotValidException.class)
 public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex) {
     // Create a HashMap to store validation error details
     Map<String, String> resp = new HashMap<>();

     // Loop through each validation error
     ex.getBindingResult().getAllErrors().forEach((error) -> {
         String fieldName = ((FieldError) error).getField();
         String message = error.getDefaultMessage();

         // Add the field name and error message to the HashMap
         resp.put(fieldName, message);
     });

     // Return a ResponseEntity with the HashMap of validation errors and HTTP status code 400 (BAD_REQUEST)
     return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
 }
}

