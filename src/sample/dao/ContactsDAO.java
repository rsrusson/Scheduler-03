package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.helper.JDBC;
import sample.model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The data access object (DAO) class for managing contacts.
 */
public class ContactsDAO {

    private static ObservableList<Contacts> allContacts = FXCollections.observableArrayList();

    private static ObservableList<Integer> allContactIds = FXCollections.observableArrayList();

    /**
     * Retrieves all contact IDs.
     *
     * @return The list of all contact IDs.
     */
    public static ObservableList<Integer> getAllContactIds() {
        return allContactIds;
    }

    /**
     * Sets the list of all contact IDs by querying the database.
     *
     */
    public static void setAllContactIds() throws SQLException {
        String sql = "SELECT Contact_ID FROM contacts;";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            int contactId = resultSet.getInt("Contact_ID");
            allContactIds.add(contactId);
        }
    }

    /**
     * Retrieves all contacts.
     *
     * @return The list of all contacts.
     */
    public static ObservableList<Contacts> getAllContacts() {
        return allContacts;
    }

    /**
     * Sets the list of all contacts by querying the database.
     *
     */
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
