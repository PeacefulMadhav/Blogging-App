package com.codewithdurgesh.blog.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithdurgesh.blog.entites.Category;

public interface CategoryRepo extends  JpaRepository<Category, Integer>{

}
