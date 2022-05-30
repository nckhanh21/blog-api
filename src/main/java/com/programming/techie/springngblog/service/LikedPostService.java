package com.programming.techie.springngblog.service;

import com.programming.techie.springngblog.dto.PostDto;
import com.programming.techie.springngblog.dto.PostDtoOutput;
import com.programming.techie.springngblog.model.LikedPost;
import com.programming.techie.springngblog.repository.LikedPostRepository;
import com.programming.techie.springngblog.repository.PostRepository;
import com.programming.techie.springngblog.repository.UserRepository;
import com.programming.techie.springngblog.security.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Service
public class LikedPostService {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LikedPostRepository likedPostRepository;
    @Autowired
    private PostService postService;
    @Transactional
    public void createLikedPost(Long post_id, Long user_id) {
        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        LikedPost likedPost = mapFromDtoToLikedPost(post_id, userRepository.findByUserName(loggedInUser.getUsername()).get().getId());
        likedPostRepository.save(likedPost);
    }
    @Transactional
    public Long countNumLike(Long post_id, Long user_id) {
        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        Long count = likedPostRepository.countByPostId(post_id, userRepository.findByUserName(loggedInUser.getUsername()).get().getId());
        if (Objects.equals(count, 0L)) {
            createLikedPost(post_id, user_id);
            PostDtoOutput postDto = postService.readSinglePost(post_id);
            postService.updateNumLikedPost(post_id, postDto, 1L);
        }
        if (Objects.equals(count, 1L)) {
            deleteLikedPost(post_id, user_id);
            PostDtoOutput postDto = postService.readSinglePost(post_id);
            postService.updateNumLikedPost(post_id, postDto, -1L);
        }
        return count;
    }
    @Transactional
    public void deleteLikedPost(Long post_id, Long user_id) {
        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        likedPostRepository.deleteByPostIdAndUserId(post_id, userRepository.findByUserName(loggedInUser.getUsername()).get().getId());
    }
    private LikedPost mapFromDtoToLikedPost(Long post_id, Long user_id) {
        LikedPost likedPost = new LikedPost();
        likedPost.setPost_id(post_id);
        likedPost.setUser_id(user_id);
        return likedPost;
    }
}
