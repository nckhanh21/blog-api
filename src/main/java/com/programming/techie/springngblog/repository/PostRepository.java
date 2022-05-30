package com.programming.techie.springngblog.repository;

import com.programming.techie.springngblog.dto.PostDto;
import com.programming.techie.springngblog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    //------------------------------------------------
    Iterable<Post> findByCategoryId(Long category_id);
    @Query("SELECT m FROM Post m WHERE m.title LIKE %:string%")
    List<Post> findByTitleOrContentContaining(String string);
    List<Post> findAllByUsername(String username);
    //------------------------------------------------
}
