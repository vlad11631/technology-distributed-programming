package com.model;

public class User implements java.io.Serializable {
    
    private long id;
    private String name;
    private String post;

    public User() {
    }

    public User(long id, String name, String post) {
        this.id = id;
        this.name = name;
        this.post = post;
    }
    
    public User(User user) {
        id = user.id;
        name = user.name;
        post = user.post;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }
    public void setPost(String post) {
        this.post = post;
    }
}
