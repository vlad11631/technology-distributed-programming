package com.controllers;

import com.db.ConnectionJdbc;
import com.models.TasksList;
import com.models.UsersList;
import com.models.Task;
import com.models.User;
import java.sql.SQLException;

public class Storage implements java.io.Serializable {
    
    private static Storage instance;
    private ConnectionJdbc connection;
    
    private Storage() throws ClassNotFoundException {
        connection = new ConnectionJdbc();
    }
    
    public static Storage getInstance() throws ClassNotFoundException {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    /*public User getUser(long id) {
        return usersList.getUserById(id);
    }*/
    public void addUser(User user) throws SQLException {
        connection.insertUser(user);
    }
    
    public void editUser(int id, User user) throws SQLException {
        connection.updateUser(id, user);
    }
    
    public void removeUser(int id) throws SQLException {
        for (Task task : getTasksList().getTasks()) {
            if (task.getUserId() == id) {
                task.setUserId(0);
                editTask(id, task);
            }
        }
        connection.deleteUser(id);
    }
    
    public UsersList getUsersList() throws SQLException {
        return connection.selectUsers();
    }

    /*public Task getTask(long id) {
        return tasksList.getTaskById(id);
    }*/
    
    public void addTask(Task task) throws SQLException {
        connection.insertTask(task);
    }
    
    public void editTask(int id, Task task) throws SQLException {
        connection.updateTask(id, task);
    }
    
    public void removeTask(int id) throws SQLException {
        connection.deleteTask(id);
    }
    
    public TasksList getTasksList() throws SQLException {
        return connection.selectTasks();
    }
}
