package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.helper.JDBC;
import sample.model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomersDAO {

    private static ObservableList<Customers> allCustomers = FXCollections.observableArrayList();

    public static ObservableList<Customers> getAllCustomers() {
        return allCustomers;
    }

    public static void setAllCustomers() throws SQLException {
        String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, customers.Division_ID, first_level_divisions.country_id\n" +
                "FROM customers\n" +
                "JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID;";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            int customerId = resultSet.getInt("Customer_ID");
            String customerName = resultSet.getString("Customer_Name");
            String address = resultSet.getString("Address");
            String postalCode = resultSet.getString("Postal_Code");
            String phone = resultSet.getString("Phone");
            int divisionId = resultSet.getInt("Division_ID");
            int countryId = resultSet.getInt("country_id");

            Customers customer = new Customers(customerId, customerName, address, postalCode, phone, divisionId, countryId);
            allCustomers.add(customer);
        }
    }
}
