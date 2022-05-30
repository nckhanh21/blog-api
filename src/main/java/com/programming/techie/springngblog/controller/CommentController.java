package com.programming.techie.springngblog.controller;

import com.programming.techie.springngblog.dto.CommentDto;
import com.programming.techie.springngblog.dto.PostDto;
import com.programming.techie.springngblog.dto.PostDtoOutput;
import com.programming.techie.springngblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/comments/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity createComment(@RequestBody CommentDto commentDto) {
        commentService.createComment(commentDto);
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<CommentDto> getSinglePost(@PathVariable @RequestBody Long id) {
        return new ResponseEntity<>(commentService.readSingleComment(id), HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<CommentDto>> showAllComments() {
        return new ResponseEntity<>(commentService.showAllComments(), HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<CommentDto>> getAllCommentsByPost(@PathVariable Long id) {
        return new ResponseEntity<>(commentService.getAllCommentsByPost(id), HttpStatus.OK);
    }


    @PutMapping("/put/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long id, @RequestBody CommentDto commentDto ) {
        commentService.updateComment(id, commentDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CommentDto> deleteComment(@PathVariable @RequestBody Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //---------------------------------------
    @GetMapping("/get/comment/{post_id}")
    public ResponseEntity<List<CommentDto>> getAllComments(@PathVariable @RequestBody Long post_id) {
        return new ResponseEntity<>(commentService.getAllComments(post_id), HttpStatus.OK);
    }
    //---------------------------------------
}
