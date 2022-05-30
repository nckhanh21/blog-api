package com.programming.techie.springngblog.security;

import com.programming.techie.springngblog.dto.PostDto;
import com.programming.techie.springngblog.dto.PostDtoOutput;
import com.programming.techie.springngblog.exception.PostNotFoundException;
import com.programming.techie.springngblog.model.Post;
import com.programming.techie.springngblog.repository.CategoryRepository;
import com.programming.techie.springngblog.repository.PostRepository;
import com.programming.techie.springngblog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PostService {

    @Autowired
    private AuthService authService;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public List<PostDto> showAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }
    @Transactional
    public List<PostDto> showPostsByUser() {
        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        List<Post> posts = postRepository.findAllByUsername(loggedInUser.getUsername());
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }


    @Transactional
    public void createPost(PostDto postDto) {
        postDto.setNumLike(0L);
        Post post = mapFromDtoToPost(postDto);
        postRepository.save(post);
    }

    @Transactional
    public PostDtoOutput readSinglePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
        return mapFromPostToDtoShow(post);
    }

    //    1
    @Transactional
    public void updatePost(Long id, PostDto postDto) {
        Post oldPost = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " +id));
        Post post = mapFromDtoToPost(postDto, oldPost);
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
    @Transactional
    public List<PostDto> searchAllPosts(String string) {
        List<Post> posts = postRepository.findByTitleOrContentContaining(string);
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }

    //------------------------------------------------
    @Transactional
    public List<PostDto> getAllPosts(Long category_id) {
        List<Post> posts = (List<Post>) postRepository.findByCategoryId(category_id);
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }
    @Transactional
    public void updateNumLikedPost(Long id, PostDtoOutput postDto, Long change) {
        Post oldPost = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " +id));
        Post post = mapFromDtoToNumLikedPost(postDto, oldPost, change);
        postRepository.save(post);
    }
    //------------------------------------------------

    private Post mapFromDtoToPost(PostDto postDto, Post post) {
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setNumLike(postDto.getNumLike());
        post.setUpdatedOn(Instant.now());
        post.setDesciption(postDto.getDescription());
        post.setThumnail(postDto.getThumnail());
        post.setCategory(categoryRepository.getOne(postDto.getCategory_id()));
        return post;
    }

    private PostDto mapFromPostToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setNumLike(post.getNumLike());
        postDto.setUsername(post.getUsername());
        postDto.setDescription(post.getDesciption());
        postDto.setThumnail(post.getThumnail());
        postDto.setCategory_id(post.getCategory().getId());
        return postDto;
    }

    private Post mapFromDtoToPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setNumLike(postDto.getNumLike());
        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        post.setCreatedOn(Instant.now());
        post.setUsername(loggedInUser.getUsername());
        post.setUpdatedOn(Instant.now());
        post.setDesciption(postDto.getDescription());
        post.setThumnail(postDto.getThumnail());
        post.setCategory(categoryRepository.getOne(postDto.getCategory_id()));
        return post;
    }

    private PostDtoOutput mapFromPostToDtoShow(Post post) {
        PostDtoOutput postDto = new PostDtoOutput();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setNumLike(post.getNumLike());
        postDto.setUsername(post.getUsername());
        postDto.setDescription(post.getDesciption());
        postDto.setThumnail(post.getThumnail());
        postDto.setCategory_id(post.getCategory().getId());
        long minuteAgo = post.getCreatedOn().until(Instant.now(), ChronoUnit.MINUTES);
        postDto.setCreatedOn(minuteAgo);
        return postDto;
    }
    private Post mapFromDtoToNumLikedPost(PostDtoOutput postDto, Post post, Long change) {
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setNumLike(postDto.getNumLike()+change);
        post.setDesciption(postDto.getDescription());
        post.setThumnail(postDto.getThumnail());
        post.setCategory(categoryRepository.getOne(postDto.getCategory_id()));
        return post;
    }
}
