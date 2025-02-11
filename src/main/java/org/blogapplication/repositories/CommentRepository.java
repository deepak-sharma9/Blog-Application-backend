package org.blogapplication.repositories;

import org.blogapplication.entities.Comment;
import org.blogapplication.payloads.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
}
