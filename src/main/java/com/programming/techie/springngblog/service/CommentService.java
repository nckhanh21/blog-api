package com.programming.techie.springngblog.service;

import com.programming.techie.springngblog.dto.CommentDto;
import com.programming.techie.springngblog.dto.PostDto;
import com.programming.techie.springngblog.dto.PostDtoOutput;
import com.programming.techie.springngblog.exception.CommentNotFoundException;
import com.programming.techie.springngblog.exception.PostNotFoundException;
import com.programming.techie.springngblog.model.Comment;
import com.programming.techie.springngblog.model.Post;
import com.programming.techie.springngblog.repository.CommentRepository;
import com.programming.techie.springngblog.repository.PostRepository;
import com.programming.techie.springngblog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CommentService {

    @Autowired
    private AuthService authService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public void createComment(CommentDto commentDto) {
        commentDto.setNumLike(0L);
        Comment comment = mapFromDtoToComment(commentDto);
        commentRepository.save(comment);
    }
    @Transactional
    public List<CommentDto> showAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(this::mapFromCommentToDto).collect(toList());
    }
    @Transactional
    public CommentDto readSingleComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
        return mapFromCommentToDto(comment);
    }
    @Transactional
    public List<CommentDto> getAllCommentsByPost(Long id) {
        List<Comment> comments = commentRepository.findAllByPost(postRepository.getOne(id));
        return comments.stream().map(this::mapFromCommentToDto).collect(toList());
    }

    @Transactional
    public void updateComment(Long id, CommentDto commentDto) {
        Comment oldComment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException("For id " +id));
        Comment comment = mapFromDtoToComment(commentDto, oldComment);
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
    //------------------------------------
    @Transactional
    public List<CommentDto> getAllComments(Long post_id) {
        List<Comment> comments = (List<Comment>) commentRepository.findByPostId(post_id);
        return comments.stream().map(this::mapFromCommentToDto).collect(toList());
    }
    @Transactional
    public void updateNumLikedComment(Long id, CommentDto commentDto, Long change) {
        Comment oldComment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException("For id " +id));
        Comment comment = mapFromDtoToNumLikedComment(commentDto, oldComment, change);
        commentRepository.save(comment);
    }

    // ------------------------------------
    private Comment mapFromDtoToComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setNumLike(commentDto.getNumLike());
        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        comment.setCreatedOn(Instant.now());
        comment.setUsername(loggedInUser.getUsername());
        comment.setPost(postRepository.getOne(commentDto.getPost_id()));
        return comment;
    }
    private CommentDto mapFromCommentToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setNumLike(comment.getNumLike());
        commentDto.setUsername(comment.getUsername());
        commentDto.setPost_id(comment.getPost().getId());
        return commentDto;
    }
    private Comment mapFromDtoToComment(CommentDto commentDto, Comment comment) {
        comment.setContent(commentDto.getContent());
        comment.setNumLike(commentDto.getNumLike());
        comment.setUpdatedOn(Instant.now());
        comment.setPost(postRepository.getOne(commentDto.getPost_id()));
        return comment;
    }
    private Comment mapFromDtoToNumLikedComment(CommentDto commentDto, Comment comment, Long change) {
        comment.setContent(commentDto.getContent());
        comment.setNumLike(commentDto.getNumLike()+change);
        comment.setPost(postRepository.getOne(commentDto.getPost_id()));
        return comment;
    }

}
