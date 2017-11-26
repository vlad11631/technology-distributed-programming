package com.models;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "Users")
public class UsersList {
    
    ArrayList<User> users = new ArrayList<User>();
    
    @XmlElementWrapper(name = "users")
    public ArrayList<User> getUsers() {
        return users;
    }
    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
    
    public User getUserById(long id){
        for(User user: users) {
           if (user.getId() == id) {
               return user;
           } 
        }
        return null;
    }  

    @Override
    public String toString() {
        return "UsersList{" + "users=" + users + '}';
    }
}
