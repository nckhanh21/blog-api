package com.programming.techie.springngblog.repository;

import com.programming.techie.springngblog.model.Comment;
import com.programming.techie.springngblog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Iterable<Comment> findByPostId(Long post_id);
    List<Comment> findAllByPost(Post post);
}
