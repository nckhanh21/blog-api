package com.programming.techie.springngblog.controller;

import com.programming.techie.springngblog.dto.LikedPostDto;
import com.programming.techie.springngblog.service.LikedPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likedPost/")
public class LikedPostController {
    @Autowired
    private LikedPostService likedPostService;
    @PostMapping("/create/{post_id}")
    public ResponseEntity createLikedPost(@PathVariable Long post_id, Long user_id) {
        likedPostService.createLikedPost(post_id, user_id);
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/count/{post_id}")
    public Long countNumLike(@PathVariable Long post_id, Long user_id) {
        return likedPostService.countNumLike(post_id, user_id);
    }
    @DeleteMapping("/delete/{post_id}")
    public ResponseEntity<LikedPostDto> deleteLikedPost(@PathVariable Long post_id, Long user_id) {
        likedPostService.deleteLikedPost(post_id, user_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
