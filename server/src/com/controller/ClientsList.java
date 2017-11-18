package com.controller;

import com.models.Message;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ClientsList {

    private static ClientsList instance;

    private LinkedList<ClintListener> clients = new LinkedList<>();
    private Map<Integer, Long> editingUserIds = new HashMap();
    private Map<Integer, Long> editingTaskIds = new HashMap();

    public static ClientsList getInstance() {
        if (instance == null) {
            instance = new ClientsList();
        }
        return instance;
    }

    public void addClient(Integer clientIndex, Socket soket) throws IOException {
        ClintListener client = new ClintListener(clientIndex, soket);
        clients.add(client);
        client.start();
    }

    public void updateClients(Message message) {
        for (ClintListener client : clients) {
            client.updateClient(message);
        }
    }
    
    //Наложение/снятиеблокировок
    public void startEditUser(int clientIndex, long userId) {
        for (Integer key : editingUserIds.keySet()) {
            long editingUserId = editingUserIds.get(key);
            if (editingUserId == userId) {
                throw new IllegalArgumentException();
            }
        }

        editingUserIds.put(clientIndex, userId);
    }

    public void stopEditUser(int clientIndex) {
        editingUserIds.remove(clientIndex);
    }

    public void startEditTask(int clientIndex, long taskId) {
        for (Integer key : editingTaskIds.keySet()) {
            long editingTaskId = editingTaskIds.get(key);
            if (editingTaskId == taskId) {
                throw new IllegalArgumentException();
            }
        }

        editingUserIds.put(clientIndex, taskId);
    }

    public void stopEditTask(int clientIndex) {
        editingUserIds.remove(clientIndex);
    }
}
