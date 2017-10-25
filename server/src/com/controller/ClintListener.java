package com.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
    }
    
    public void run() {
        MessageHandler messageHandler = MessageHandler.getInstance();
        while (true) {
            try {
                Message message = read();
                if (message != null) {
                    Message response = messageHandler.handleMessage(clientIndex, message);
                    write(response);
                }
            } catch (IOException e) {
                System.out.println("Клиент " + clientIndex + ": Ошибка соединения. Клиент отключён");
                break;
            }
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Клиент " + clientIndex + ": Принудительно отключён клиент");
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
                message = (Message) object;
                System.out.println("Принято сообщение " + message);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Получено сообщение неизвестного формата");

        }
        return message;
    }

    //МЕТОД отправки сообщений на клиент
    public void write(Message message) throws IOException {
        outStream.writeObject(message);
        outStream.flush();
        System.out.println("Отправлено сообщение " + message);
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
