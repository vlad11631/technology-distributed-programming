package com.controllers;

import com.models.TasksList;
import com.models.UsersList;
import com.models.Task;
import com.models.User;

public class ClientStorage implements java.io.Serializable {

    private static ClientStorage instance;
    private UsersList usersList;
    private TasksList tasksList;

    public ClientStorage() {
        usersList = new UsersList();
        tasksList = new TasksList();
    }

    public static ClientStorage getInstance() {
        if (instance == null) {
            instance = new ClientStorage();
        }
        return instance;
    }

    public User getUser(long id) {
        return usersList.getUserById(id);
    }

    public void addUser(User user) {
        usersList.getUsers().add(user);
    }

    public void editUser(long id, User user) {
        for (int i = 0; i < usersList.getUsers().size(); i++) {
            if (usersList.getUsers().get(i).getId() == id) {
                usersList.getUsers().set(i, user);
                return;
            }
        }
        throw new IllegalArgumentException();
    }

    public void removeUser(long id) {
        for (Task t : tasksList.getTasks()) {
            if (t.getUserId() == id) {
                t.setUserId(0);
            }
        }

        for (User u : usersList.getUsers()) {
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

    public void setUsersList(UsersList usersList) {
        this.usersList = usersList;
    }

    public Task getTask(long id) {
        return tasksList.getTaskById(id);
    }

    public void addTask(Task task) {
        tasksList.getTasks().add(task);
    }

    public void editTask(long id, Task task) {
        for (int i = 0; i < tasksList.getTasks().size(); i++) {
            if (tasksList.getTasks().get(i).getId() == id) {
                tasksList.getTasks().set(i, task);
                return;
            }
        }
        throw new IllegalArgumentException();
    }

    public void removeTask(long id) {
        for (Task t : tasksList.getTasks()) {
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

    public void setTasksList(TasksList tasksList) {
        this.tasksList = tasksList;
    }
}
