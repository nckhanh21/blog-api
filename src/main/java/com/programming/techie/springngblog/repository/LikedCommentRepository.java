package com.programming.techie.springngblog.repository;

import com.programming.techie.springngblog.model.LikedComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface LikedCommentRepository extends JpaRepository<LikedComment, Long> {
    @Query("select count(l) from LikedComment l where l.comment_id = :comment_id and l.user_id = :user_id")
    Long countByCommentId(Long comment_id, Long user_id);

    @Modifying
    @Query("delete from LikedComment where comment_id = :comment_id and user_id = :user_id")
    void deleteByCommentIdAndUserId(Long comment_id, Long user_id);

}
