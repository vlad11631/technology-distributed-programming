package com.view.tableModel;

import com.models.User;
import com.models.UsersList;
import javax.swing.table.DefaultTableModel;

public class UsersTableModel extends DefaultTableModel {
   
    private final String[] headers = new String[] {
        "ФИО",
        "Должность"
    };
    private UsersList usersList;
    
    public UsersTableModel() {  
    }
    
    public UsersTableModel(UsersList usersList) {
       this.usersList = usersList;
    }

    public void setData(UsersList usersList) {
        this.usersList = usersList;
    }
    
    @Override
    public int getRowCount() {
        if (usersList != null) return usersList.size();
        else return 0;
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return headers[column];
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false; 
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = usersList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return user.getName();
            case 1:
                return user.getPost();
            default:
                return "";
        }
    }
}
