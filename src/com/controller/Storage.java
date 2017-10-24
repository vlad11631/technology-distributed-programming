package com.controller;

import java.util.Date;
import com.model.TasksList;
import com.model.UsersList;
import com.model.Task;
import com.model.User;


public class Storage implements java.io.Serializable{
    
    private static Storage instance;
    private UsersList usersList;
    private TasksList tasksList;
    
    public Storage(){
        usersList = new UsersList();
        tasksList = new TasksList();
        init();
    }
    
    private void init() {
        usersList.add(new User(100, "Klyuev Vlad", "Dev"));
        usersList.add(new User(101, "Platonova Anna", "TA"));
        tasksList.add(new Task(102, "Make Lab1", "Make good lab", new Date(17, 9, 22), new Date(17, 10, 3), 100));
    }
    
    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }
    
    public User getUser(long id){
        return usersList.getUserById(id);
    }
    
    public void addUser(User user){
        user.setId(generationId());
        usersList.add(user);
    }
    
    public void editUser(long id, User user){
        for(User u: usersList) {
           if (u.getId() == id) {
               u = user;
               return;
           } 
        }
        throw new IllegalArgumentException();
    }
    
    public void removeUser(long id){
        for(User u: usersList) {
           if (u.getId() == id) {
               usersList.remove(u);
               return;
           } 
        }
        throw new IllegalArgumentException();
    }

    public UsersList getUsersList() {
        return usersList;
    }  
    
    public Task getTask(long id){
        return tasksList.getTaskById(id);
    }
    
    public void addTask(Task task){
        task.setId(generationId());
        tasksList.add(task);
    }
    
    public void editTask(long id, Task task){
        for(Task t: tasksList) {
           if (t.getId() == id) {
               t = task;
               return;
           } 
        }
        throw new IllegalArgumentException();
    }
    
    public void removeTask(long id){
        for(Task t: tasksList) {
           if (t.getId() == id) {
               tasksList.remove(t);
               return;
           } 
        }
        throw new IllegalArgumentException();
    }

    public TasksList getTasksList() {
        return tasksList;
    }
    
    private long generationId(){
        Date d = new Date();
        return d.getTime();
    }
}
