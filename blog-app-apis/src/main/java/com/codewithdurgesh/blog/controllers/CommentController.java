package com.codewithdurgesh.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithdurgesh.blog.entites.Comments;
import com.codewithdurgesh.blog.payloads.ApiResponse;
import com.codewithdurgesh.blog.payloads.CommentsDto;
import com.codewithdurgesh.blog.services.CommentsService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	@Autowired	
	private CommentsService commentsService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentsDto> createComments(@RequestBody CommentsDto commentsDto,
														@PathVariable Integer postId){
		CommentsDto comment = this.commentsService.createComment(commentsDto, postId);
		return new ResponseEntity<CommentsDto>(comment, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<ApiResponse> deleteComments(@PathVariable Integer commentId){
		 this.commentsService.deleteComment(commentId);
		 return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted successfully",true),
				 																		HttpStatus.OK);
	}
	
}
