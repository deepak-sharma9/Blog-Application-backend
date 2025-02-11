package org.blogapplication.services.impl;

import org.blogapplication.entities.Comment;
import org.blogapplication.entities.Post;
import org.blogapplication.exceptions.ResourceNotFoundException;
import org.blogapplication.payloads.CommentDto;
import org.blogapplication.repositories.CommentRepository;
import org.blogapplication.repositories.PostRepository;
import org.blogapplication.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {


    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","post id",postId));
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        return modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {

        Comment com = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","comment id",commentId));
        commentRepository.delete(com);
    }
}
