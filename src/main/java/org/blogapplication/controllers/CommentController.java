package org.blogapplication.controllers;


import org.blogapplication.entities.Comment;
import org.blogapplication.exceptions.ApiResponse;
import org.blogapplication.payloads.CommentDto;
import org.blogapplication.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //create comment
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment, @PathVariable Integer postId){
        CommentDto createComment = commentService.createComment(comment, postId);
        return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);
    }

    //delete comment
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully",true),HttpStatus.OK);
    }

}
