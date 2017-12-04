package com.db;

import com.controllers.Storage;
import java.sql.SQLException;

public class TestJdbc {
    
    public static void main(String[] agrs) throws ClassNotFoundException, SQLException {
        ConnectionJdbc connect = new ConnectionJdbc();
        Storage storage = Storage.getInstance();
        System.out.println(storage.getUsersList());
        System.out.println(storage.getTasksList());
    }   
}
