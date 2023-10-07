package com.codewithdurgesh.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.entites.Category;
import com.codewithdurgesh.blog.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog.payloads.CategoryDto;
import com.codewithdurgesh.blog.repos.CategoryRepo;
import com.codewithdurgesh.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category cat=this.modelMapper.map(categoryDto, Category.class);
		Category addedCat=this.categoryRepo.save(cat);
		return this.modelMapper.map(addedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer catId) {
			Category cat= this.categoryRepo.findById(catId)
						  .orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", catId));
			cat.setCategoryTitle(categoryDto.getCategoryTitle());
			cat.setCategoryDesciption(categoryDto.getCategoryDesciption());
			Category updatedCat = this.categoryRepo.save(cat);
		return this.modelMapper.map(updatedCat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer catId) {
			Category cat = this.categoryRepo.findById(catId)
							.orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", catId));
			this.categoryRepo.delete(cat);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> catList = this.categoryRepo.findAll();
		List<CategoryDto> catDtos = catList.stream().map((cat)->this.modelMapper.map(cat, CategoryDto.class))
													.collect(Collectors.toList());
		return catDtos;
	}

	@Override
	public CategoryDto getSingleCategory(Integer catId) {
		Category cat = this.categoryRepo.findById(catId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", catId));
		return modelMapper.map(cat, CategoryDto.class);
	}
}
