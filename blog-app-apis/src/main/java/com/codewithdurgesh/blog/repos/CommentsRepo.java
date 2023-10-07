package com.codewithdurgesh.blog.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithdurgesh.blog.entites.Comments;

public interface CommentsRepo extends JpaRepository<Comments, Integer>{
	
}
