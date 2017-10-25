package com.controller;

import java.util.Date;
import com.model.TasksList;
import com.model.UsersList;
import com.model.Task;
import com.model.User;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Storage implements java.io.Serializable {
    private static Storage instance;
    private UsersList usersList;
    private TasksList tasksList;

    private Storage() {
        usersList = new UsersList();
        tasksList = new TasksList();
        init();
    }
    
    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    private void init() {
        usersList.add(new User(100, "Klyuev Vlad", "Dev"));
        usersList.add(new User(101, "Platonova Anna", "TA"));
        tasksList.add(new Task(102, "Make Lab1", "Make good lab", new Date(17, 9, 22), new Date(17, 10, 3), 100));
    }

    public User getUser(int index) {
        return usersList.get(index);
    }
    
    public User getUserById(long id) {
        return usersList.getUserById(id);
    }

    public void addUser(User user) {
        user.setId(generationId());
        usersList.add(user);
    }

    public void editUser(int index, User user) {
        usersList.set(index, user);
    }
    
    public void editUserById(long id, User user) {
        for(int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getId() == id) {
                editUser(i, user);
                return;
            }
        }
    }

    public void removeUser(int index) {
        User user = usersList.get(index);
        usersList.remove(index);

        for (Task task : tasksList) {
            if (task.getUserId() == user.getId()) {
                task.setUserId(0);
            }
        }
    }

    public void removeUserById(long id) {
        for(int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getId() == id) {
                removeUser(i);
                return;
            }
        }
    }
    
    public UsersList getUsersList() {
        return usersList;
    }

    public Task getTask(int index) {
        return tasksList.get(index);
    }
    
    public Task getTaskById(long id) {
        return tasksList.getTaskById(id);
    }

    public void addTask(Task task) {
        task.setId(generationId());
        tasksList.add(task);
    }

    public void editTask(int index, Task task) {
        tasksList.set(index, task);
    }
    
    public void editTaskById(long id, Task task) {
        for(int i = 0; i < tasksList.size(); i++) {
            if (tasksList.get(i).getId() == id) {
                editTask(i, task);
                return;
            }
        }
    }

    public void removeTask(int index) {
        tasksList.remove(index);
    }
    
    public void removeTaskById(long id) {
        for(int i = 0; i < tasksList.size(); i++) {
            if (tasksList.get(i).getId() == id) {
                removeTask(i);
                return;
            }
        }
    }

    public TasksList getTasksList() {
        return tasksList;
    }

    private long generationId() {
        Date d = new Date();
        return d.getTime();
    }

    public static void saveStorage(String fileName) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
        out.writeObject(instance);
        out.close();
    }

    public static void loadStorage(String fileName) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
        instance = (Storage) in.readObject();
        in.close();
    }
}
