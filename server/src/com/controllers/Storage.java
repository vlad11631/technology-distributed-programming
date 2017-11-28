package com.controllers;

import java.util.Date;
import com.models.TasksList;
import com.models.UsersList;
import com.models.Task;
import com.models.User;
import java.io.IOException;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Storage implements java.io.Serializable {

    private static Storage instance;
    private Document users;
    private Document tasks;

    public Storage() {
        try {
            users = XmlHelper.fromXmlFileToXml("users.xml");
            tasks = XmlHelper.fromXmlFileToXml("tasks.xml");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    /*public User getUser(long id) {
        return usersList.getUserById(id);
    }*/
    
    public void addUser(User user) {
        try {
            user.setId(generationId());
            XmlHelper.addUser(users, XmlHelper.fromObjectToXml(user));
        } catch (ParserConfigurationException | JAXBException e) {
            e.printStackTrace();
        }
    }

    public void editUser(long id, User user) {
        try {
            XmlHelper.setUser(users, XmlHelper.fromObjectToXml(user), id);
        } catch (ParserConfigurationException | JAXBException e) {
            e.printStackTrace();
        }
    }

    public void removeUser(long id) {
        try {
            for (Task task : getTasksList().getTasks()) {
                if (task.getUserId() == id) {
                    task.setUserId(0);
                    editTask(id, task);
                }
            }

            XmlHelper.removeUser(users, id);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public UsersList getUsersList() {
        try {
            return (UsersList) XmlHelper.fromXmlToObject(users, UsersList.class);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*public Task getTask(long id) {
        return tasksList.getTaskById(id);
    }*/

    public void addTask(Task task) {
        try {
            task.setId(generationId());
            XmlHelper.addTask(tasks, XmlHelper.fromObjectToXml(task));
        } catch (ParserConfigurationException | JAXBException e) {
            e.printStackTrace();
        }
    }

    public void editTask(long id, Task task) {
        try {
            XmlHelper.setTask(tasks, XmlHelper.fromObjectToXml(task), id);
        } catch (ParserConfigurationException | JAXBException e) {
            e.printStackTrace();
        }
    }

    public void removeTask(long id) {
        try {
            XmlHelper.removeTask(tasks, id);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public TasksList getTasksList() {
        try {
            return (TasksList) XmlHelper.fromXmlToObject(tasks, TasksList.class);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    private long generationId() {
        Date d = new Date();
        return d.getTime();
    }
}
