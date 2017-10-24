package com.model;

import java.util.LinkedList;

public class UsersList extends LinkedList<User> {
    
    public User getUserById(long id){
        for(User u: this) {
           if (u.getId() == id) {
               return u;
           } 
        }
        return null;
    }  
}
