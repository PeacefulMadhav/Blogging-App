package com.codewithdurgesh.blog.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.payloads.CategoryDto;


public interface CategoryService {
	//POST:
	public CategoryDto createCategory(CategoryDto categoryDto);
	//UPDATE:
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer catId);
	//DELETE:
	public void deleteCategory(Integer catId);
	//GET ALL:
	public List<CategoryDto> getAllCategory();
	//GET SINGLE CATEGORY:
	public CategoryDto getSingleCategory(Integer catId);

}