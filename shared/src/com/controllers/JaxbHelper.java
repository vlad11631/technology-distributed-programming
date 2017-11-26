package com.controllers;

import com.models.Message;
import com.models.Task;
import com.models.TasksList;
import com.models.User;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class JaxbHelper {

    // сохраняем объект в XML файл
    public static void fromMessageToXml(Message message, ObjectOutputStream outStream) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Message.class);
        Marshaller marshaller = context.createMarshaller();
        // устанавливаем флаг для читабельного вывода XML в JAXB
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(message, outStream);

    }

    // восстанавливаем объект из XML файла
    public static Message fromXmlToMessage(ObjectInputStream inStream) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Message.class);
        Unmarshaller un = jaxbContext.createUnmarshaller();
        return (Message) un.unmarshal(inStream);

    }
    
    

    // сохраняем объект в XML файл
    public static Document fromMessageToXml(Message message) throws JAXBException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        
        JAXBContext context = JAXBContext.newInstance(Message.class);
        Marshaller marshaller = context.createMarshaller();
        // устанавливаем флаг для читабельного вывода XML в JAXB
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(message, doc);
        return doc;
    }

    // восстанавливаем объект из XML файла
    public static Message fromXmlToMessage(Document document) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Message.class);
        Unmarshaller un = jaxbContext.createUnmarshaller();
        return (Message) un.unmarshal(document);

    }
    
    public static void main(String[] args) throws ParserConfigurationException, JAXBException{
        User user = new User(123, "Name", "Post");
        Message message = new Message(Message.TypeMessage.OK, Message.TypeObject.USER, user);
        Document doc = fromMessageToXml(message);
        
        Message message2 = fromXmlToMessage(doc);
        System.out.println(message2);
        
    }
}
