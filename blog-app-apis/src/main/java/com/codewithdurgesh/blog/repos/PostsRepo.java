package com.codewithdurgesh.blog.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithdurgesh.blog.entites.Category;
import com.codewithdurgesh.blog.entites.Posts;
import com.codewithdurgesh.blog.entites.User;

public interface PostsRepo extends JpaRepository<Posts, Integer>{
	
	//Custom Finder methods:
	List<Posts> findByUser(User user);
	List<Posts> findByCategory(Category category);
	//For Searching By Title:
	List<Posts> findByTitleContaining(String title);
}
