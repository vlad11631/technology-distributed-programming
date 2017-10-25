package com.models;

import java.util.Date;

public class Task implements java.io.Serializable {
    
    private long id;
    private String name;
    private String description;
    private Date createdDate;
    private Date endDate; 
    private long userId;

    public Task() {
    }

    public Task(long id, String name, String description, Date createdDate, Date endDate, long userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
        this.endDate = endDate;
        this.userId = userId;
    }
    
    public Task(Task task) {
        id = task.id;
        name = task.name;
        description = task.description;
        createdDate = task.createdDate;
        endDate = task.endDate;
        userId = task.userId;
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

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
}
