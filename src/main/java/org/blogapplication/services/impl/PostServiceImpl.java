package org.blogapplication.services.impl;

import org.blogapplication.entities.Category;
import org.blogapplication.entities.Post;
import org.blogapplication.entities.User;
import org.blogapplication.exceptions.ResourceNotFoundException;
import org.blogapplication.payloads.PostDto;
import org.blogapplication.payloads.PostResponse;
import org.blogapplication.repositories.CategoryRepository;
import org.blogapplication.repositories.PostRepository;
import org.blogapplication.repositories.UserRepository;
import org.blogapplication.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));
        Post post = modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPost = postRepository.save(post);
        return modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Id",postId));
        postRepository.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy) {

        Pageable p= PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));

        Page<Post> pagePost = postRepository.findAll(p);
        List<Post> allPosts = pagePost.getContent();

        List<PostDto> postDtoList = allPosts.stream().map(post -> modelMapper.map(post, PostDto.class)).toList();

        PostResponse postResponse=new PostResponse();

        postResponse.setContent(postDtoList);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPage(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Id",postId));
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category cat = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Id",categoryId));
        List<Post> posts = postRepository.findByCategory(cat);
        List<PostDto> postDto = posts.stream().map(post -> modelMapper.map(post, PostDto.class)).toList();
        return postDto;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
        List<Post> posts = postRepository.findByUser(user);

        List<PostDto> postDtos = posts.stream().map(post -> modelMapper.map(post, PostDto.class)).toList();
        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = postRepository.findByTitleContaining(keyword);
        List<PostDto>postDtos = posts.stream().map((post)->modelMapper.map(post, PostDto.class)).toList();
        return postDtos;
    }
}
