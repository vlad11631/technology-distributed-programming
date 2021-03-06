package com.models;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "task")
public class Task implements java.io.Serializable {

    private int id;
    private String name;
    private String description;
    private Date createdDate;
    private Date endDate;
    private int userId;

    public Task() {
    }

    public Task(int id, String name, String description, Date createdDate, Date endDate, int userId) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append("{Task ")
                .append(id).append(", ")
                .append(name).append(", ")
                .append(description).append(", ")
                .append(createdDate).append(", ")
                .append(endDate).append(", ")
                .append(userId).append("}");
        return str.toString();
    }
}
