package com.model;

import java.util.LinkedList;

public class TasksList extends LinkedList<Task> {
    
    public Task getTaskById(long id){
        for(Task t: this) {
           if (t.getId() == id) {
               return t;
           } 
        }
        return null;
    }
    
}
