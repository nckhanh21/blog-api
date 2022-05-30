package com.programming.techie.springngblog.repository;

import com.programming.techie.springngblog.model.LikedPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface LikedPostRepository extends JpaRepository<LikedPost, Long> {
    @Query("select count(l) from LikedPost l where l.post_id = :post_id and l.user_id = :user_id")
    Long countByPostId(Long post_id, Long user_id);

    @Modifying
    @Query("delete from LikedPost where post_id = :post_id and user_id = :user_id")
    void deleteByPostIdAndUserId(Long post_id, Long user_id);

}
