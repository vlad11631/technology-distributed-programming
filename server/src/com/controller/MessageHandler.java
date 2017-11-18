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
            case CREATE:
                response = create(message);
                break;
            case START_EDIT:
                response = startEdit(clientIndex, message);
                break;
            case STOP_EDIT:
                response = stopEdit(clientIndex, message);
                break;
            case EDIT:
                response = edit(clientIndex, message);
                break;
            case DELETE:
                response = delete(clientIndex, message);
                break;
            case FINISH_SESSION:
                //response = finishSession(clientIndex, message);
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
    
    private Message create(Message message) {
        if (message.getTypeObject() == Message.TypeObject.USER){
            User user = (User)message.getData();
            storage.addUser(user);
            clients.updateClients(new Message(Message.TypeMessage.CREATE, Message.TypeObject.USER, user));
            return new Message(Message.TypeMessage.OK, Message.TypeObject.USER, null);            
        } else if (message.getTypeObject() == Message.TypeObject.TASK){
            Task task = (Task)message.getData();
            storage.addTask(task);
            clients.updateClients(new Message(Message.TypeMessage.CREATE, Message.TypeObject.TASK, task));
            return new Message(Message.TypeMessage.OK, Message.TypeObject.TASK, null);           
        }
        return new Message(Message.TypeMessage.ERROR, message.getTypeObject(), null);
    }
    
    private Message startEdit(int clientIndex, Message message) {
        if (message.getTypeObject() == Message.TypeObject.USER){
            User user = (User)message.getData();
            try {
                clients.startEditUser(clientIndex, user.getId());
                return new Message(Message.TypeMessage.START_EDIT, Message.TypeObject.USER, user);
            } catch (IllegalArgumentException e) {
                return new Message(Message.TypeMessage.ERROR, Message.TypeObject.USER, "В данный момент редактирование невозможно!"); 
            }            
        } else if (message.getTypeObject() == Message.TypeObject.TASK){
            Task task = (Task)message.getData();
            try {
                clients.startEditTask(clientIndex, task.getId());
                return new Message(Message.TypeMessage.START_EDIT, Message.TypeObject.TASK, task);
            } catch (IllegalArgumentException e) {
                return new Message(Message.TypeMessage.ERROR, Message.TypeObject.TASK, "В данный момент редактирование невозможно!"); 
            }              
        }
        return new Message(Message.TypeMessage.ERROR, message.getTypeObject(), null);
    }
    
    private Message stopEdit(int clientIndex, Message message) {
        if (message.getTypeObject() == Message.TypeObject.USER){
            User user = (User)message.getData();
            clients.stopEditUser(clientIndex);
            return new Message(Message.TypeMessage.OK, Message.TypeObject.USER, null);            
        } else if (message.getTypeObject() == Message.TypeObject.TASK){
            Task task = (Task)message.getData();
            clients.stopEditTask(clientIndex);
            return new Message(Message.TypeMessage.OK, Message.TypeObject.TASK, null);           
        }
        return new Message(Message.TypeMessage.ERROR, message.getTypeObject(), null);
    }
    
    private Message edit(int clientIndex, Message message) {
        if (message.getTypeObject() == Message.TypeObject.USER){
            User user = (User)message.getData();
            storage.editUser(user.getId(), user);
            clients.stopEditUser(clientIndex);
            clients.updateClients(new Message(Message.TypeMessage.EDIT, Message.TypeObject.USER, user));
            return new Message(Message.TypeMessage.OK, Message.TypeObject.USER, null);            
        } else if (message.getTypeObject() == Message.TypeObject.TASK){
            Task task = (Task)message.getData();
            storage.editTask(task.getId(), task);
            clients.stopEditTask(clientIndex);
            clients.updateClients(new Message(Message.TypeMessage.EDIT, Message.TypeObject.TASK, task));
            return new Message(Message.TypeMessage.OK, Message.TypeObject.TASK, null);           
        }
        return new Message(Message.TypeMessage.ERROR, message.getTypeObject(), null);
    }
    
    private Message delete(int clientIndex, Message message) {
        if (message.getTypeObject() == Message.TypeObject.USER){
            User user = (User)message.getData();
            try{
                clients.startEditUser(clientIndex, user.getId());
                storage.removeUser(user.getId());
                clients.stopEditUser(clientIndex);
                clients.updateClients(new Message(Message.TypeMessage.DELETE, Message.TypeObject.USER, user));
                return new Message(Message.TypeMessage.OK, Message.TypeObject.USER, null); 
            } catch(IllegalArgumentException e){
               return new Message(Message.TypeMessage.ERROR, Message.TypeObject.USER, "В данный момент удаление невозможно!");  
            }
        } else if (message.getTypeObject() == Message.TypeObject.TASK){
            Task task = (Task)message.getData();
            try{
                clients.startEditTask(clientIndex, task.getId());
                storage.removeTask(task.getId());
                clients.stopEditTask(clientIndex);
                clients.updateClients(new Message(Message.TypeMessage.DELETE, Message.TypeObject.TASK, task));
                return new Message(Message.TypeMessage.OK, Message.TypeObject.TASK, null); 
            } catch(IllegalArgumentException e){
               return new Message(Message.TypeMessage.ERROR, Message.TypeObject.TASK, "В данный момент удаление невозможно!");  
            }
        }
        return new Message(Message.TypeMessage.ERROR, message.getTypeObject(), null);
    }
    
    private Message finishSession(int clientIndex, Message message) {
        clients.stopEditUser(clientIndex);
        clients.stopEditTask(clientIndex);
        return new Message(Message.TypeMessage.OK, null, null);
    }
}
