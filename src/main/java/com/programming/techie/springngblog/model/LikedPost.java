package com.programming.techie.springngblog.model;

import javax.persistence.*;

@Entity
@Table
public class LikedPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column
    private Long post_id;
    @Column
    private Long user_id;

    public Long getPost_id() { return post_id; }

    public void setPost_id(Long post_id) {  this.post_id = post_id; }

    public Long getUser_id() { return user_id; }

    public void setUser_id(Long user_id) { this.user_id = user_id; }
}
