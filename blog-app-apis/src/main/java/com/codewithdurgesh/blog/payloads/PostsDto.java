package com.codewithdurgesh.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.codewithdurgesh.blog.entites.Category;
import com.codewithdurgesh.blog.entites.Comments;
import com.codewithdurgesh.blog.entites.User;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostsDto {
	private Integer postId;
	
	private String title;
	
	private String content;
	
	private String imageName;

	private Date addedDate;
	
	//used Dtos because When Request is sent it goes in recursion. 
	private CategoryDto category;
	//used Dtos because When Request is sent it goes in recursion. 
	private UserDto user;
	
	//When the post is fetched automatically comments are also fetched with post:
	private Set<CommentsDto> comments=new HashSet<>();
}
