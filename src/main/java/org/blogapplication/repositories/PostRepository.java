package org.blogapplication.repositories;

import org.blogapplication.entities.Category;
import org.blogapplication.entities.Post;
import org.blogapplication.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String title);

}
