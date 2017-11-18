package com.view.tableModel;

import com.models.Task;
import com.models.TasksList;
import com.models.User;
import com.models.UsersList;
import java.text.DateFormat;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;

public class TasksTableModel extends DefaultTableModel {

    private final String[] headers = new String[]{
        "Название",
        "Описание",
        "Дата создания",
        "Дата окончания",
        "Исполнитель"
    };
    private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, new Locale("ru", "RU"));
    private TasksList tasksList;
    private UsersList usersList;

    public TasksTableModel() {
    }

    public TasksTableModel(TasksList tasksList, UsersList usersList) {
        this.tasksList = tasksList;
        this.usersList = usersList;
    }

    public void setData(TasksList tasksList, UsersList usersList) {
        this.tasksList = tasksList;
        this.usersList = usersList;
    }

    @Override
    public int getRowCount() {
        if (tasksList != null) {
            return tasksList.size();
        } else {
            return 0;
        }
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
        Task task = tasksList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return task.getName();
            case 1:
                return task.getDescription();
            case 2:
                return dateFormat.format(task.getCreatedDate());
            case 3:
                return (task.getEndDate() != null) ? dateFormat.format(task.getEndDate()) : "";
            case 4:
                User user = usersList.getUserById(task.getUserId());
                return (user != null) ? user.getName() : "";
            default:
                return "";
        }
    }
}
