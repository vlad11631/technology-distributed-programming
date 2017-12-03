package com.controllers;

import com.models.Task;
import com.models.TasksList;
import com.models.User;
import com.models.UsersList;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlHelper {
   
    public static Document fromObjectToXml(Object object) throws JAXBException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        JAXBContext context = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = context.createMarshaller();
        // устанавливаем флаг для читабельного вывода XML в JAXB
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(object, doc);
        return doc;
    }

    public static Object fromXmlToObject(Document document, Class clazz) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller un = jaxbContext.createUnmarshaller();
        return un.unmarshal(document);
    }
    
    public static void fromObjectToXmlFile(Object object, String filePath) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(TasksList.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(object, new File(filePath));
    }
    
    public static Document fromXmlFileToXml(String filePath) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(filePath);
    }

    public static Document addUser(Document document, Document user) throws ParserConfigurationException {
        Element newUserElement = (Element) user.getElementsByTagName("user").item(0);
        Element newElement = (Element) document.importNode(newUserElement, Boolean.TRUE);

        Element usersElement = (Element) document.getElementsByTagName("users").item(0);
        usersElement.appendChild(newElement);

        return document;
    }

    public static Document setUser(Document document, Document user, long userId) throws ParserConfigurationException {
        Element newUserElement = (Element) user.getElementsByTagName("user").item(0);

        Element usersElement = (Element) document.getElementsByTagName("users").item(0);
        NodeList nodeList = document.getElementsByTagName("user");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element userElement = (Element) nodeList.item(i);
            Element idElement = (Element) userElement.getElementsByTagName("id").item(0);
            long id = Long.valueOf(idElement.getChildNodes().item(0).getNodeValue());

            if (id == userId) {
                Element newElement = (Element) document.importNode(newUserElement, Boolean.TRUE);
                usersElement.replaceChild(newElement, userElement);
                break;
            }
        }

        return document;
    }

    public static Document removeUser(Document document, long userId) throws ParserConfigurationException {
        Element usersElement = (Element) document.getElementsByTagName("users").item(0);
        NodeList nodeList = document.getElementsByTagName("user");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element userElement = (Element) nodeList.item(i);
            Element idElement = (Element) userElement.getElementsByTagName("id").item(0);
            long id = Long.valueOf(idElement.getChildNodes().item(0).getNodeValue());

            if (id == userId) {
                usersElement.removeChild(userElement);
                break;
            }
        }

        return document;
    }
    
    public static Document addTask(Document document, Document task) throws ParserConfigurationException {
        Element newTasklement = (Element) task.getElementsByTagName("task").item(0);
        Element newElement = (Element) document.importNode(newTasklement, Boolean.TRUE);

        Element tasksElement = (Element) document.getElementsByTagName("tasks").item(0);
        tasksElement.appendChild(newElement);

        return document;
    }

    public static Document setTask(Document document, Document task, long taskId) throws ParserConfigurationException {
        Element newTaskElement = (Element) task.getElementsByTagName("task").item(0);

        Element tasksElement = (Element) document.getElementsByTagName("tasks").item(0);
        NodeList nodeList = document.getElementsByTagName("task");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element taskElement = (Element) nodeList.item(i);
            Element idElement = (Element) taskElement.getElementsByTagName("id").item(0);
            long id = Long.valueOf(idElement.getChildNodes().item(0).getNodeValue());

            if (id == taskId) {
                Element newElement = (Element) document.importNode(newTaskElement, Boolean.TRUE);
                tasksElement.replaceChild(newElement, taskElement);
                break;
            }
        }

        return document;
    }

    public static Document removeTask(Document document, long taskId) throws ParserConfigurationException {
        Element tasksElement = (Element) document.getElementsByTagName("tasks").item(0);
        NodeList nodeList = document.getElementsByTagName("task");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element taskElement = (Element) nodeList.item(i);
            Element idElement = (Element) taskElement.getElementsByTagName("id").item(0);
            long id = Long.valueOf(idElement.getChildNodes().item(0).getNodeValue());

            if (id == taskId) {
                tasksElement.removeChild(taskElement);
                break;
            }
        }
        return document;
    }

    public static void main(String[] args) throws JAXBException {
        User user = new User(123, "Name", "Post");
        UsersList users = new UsersList();
        users.getUsers().add(user);
        
        Task task = new Task(124, "Name", "Dec", new Date(), new Date(), 123);
        TasksList tasks = new TasksList();
        tasks.getTasks().add(task);

        fromObjectToXmlFile(users, "users.xml");
        fromObjectToXmlFile(tasks, "tasks.xml");
    }
}
