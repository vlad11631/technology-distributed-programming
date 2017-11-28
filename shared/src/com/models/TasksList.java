package com.models;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tasksList")
public class TasksList {

    ArrayList<Task> tasks = new ArrayList<Task>();

    @XmlElementWrapper(name = "tasks")
    @XmlElement(name = "task")
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public Task getTaskById(long id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "TasksList{" + "tasks=" + tasks + '}';
    }
}
