package org.blogapplication.services;

import org.blogapplication.payloads.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Integer postId);
    void deleteComment(Integer commentId);
}
