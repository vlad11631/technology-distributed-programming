package com.controller;

import com.models.Message;
import com.models.Task;
import com.models.User;
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
    
    public void startEditUser(int clientIndex, User user) {
        for (Integer key : editingUserIds.keySet()) {
            long editingUserId = editingUserIds.get(key);
            if (editingUserId == user.getId()) {
                throw new IllegalArgumentException();
            }
        }

        editingUserIds.put(clientIndex, user.getId());
    }

    public void stopEditUser(int clientIndex, User user) {
        if (user.getId() != editingUserIds.get(clientIndex)) {
            throw new IllegalArgumentException();
        }

        editingUserIds.remove(clientIndex);
    }

    public void startEditTask(int clientIndex, Task task) {
        for (Integer key : editingTaskIds.keySet()) {
            long editingTaskId = editingTaskIds.get(key);
            if (editingTaskId == task.getId()) {
                throw new IllegalArgumentException();
            }
        }

        editingUserIds.put(clientIndex, task.getId());
    }

    public void stopEditTask(int clientIndex, Task task) {
        if (task.getId() != editingUserIds.get(clientIndex)) {
            throw new IllegalArgumentException();
        }

        editingUserIds.remove(clientIndex);
    }
}
