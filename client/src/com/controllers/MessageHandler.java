package com.controllers;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import com.models.Message;
import com.models.Task;
import com.models.TasksList;
import com.models.User;
import com.models.UsersList;
import com.views.TaskDialog;
import com.views.UserDialog;
import javax.swing.JOptionPane;

public class MessageHandler {
    
    private static MessageHandler instance;
    private List<ActionListener> updateUsersListeners = new ArrayList<ActionListener>();
    private List<ActionListener> updateTasksListeners = new ArrayList<ActionListener>();
    
    private ClientStorage storage;
    
    
    private MessageHandler() { 
        storage = ClientStorage.getInstance();
    }
    
    public static MessageHandler getInstance() {
        if (instance == null) {
            instance = new MessageHandler();
        }
        return instance;
    }
    
    public void addUpdateUsersListener(ActionListener listener) {
        updateUsersListeners.add(listener);
    }
    
    public void addUpdateTasksListener(ActionListener listener) {
        updateTasksListeners.add(listener);
    }
    
    private void invokeUpdateUsers(Message message) {
        for (int i = 0; i < updateUsersListeners.size(); i++) {
            updateUsersListeners.get(i).actionPerformed(new ActionEvent(message, 0, "update"));
        }
    }
    
    private void invokeUpdateTasks(Message message) {
        for (int i = 0; i < updateTasksListeners.size(); i++) {
            updateTasksListeners.get(i).actionPerformed(new ActionEvent(message, 0, "update"));
        }
    }
    
    public void handleMessage(Message message) {
        switch (message.getTypeMessage()) {
            case OK:
                //nothing
                break;
            case ERROR:
                error(message);
                break;
            case LOAD_OBJECTS:
                loadObjects(message);
                break;
            case CREATE:
                create(message);
                break;
            case START_EDIT:
                startEdit(message);
                break;
            case EDIT:
                edit(message);
                break;
            case DELETE:
                delete(message);
                break;
        }
    }
    
    private void error(Message message) {
        String errorMessage = (String)message.getData();
        JOptionPane.showMessageDialog(new Frame(), errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    } 
    
    private void loadObjects(Message message) {
        if (message.getTypeObject() == Message.TypeObject.USER){
            UsersList usersList = (UsersList)message.getData();
            storage.setUsersList(usersList);
            invokeUpdateUsers(message);
        } else if (message.getTypeObject() == Message.TypeObject.TASK){
            TasksList tasksList = (TasksList)message.getData(); 
            storage.setTasksList(tasksList);
            invokeUpdateTasks(message);
        }
    } 
    
    private void create(Message message) {
        if (message.getTypeObject() == Message.TypeObject.USER){
            User user = (User)message.getData();
            storage.addUser(user);
            invokeUpdateUsers(message);        
        } else if (message.getTypeObject() == Message.TypeObject.TASK){
            Task task = (Task)message.getData();
            storage.addTask(task);
            invokeUpdateTasks(message);
        }
    }
    
    private void startEdit(Message message) {
        if (message.getTypeObject() == Message.TypeObject.USER){
            User user = (User)message.getData();
            UserDialog dialog = new UserDialog(new Frame(), true);
            dialog.showDialog(user);        
        } else if (message.getTypeObject() == Message.TypeObject.TASK){
            Task task = (Task)message.getData();
            TaskDialog dialog = new TaskDialog(new Frame(), true);
            dialog.showDialog(task);
        }
    }
            
    private void edit(Message message) {
        if (message.getTypeObject() == Message.TypeObject.USER){
            User user = (User)message.getData();
            storage.editUser(user.getId(), user);
            System.out.print(storage.getUsersList());
            invokeUpdateUsers(message);        
        } else if (message.getTypeObject() == Message.TypeObject.TASK){
            Task task = (Task)message.getData();
            storage.editTask(task.getId(), task);
            invokeUpdateTasks(message); 
        }
    }
    
    private void delete(Message message) {
        if (message.getTypeObject() == Message.TypeObject.USER){
            User user = (User)message.getData();
            storage.removeUser(user.getId());
            invokeUpdateUsers(message);        
        } else if (message.getTypeObject() == Message.TypeObject.TASK){
            Task task = (Task)message.getData();
            storage.removeTask(task.getId());
            invokeUpdateTasks(message);          
        }
    }
}
