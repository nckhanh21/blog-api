package com.programming.techie.springngblog.controller;

import com.programming.techie.springngblog.dto.PostDto;
import com.programming.techie.springngblog.dto.PostDtoOutput;
import com.programming.techie.springngblog.dto.SearchRequest;
import com.programming.techie.springngblog.security.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/")
public class PostController {

    @Autowired
    private PostService postService;
    @PostMapping
    public ResponseEntity createPost(@RequestBody PostDto postDto) {
        postService.createPost(postDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> showAllPosts() {
        return new ResponseEntity<>(postService.showAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/all/profile")
    public ResponseEntity<List<PostDto>> showAllPostsByUser() {
        return new ResponseEntity<>(postService.showPostsByUser(), HttpStatus.OK);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<PostDtoOutput> getSinglePost(@PathVariable @RequestBody Long id) {
        return new ResponseEntity<>(postService.readSinglePost(id), HttpStatus.OK);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @RequestBody PostDto postDto ) {
        postService.updatePost(id, postDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<PostDto> deletePost(@PathVariable @RequestBody Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
//------------------------------------------------
    @GetMapping("/get/category/{category_id}")
    public ResponseEntity<List<PostDto>> getAllPosts(@PathVariable @RequestBody Long category_id) {
        return new ResponseEntity<>(postService.getAllPosts(category_id), HttpStatus.OK);
    }
    @PostMapping("/search")
    public ResponseEntity<List<PostDto>> searchAllPosts(@RequestBody SearchRequest searchContent) {
        return new ResponseEntity<>(postService.searchAllPosts(searchContent.getContent()), HttpStatus.OK);
    }
    //--------------------------------------------
}
