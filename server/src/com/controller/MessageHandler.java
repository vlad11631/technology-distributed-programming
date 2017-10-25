package com.controller;

import com.models.Message;
import com.models.Task;
import com.models.User;

public class MessageHandler {
    
    private static MessageHandler instance;
    
    private ClientsList clients;
    private Storage storage;
    
    private MessageHandler() { 
        clients = ClientsList.getInstance();
        storage = Storage.getInstance();
    }
    
    public static MessageHandler getInstance() {
        if (instance == null) {
            instance = new MessageHandler();
        }
        return instance;
    }
    
 
    public Message handleMessage(int clientIndex, Message message) {
        Message response = null;
        switch (message.getTypeMessage()) {
            case LOAD_OBJECTS:
                response = loadObjects(message);
                break;
            case LOAD_OBJECT:
                response = loadObject(message);
                break;
            case CREATE_OBJECT:
                response = createObject(message);
                break;
            case START_EDIT_OBJECT:
                response = startEditObject(clientIndex, message);
                break;
            case STOP_EDIT_OBJECT:
                response = stopEditObject(clientIndex, message);
                break;
            case DELETE_OBJECT:
                response = deleteObject(message);
                break;
            case FINISH_SESSION:
                response = finishSession(message);
                break;
        }
        return response;
    }
    
    private Message loadObjects(Message message) {
        if (message.getTypeObject() == Message.TypeObject.USER){
            return new Message(Message.TypeMessage.LOAD_OBJECTS, Message.TypeObject.USER, storage.getUsersList());            
        } else if (message.getTypeObject() == Message.TypeObject.TASK){
            return new Message(Message.TypeMessage.LOAD_OBJECTS, Message.TypeObject.TASK, storage.getTasksList());            
        }
        return new Message(Message.TypeMessage.ERROR, message.getTypeObject(), null);
    } 
    
    private Message loadObject(Message message) {
        if (message.getTypeObject() == Message.TypeObject.USER){
            User user = (User)message.getData();
            User newUser = storage.getUser(user.getId());
            return new Message(Message.TypeMessage.LOAD_OBJECT, Message.TypeObject.USER, newUser);            
        } else if (message.getTypeObject() == Message.TypeObject.TASK){
            Task task = (Task)message.getData();
            Task newTask = storage.getTask(task.getId());
            return new Message(Message.TypeMessage.LOAD_OBJECT, Message.TypeObject.TASK, newTask);            
        }
        return new Message(Message.TypeMessage.ERROR, message.getTypeObject(), null);
    } 
    
    private Message createObject(Message message) {
        if (message.getTypeObject() == Message.TypeObject.USER){
            User user = (User)message.getData();
            storage.addUser(user);
            clients.updateClients(new Message(Message.TypeMessage.CREATE_OBJECT, Message.TypeObject.USER, user));
            return new Message(Message.TypeMessage.OK, Message.TypeObject.USER, null);            
        } else if (message.getTypeObject() == Message.TypeObject.TASK){
            Task task = (Task)message.getData();
            storage.addTask(task);
            clients.updateClients(new Message(Message.TypeMessage.CREATE_OBJECT, Message.TypeObject.TASK, task));
            return new Message(Message.TypeMessage.OK, Message.TypeObject.TASK, null);           
        }
        return new Message(Message.TypeMessage.ERROR, message.getTypeObject(), null);
    }
    
    private Message startEditObject(int clientIndex, Message message) {
        if (message.getTypeObject() == Message.TypeObject.USER){
            User user = (User)message.getData();
            try {
                clients.startEditUser(clientIndex, user);
                return new Message(Message.TypeMessage.OK, Message.TypeObject.USER, null);
            } catch (IllegalArgumentException e) {
                return new Message(Message.TypeMessage.ERROR, Message.TypeObject.USER, null); 
            }            
        } else if (message.getTypeObject() == Message.TypeObject.TASK){
            Task task = (Task)message.getData();
            try {
                clients.startEditTask(clientIndex, task);
                return new Message(Message.TypeMessage.OK, Message.TypeObject.TASK, null);
            } catch (IllegalArgumentException e) {
                return new Message(Message.TypeMessage.ERROR, Message.TypeObject.TASK, null); 
            }              
        }
        return new Message(Message.TypeMessage.ERROR, message.getTypeObject(), null);
    }
    
    private Message stopEditObject(int clientIndex, Message message) {
        if (message.getTypeObject() == Message.TypeObject.USER){
            User user = (User)message.getData();
            storage.editUser(user.getId(), user);
            clients.stopEditUser(clientIndex, user);
            clients.updateClients(new Message(Message.TypeMessage.STOP_EDIT_OBJECT, Message.TypeObject.USER, user));
            return new Message(Message.TypeMessage.OK, Message.TypeObject.USER, null);            
        } else if (message.getTypeObject() == Message.TypeObject.TASK){
            Task task = (Task)message.getData();
            storage.editTask(task.getId(), task);
            clients.stopEditTask(clientIndex, task);
            clients.updateClients(new Message(Message.TypeMessage.STOP_EDIT_OBJECT, Message.TypeObject.TASK, task));
            return new Message(Message.TypeMessage.OK, Message.TypeObject.TASK, null);           
        }
        return new Message(Message.TypeMessage.ERROR, message.getTypeObject(), null);
    }
    
    private Message deleteObject(Message message) {
        if (message.getTypeObject() == Message.TypeObject.USER){
            User user = (User)message.getData();
            storage.removeUser(user.getId());
            clients.updateClients(new Message(Message.TypeMessage.DELETE_OBJECT, Message.TypeObject.USER, user));
            return new Message(Message.TypeMessage.OK, Message.TypeObject.USER, null);            
        } else if (message.getTypeObject() == Message.TypeObject.TASK){
            Task task = (Task)message.getData();
            storage.removeTask(task.getId());
            clients.updateClients(new Message(Message.TypeMessage.DELETE_OBJECT, Message.TypeObject.TASK, task));
            return new Message(Message.TypeMessage.OK, Message.TypeObject.TASK, null);           
        }
        return new Message(Message.TypeMessage.ERROR, message.getTypeObject(), null);
    }
    
    private Message finishSession(Message message) {
        return new Message(Message.TypeMessage.ERROR, message.getTypeObject(), null);
    }
}
