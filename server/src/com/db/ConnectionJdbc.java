package com.db;

import com.models.Task;
import com.models.TasksList;
import com.models.User;
import com.models.UsersList;
import java.sql.*;

public class ConnectionJdbc {

    public ConnectionJdbc() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "123");
    }

    //Users
    //МЕТОД запрос из базы данных Users
    public UsersList selectUsers() throws SQLException {
        Connection connection = getConnection();
        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Users");
        ResultSet rs = pstmt.executeQuery();
        connection.close();
        
        UsersList usersList = new UsersList();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name").trim());
            user.setPost(rs.getString("post").trim());
            usersList.getUsers().add(user);
        }
        return usersList;
    }

    //МЕТОД вставки строчки в базу данных Users
    public void insertUser(User user) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement stat = connection.prepareStatement(
                "INSERT INTO Users(id, name, post) VALUES (NEXTVAL('Seq_id'), ?, ?);"
        );
        stat.setString(1, user.getName());
        stat.setString(2, user.getPost());
        stat.executeUpdate();
        connection.close();
    }
    
    //МЕТОД изменения строчки из базы данных Teachers
    public void updateUser(int id, User user) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement stat = connection.prepareStatement(
                "UPDATE Users SET name = ?, post = ? WHERE id  = ? ");
        stat.setString(1, user.getName());
        stat.setString(2, user.getPost());
        stat.setInt(3, id);
        stat.executeUpdate();
        connection.close();
    }
    
    //МЕТОД удаления строчки из базы данных Users
    public void deleteUser(int id) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement stat = connection.prepareStatement("DELETE FROM Users WHERE id = ? ");
        stat.setInt(1, id);
        stat.executeUpdate();
        connection.close();
    }
     
    //Tasks
    //МЕТОД запрос из базы данных Tasks
    public TasksList selectTasks() throws SQLException {
        Connection connection = getConnection();
        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Tasks");
        ResultSet rs = pstmt.executeQuery();
        connection.close();
        
        TasksList tasksList = new TasksList();
        while (rs.next()) {
            Task task = new Task();
            task.setId(rs.getInt("id"));
            task.setName(rs.getString("name").trim());
            task.setDescription(rs.getString("description").trim());
            task.setCreatedDate(rs.getDate("createdDate"));
            task.setEndDate(rs.getDate("endDate"));
            task.setUserId(rs.getInt("userId"));
            tasksList.getTasks().add(task);
        }
        return tasksList;
    }
    
    //МЕТОД вставки строчки в базу данных Tasks
    public void insertTask(Task task) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement stat = connection.prepareStatement(
                "INSERT INTO Tasks(id, name, description, createdDate, endDate, userId) " +
                "VALUES (NEXTVAL('Seq_id'), ?, ?, ?, ?, ?);"
        );
        stat.setString(1, task.getName());
        stat.setString(2, task.getDescription());
        stat.setDate(3, new Date(task.getCreatedDate().getTime()));
        stat.setDate(4, new Date(task.getEndDate().getTime()));
        stat.setInt(5, task.getUserId());
        stat.executeUpdate();
        connection.close();
    }

    //МЕТОД изменения строчки из базы данных Students
    public void updateTask(int id, Task task) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement stat = connection.prepareStatement(
                "UPDATE Tasks SET name = ?, description = ?, createdDate = ?, endDate = ?, userId = ? WHERE id = ? ");
        stat.setString(1, task.getName());
        stat.setString(2, task.getDescription());
        stat.setDate(3, new Date(task.getCreatedDate().getTime()));
        stat.setDate(4, new Date(task.getEndDate().getTime()));
        stat.setInt(5, task.getUserId());
        stat.setInt(6, id);
        stat.executeUpdate();
        connection.close();
    }
    
    //МЕТОД удаления строчки из базы данных Students
    public void deleteTask(int id) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement stat = connection.prepareStatement("DELETE FROM Tasks WHERE id = ? ");
        stat.setInt(1, id);
        stat.executeUpdate();
        connection.close();
    }
}
