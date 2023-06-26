package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.helper.JDBC;
import sample.model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersDAO {

    private static ObservableList<Users> allUsers = FXCollections.observableArrayList();

    public static ObservableList<Users> getAllUsers() {
        return allUsers;
    }

    public static void setAllUsers() throws SQLException {
        String sql = "SELECT * FROM users;";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            int userId = resultSet.getInt("User_ID");
            String userName = resultSet.getString("User_Name");
            String password = resultSet.getString("Password");
            Users newUser = new Users(userId, userName, password);
            allUsers.add(newUser);
        }
    }
}
