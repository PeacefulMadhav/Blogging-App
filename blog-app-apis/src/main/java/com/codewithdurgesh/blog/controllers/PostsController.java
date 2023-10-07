package com.codewithdurgesh.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codewithdurgesh.blog.config.AppConstants;
import com.codewithdurgesh.blog.entites.Posts;
import com.codewithdurgesh.blog.payloads.ApiResponse;
import com.codewithdurgesh.blog.payloads.PostResponse;
import com.codewithdurgesh.blog.payloads.PostsDto;
import com.codewithdurgesh.blog.services.FileService;
import com.codewithdurgesh.blog.services.PostsService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;

@RestController
@RequestMapping("/api/")
public class PostsController {
	@Autowired
	private PostsService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	//CREATE-POST
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostsDto> createPost(@RequestBody PostsDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId)
	{	
		PostsDto createPost = this.postService.createPost(postDto, categoryId, userId);
		return new ResponseEntity<PostsDto>(createPost, HttpStatus.CREATED);
	}
	
	//GET POSTS BY USER
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostsDto>> getPostsByUser(@PathVariable Integer userId){
		List<PostsDto> postsByUser = this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostsDto>>(postsByUser, HttpStatus.OK);
	}
	
	//GET POSTS BY CATEGORY
		@GetMapping("/category/{catId}/posts")
		public ResponseEntity<List<PostsDto>> getPostsByCategory(@PathVariable Integer catId){
			List<PostsDto> postsByCat = this.postService.getPostByCategory(catId);
			return new ResponseEntity<List<PostsDto>>(postsByCat, HttpStatus.OK);
		}
		
	//GET POST BY ID:
		@GetMapping("/posts/{postId}")
		public ResponseEntity<PostsDto> getPost(@PathVariable Integer postId){
			PostsDto postById = this.postService.getPostById(postId);
			return new ResponseEntity<PostsDto>(postById, HttpStatus.OK);
		}
		
	//GET ALL POST:
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
						@RequestParam(value="pageNumber",defaultValue =AppConstants.PAGE_NUMBER,required=false)Integer pageNumber,
						@RequestParam(value="pageSize",defaultValue = AppConstants.PAGE_SIZE,required=false)Integer pageSize,
						@RequestParam(value ="sortBy",defaultValue = AppConstants.SORT_BY,required = false)String sortBy,
						@RequestParam(value ="sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir
			){
		 PostResponse allPost = this.postService.getAllPost(pageNumber,pageSize,sortBy, sortDir);
		return new ResponseEntity<PostResponse>(allPost, HttpStatus.OK);
	}
	
	//DELETE POST
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId){
		Void postById = this.postService.deletePost(postId);
		return new ApiResponse("Post Deleted Successfully", true);
	}
	
	//UPDATE POST
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostsDto> updatePost(@RequestBody PostsDto postDto, @PathVariable Integer postId){
		PostsDto updatedPost = this.postService.updatePost(postDto, postId);		
		return new ResponseEntity<PostsDto>(updatedPost, HttpStatus.OK);
	}
	
	//SEARCH POST:
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostsDto>> searchPostByTitle(@PathVariable String keywords){
		List<PostsDto> searchdPost = this.postService.searchPost(keywords);
		return new ResponseEntity<List<PostsDto>>(searchdPost, HttpStatus.OK);
	}
	
	//UPLOAD IMAGE:
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostsDto> uploadImage(
			@RequestParam("image")MultipartFile image,
			@PathVariable Integer postId) throws IOException{
		String fileName = this.fileService.uploadImage(path, image);
		PostsDto postDto=this.postService.getPostById(postId);
		postDto.setImageName(fileName);
		PostsDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostsDto>(updatePost, HttpStatus.OK);
	}
	
	//SERVE IMAGE:
	@GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
	        @PathVariable("imageName") String imageName,
	        HttpServletResponse response
	) throws IOException {
	    InputStream resource = this.fileService.getResource(path, imageName);
	    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
	    StreamUtils.copy(resource, response.getOutputStream());
	}
}
