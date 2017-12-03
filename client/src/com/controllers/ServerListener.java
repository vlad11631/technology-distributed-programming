package com.controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import com.models.Message;
import com.models.Task;
import com.models.User;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;

public class ServerListener extends Thread {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 5555;
    private static ServerListener instance;

    private final Socket socket;
    private final ObjectOutputStream outStream;
    private final ObjectInputStream inStream;

    private ServerListener(String host, int port) throws IOException {
        socket = new Socket(host, port);

        outStream = new ObjectOutputStream(socket.getOutputStream());
        outStream.flush();
        inStream = new ObjectInputStream(socket.getInputStream());
    }

    public static ServerListener getInstance() {
        try {
            if (instance == null) {
                instance = new ServerListener(HOST, PORT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instance;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = read();
                if (message != null) {
                    MessageHandler.getInstance().handleMessage(message);
                }
            } catch (Exception e) {
                System.out.println("Ошибка соединения с сервером");
                e.printStackTrace();
                break;
            }

            if (Thread.currentThread().isInterrupted()) {
                break;
            }
        }
        close();
    }

    private void close() {
        try {
            inStream.close();
            outStream.close();
            socket.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //МЕТОД чтения сообщений с сервера
    private Message read() throws IOException {
        Message message = null;
        try {
            Object object = inStream.readObject();
            if (object != null) {
                message = (Message) XmlHelper.fromXmlToObject((Document) object, Message.class);
                System.out.println("Принято сообщение " + message);
            }
        } catch (ClassNotFoundException | JAXBException e) {
            e.printStackTrace();
            throw new IOException();
        }
        return message;
    }

    //МЕТОД отправки сообщений на сервер
    private void write(Message message) throws IOException {
        try {
            outStream.writeObject(XmlHelper.fromObjectToXml(message));
            outStream.flush();
            System.out.println("Отправлено сообщение " + message);
        } catch (ParserConfigurationException | JAXBException e) {
            e.printStackTrace();
            throw new IOException();
        }
    }
    
    public void loadData() throws IOException {
        write(new Message(Message.TypeMessage.LOAD_OBJECTS, Message.TypeObject.USER, null));
        write(new Message(Message.TypeMessage.LOAD_OBJECTS, Message.TypeObject.TASK, null));
    }

    public void createUser(User user) throws IOException {
        write(new Message(Message.TypeMessage.CREATE, Message.TypeObject.USER, user));
    }

    public void createTask(Task task) throws IOException {
        write(new Message(Message.TypeMessage.CREATE, Message.TypeObject.TASK, task));
    }

    public void startEditUser(User user) throws IOException {
        write(new Message(Message.TypeMessage.START_EDIT, Message.TypeObject.USER, user));
    }

    public void startEditTask(Task task) throws IOException {
        write(new Message(Message.TypeMessage.START_EDIT, Message.TypeObject.TASK, task));
    }

    public void stopEditUser(User user) throws IOException {
        write(new Message(Message.TypeMessage.STOP_EDIT, Message.TypeObject.USER, user));
    }

    public void stopEditTask(Task task) throws IOException {
        write(new Message(Message.TypeMessage.STOP_EDIT, Message.TypeObject.TASK, task));
    }

    public void editUser(User user) throws IOException {
        write(new Message(Message.TypeMessage.EDIT, Message.TypeObject.USER, user));
    }

    public void editTask(Task task) throws IOException {
        write(new Message(Message.TypeMessage.EDIT, Message.TypeObject.TASK, task));
    }

    public void deleteUser(User user) throws IOException {
        write(new Message(Message.TypeMessage.DELETE, Message.TypeObject.USER, user));
    }

    public void deleteTask(Task task) throws IOException {
        write(new Message(Message.TypeMessage.DELETE, Message.TypeObject.TASK, task));
    }

    public void finishSession() {
        try {
            write(new Message(Message.TypeMessage.FINISH_SESSION, null, null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
