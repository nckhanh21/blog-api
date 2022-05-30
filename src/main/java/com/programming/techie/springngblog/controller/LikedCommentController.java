package com.programming.techie.springngblog.controller;

import com.programming.techie.springngblog.dto.LikedCommentDto;
import com.programming.techie.springngblog.service.LikedCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likedComment/")
public class LikedCommentController {
    @Autowired
    private LikedCommentService likedCommentService;
    @PostMapping("/create/{comment_id}")
    public ResponseEntity createLikedComment(@PathVariable Long comment_id, Long user_id) {
        likedCommentService.createLikedComment(comment_id, user_id);
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/count/{comment_id}")
    public Long countNumLike(@PathVariable Long comment_id, Long user_id) {
        return likedCommentService.countNumLike(comment_id, user_id);
    }
    @DeleteMapping("/delete/{comment_id}")
    public ResponseEntity<LikedCommentDto> deleteLikedComment(@PathVariable Long comment_id, Long user_id) {
        likedCommentService.deleteLikedComment(comment_id, user_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
