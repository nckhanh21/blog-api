package com.programming.techie.springngblog.dto;

public class CommentDto {

    private Long id;
    private String content;
    private String username;
    private Long post_id;
    //  -----------------------------------------------------------------
    private Long numLike;

    public Long getNumLike() {  return numLike;    }

    public void setNumLike(Long numLike) {this.numLike = numLike;    }
    //  -----------------------------------------------------------------

    public Long getPost_id() { return post_id; }

    public void setPost_id(Long post_id) { this.post_id = post_id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }
}
