package com.codewithdurgesh.blog.services.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.entites.Comments;
import com.codewithdurgesh.blog.entites.Posts;
import com.codewithdurgesh.blog.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog.payloads.CommentsDto;
import com.codewithdurgesh.blog.repos.CommentsRepo;
import com.codewithdurgesh.blog.repos.PostsRepo;
import com.codewithdurgesh.blog.services.CommentsService;

@Service
public class CommentsServiceImpl implements CommentsService{
	@Autowired
	private CommentsRepo commentRepo;
	@Autowired
	private PostsRepo postsRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentsDto createComment(CommentsDto commentDto, Integer postId) {
		Posts post = this.postsRepo.findById(postId)
				 .orElseThrow(()->new ResourceNotFoundException("Posts", "postId", postId));
		Comments comment = this.modelMapper.map(commentDto, Comments.class);
		comment.setPost(post);
		Comments savedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentsDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comments comment = this.commentRepo.findById(commentId)
		.orElseThrow(()->new ResourceNotFoundException("Comments", "commentId", commentId));
		this.commentRepo.delete(comment);
	}
}
