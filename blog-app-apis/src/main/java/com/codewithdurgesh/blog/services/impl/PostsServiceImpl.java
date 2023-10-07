package com.codewithdurgesh.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.entites.Category;
import com.codewithdurgesh.blog.entites.Posts;
import com.codewithdurgesh.blog.entites.User;
import com.codewithdurgesh.blog.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog.payloads.PostResponse;
import com.codewithdurgesh.blog.payloads.PostsDto;
import com.codewithdurgesh.blog.repos.CategoryRepo;
import com.codewithdurgesh.blog.repos.PostsRepo;
import com.codewithdurgesh.blog.repos.UserRepo;
import com.codewithdurgesh.blog.services.PostsService;

@Service
public class PostsServiceImpl implements PostsService{
	@Autowired
	private PostsRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	@Override
	public PostsDto createPost(PostsDto postDto,Integer categoryId, Integer userId) {
		//Fetch the User by User Id from userRepo:
		User user = this.userRepo.findById(userId)
				  .orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
		
		//Fetch the Category by category Id from catRepo:
		Category cat = this.categoryRepo.findById(categoryId)
				  .orElseThrow(()->new ResourceNotFoundException("Category", "catId", categoryId));
		
		Posts post = this.modelMapper.map(postDto, Posts.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(cat);
		Posts newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostsDto.class);
	}

	@Override
	public PostsDto updatePost(PostsDto postDto, Integer postId) {
		Posts post = this.postRepo.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
//		post.setAddedDate(postDto.getAddedDate());
//		post.setCategory(postDto.getCategory());
		post.setImageName(postDto.getImageName());
		Posts updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostsDto.class);
	}

	@Override
	public Void deletePost(Integer postId) {
		Posts post = this.postRepo.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		this.postRepo.delete(post);
		return null;
	}

	@Override
	public PostsDto getPostById(Integer postId) {
		Posts post = this.postRepo.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		return this.modelMapper.map(post, PostsDto.class);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
		}
		else {
			sort=Sort.by(sortBy).descending();
		}
		
//		int pageSize=5;
//		int pageNumber=1;		
		Pageable p=PageRequest.of(pageNumber, pageSize, sort);
		Page<Posts> pagePost = this.postRepo.findAll(p);
		List<Posts> allposts = pagePost.getContent();
		
		List<PostsDto> postDtos = allposts.stream().map((post)->modelMapper.map(post, PostsDto.class))
										.collect(Collectors.toList());
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}
 
	@Override
	public List<PostsDto> getPostByCategory(Integer categoryId) {
		//Fetch the Category by Category id from categoryRepo:
		Category cat = this.categoryRepo.findById(categoryId)
				 .orElseThrow(()->new ResourceNotFoundException("Category", "catId", categoryId));
		//Fetch the List of Posts related to that Category:
		List<Posts> postsList = this.postRepo.findByCategory(cat);
		//Convert the List of Posts into List of PostDtos:
		List<PostsDto> postDtoList = postsList.stream().map((post)->this.modelMapper.map(post, PostsDto.class))
						  .collect(Collectors.toList());
		return postDtoList;
	}

	@Override
	public List<PostsDto> getPostByUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
		
		List<Posts> postsList = this.postRepo.findByUser(user);
		List<PostsDto> postDtoList = postsList.stream().map((post)->this.modelMapper.map(post, PostsDto.class))
				  .collect(Collectors.toList());
		return postDtoList;
	}

	@Override
	public List<PostsDto> searchPost(String keyword) {
		List<Posts> posts = this.postRepo.findByTitleContaining(keyword); 
		List<PostsDto> postsDtoList = posts.stream().map((post)->this.modelMapper.map(post, PostsDto.class))
											.collect(Collectors.toList());
		return postsDtoList;
	}
}
