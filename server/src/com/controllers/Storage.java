package com.controllers;

import java.util.Date;
import com.models.TasksList;
import com.models.UsersList;
import com.models.Task;
import com.models.User;


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
        usersList.getUsers().add(new User(100, "Klyuev Vlad", "Dev"));
        usersList.getUsers().add(new User(101, "Platonova Anna", "TA"));
        tasksList.getTasks().add(new Task(102, "Make Lab2", "Make good lab2", new Date(117, 9, 22), new Date(117, 10, 3), 100));
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
        usersList.getUsers().add(user);
    }
    
    public void editUser(long id, User user){
        for (int i = 0; i < usersList.getUsers().size(); i++) {
            if (usersList.getUsers().get(i).getId() == id) {
                usersList.getUsers().set(i, user);
                return;
            }
        }
        throw new IllegalArgumentException();
    }
    
    public void removeUser(long id){
        for(Task t: tasksList.getTasks()) {
           if (t.getUserId() == id) {
               t.setUserId(0);
           }
        }
        
        for(User u: usersList.getUsers()) {
           if (u.getId() == id) {
               usersList.getUsers().remove(u);
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
        tasksList.getTasks().add(task);
    }
    
    public void editTask(long id, Task task){
        for (int i = 0; i < tasksList.getTasks().size(); i++) {
            if (tasksList.getTasks().get(i).getId() == id) {
                tasksList.getTasks().set(i, task);
                return;
            }
        }
        throw new IllegalArgumentException();
    }
    
    public void removeTask(long id){
        for(Task t: tasksList.getTasks()) {
           if (t.getId() == id) {
               tasksList.getTasks().remove(t);
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
