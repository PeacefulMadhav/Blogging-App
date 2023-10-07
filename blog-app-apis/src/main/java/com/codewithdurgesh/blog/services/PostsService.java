package com.codewithdurgesh.blog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.codewithdurgesh.blog.entites.Posts;
import com.codewithdurgesh.blog.payloads.PostResponse;
import com.codewithdurgesh.blog.payloads.PostsDto;
import com.codewithdurgesh.blog.repos.PostsRepo;

public interface PostsService {
	
	//Create:
	public PostsDto createPost(PostsDto postDto, Integer categoryId, Integer userId);
	
	//Update:
	public PostsDto updatePost(PostsDto postDto, Integer postId);
	
	//Delete:
	public Void deletePost(Integer postId);
	
	//Get Single Post:
	public PostsDto getPostById(Integer postId);
	
	//Get All Posts:
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	//Get All Posts By Category:
	public List<PostsDto> getPostByCategory(Integer categoryId);
	
	//Get All Posts By User:
	public List<PostsDto> getPostByUser(Integer userId);
	
	//Search Posts
	public List<PostsDto> searchPost(String keyword);

}
