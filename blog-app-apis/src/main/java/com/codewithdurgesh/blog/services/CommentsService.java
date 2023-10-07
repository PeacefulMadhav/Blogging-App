package com.codewithdurgesh.blog.services;

import com.codewithdurgesh.blog.payloads.CommentsDto;

public interface CommentsService {
	//Create
		CommentsDto createComment(CommentsDto commentDto, Integer postId);
	//Delete 
		void deleteComment(Integer commentId);
		
//	//Update
//		CommentsDto updateComment(CommentsDto commentDto, Integer postId);
//	//Get
//		CommentsDto getComment();
//	//Get All Comments:
//		List<CommentsDto> getAllComments();
}
