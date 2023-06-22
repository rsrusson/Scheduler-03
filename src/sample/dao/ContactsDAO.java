package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.helper.JDBC;
import sample.model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactsDAO {

    private static ObservableList allContacts = FXCollections.observableArrayList();

    private static ObservableList allContactIds = FXCollections.observableArrayList();

    public static ObservableList getAllContactIds() {
        return allContactIds;
    }

    public static void setAllContactIds() throws SQLException {
        String sql = "SELECT Contact_ID FROM contacts;";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            int contactId = resultSet.getInt("Contact_ID");
            allContactIds.add(contactId);
        }
    }

    public static ObservableList getAllContacts() {
        return allContacts;
    }

    public static void setAllContacts() throws SQLException {
        String sql = "SELECT * FROM contacts;";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int contactId = resultSet.getInt("Contact_ID");
            String contactName = resultSet.getString("Contact_Name");
            String email = resultSet.getString("Email");
            Contacts newContact = new Contacts(contactId, contactName, email);
            allContacts.add(newContact);
        }
    }
}
