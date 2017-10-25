package com;

import com.controller.ClientsList;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {
    
    private static ClientsList clients;


    public static void main(String args[]) {        
        try {
            clients = ClientsList.getInstance();
            
            int clientIndex = 0;
            ServerSocket serverSocket = new ServerSocket(5555);
            System.out.println("Сервер запущен");
            while(true) {
                try {
                    Socket socket = serverSocket.accept();
                    clients.addClient(clientIndex, socket);
                } catch (SocketTimeoutException e) {
                    System.out.println("Клиент " + clientIndex + ": Не удалось установить соединение");
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("Клиент " + clientIndex + ": Не удалось установить соединение");
                    e.printStackTrace();
                }
                clientIndex++;
            }
        } catch (IOException e) {
            System.out.println("Не удалось создать сокет");
            e.printStackTrace();
        }
    }
}
