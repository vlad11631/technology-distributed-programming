package com.controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import com.models.Message;

public class ClintListener extends Thread {

    private final int clientIndex;
    private final Socket socket;
    private final ObjectOutputStream outStream;
    private final ObjectInputStream inStream;

    public ClintListener(int clientIndex, Socket socket) throws IOException {
        this.clientIndex = clientIndex;
        this.socket = socket;
        this.outStream = new ObjectOutputStream(socket.getOutputStream());
        this.outStream.flush();
        this.inStream = new ObjectInputStream(socket.getInputStream());
        System.out.println("Клиент " + clientIndex + ": Подключен");
    }

    public void run() {
        MessageHandler messageHandler = MessageHandler.getInstance();
        while (true) {
            try {
                Message message = read();
                if (message != null) {
                    Message response = messageHandler.handleMessage(clientIndex, message);
                    write(response);

                    if (message.getTypeMessage() == Message.TypeMessage.FINISH_SESSION) {
                        System.out.println("Клиент " + clientIndex + ": Отключен");
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println("Клиент " + clientIndex + ": Ошибка соединения. Отключен");
                e.printStackTrace();
                break;
            }
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Клиент " + clientIndex + ": Принудительно отключён");
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

            ClientsList clients = ClientsList.getInstance();
            clients.stopEditUser(clientIndex);
            clients.stopEditTask(clientIndex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //МЕТОД чтения сообщений с колиенка
    public Message read() throws IOException {
        Message message = null;
        try {
            Object object = inStream.readObject();
            if (object != null) {
                message = (Message) XmlHelper.fromXmlToObject((Document) object, Message.class);
                System.out.println("Клиент " + clientIndex + ": Принято сообщение " + message);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new IOException();
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new IOException();
        }
        return message;
    }

    //МЕТОД отправки сообщений на клиент
    public void write(Message message) throws IOException {
        try {
            outStream.writeObject(XmlHelper.fromObjectToXml(message));
            outStream.flush();
            System.out.println("Клиент " + clientIndex + ": Отправлено сообщение " + message);
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new IOException();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new IOException();
        }
    }

    public void updateClient(Message message) {
        try {
            write(message);
        } catch (IOException e) {
            System.out.println("Клиент " + clientIndex + ": Ошибка соединения");
            e.printStackTrace();
        }
    }
}
