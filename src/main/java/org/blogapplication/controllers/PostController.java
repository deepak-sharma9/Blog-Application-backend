package org.blogapplication.controllers;


import jakarta.servlet.http.HttpServletResponse;
import org.blogapplication.config.AppConstants;
import org.blogapplication.entities.Post;
import org.blogapplication.exceptions.ApiResponse;
import org.blogapplication.payloads.PostDto;
import org.blogapplication.payloads.PostResponse;
import org.blogapplication.services.FileService;
import org.blogapplication.services.PostService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    private PostService postService;
    private FileService fileService;

    public PostController(PostService postService, FileService fileService) {
        this.postService = postService;
        this.fileService = fileService;
    }

    @Value("${project.image}")
    private String path;

    //create post
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId){
        PostDto createdPost = postService.createPost(postDto,userId,categoryId);
        return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
    }

    //get posts by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
     List<PostDto> postDtoList = postService.getPostsByUser(userId);
     return new ResponseEntity<List<PostDto>>(postDtoList,HttpStatus.OK);
    }

    //get posts by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
        List<PostDto> postDtoList = postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(postDtoList);
    }

    //get all posts
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy
    ){
        PostResponse postResponse = postService.getAllPost(pageNumber, pageSize,sortBy);
        return ResponseEntity.ok(postResponse);
    }

    //get single post
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Integer postId){
        PostDto postDto = postService.getPostById(postId);
        return ResponseEntity.ok(postDto);
    }

    //update post
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
        PostDto postDto1 = postService.updatePost(postDto,postId);
        return new ResponseEntity<PostDto>(postDto1,HttpStatus.OK);
    }

    //delete post
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
        postService.deletePost(postId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted successfully",true),HttpStatus.OK);
    }

    //search post
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyword){
        List<PostDto> result = postService.searchPosts(keyword);
        return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);
    }

    //post image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image")MultipartFile image,
                                                         @PathVariable Integer postId) throws IOException {
        String fileName = fileService.uploadImage(path, image);
        PostDto postDto = postService.getPostById(postId);
        postDto.setImageName(fileName);
        PostDto updatePost = postService.updatePost(postDto,postId);
        return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
    }

    //method to serve files
    @GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName,
                              HttpServletResponse response) throws IOException {
        InputStream resource = fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }




}
