package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.helper.JDBC;
import sample.model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CustomersDAO {

    private static ObservableList<Customers> allCustomers = FXCollections.observableArrayList();

    public static ObservableList<Customers> getAllCustomers() {
        return allCustomers;
    }

    public static void addCustomer(Customers newCustomer) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID)\n" +
                "VALUES (?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        preparedStatement.setString(1, newCustomer.getCustomerName());
        preparedStatement.setString(2, newCustomer.getAddress());
        preparedStatement.setString(3, newCustomer.getPostalCode());
        preparedStatement.setString(4, newCustomer.getPhone());
        preparedStatement.setInt(5, newCustomer.getDivisionId());
        preparedStatement.executeUpdate();
    }

    public static void deleteCustomer(Customers oldCustomer) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?;";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        preparedStatement.setInt(1, oldCustomer.getCustomerId());
        preparedStatement.executeUpdate();
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

    public static void updateCustomer(Customers updatedCustomer) throws SQLException {
        String sql = "UPDATE customers SET Customer_ID = ?, Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?;";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        preparedStatement.setInt(1, updatedCustomer.getCustomerId());
        preparedStatement.setString(2, updatedCustomer.getCustomerName());
        preparedStatement.setString(3, updatedCustomer.getAddress());
        preparedStatement.setString(4, updatedCustomer.getPostalCode());
        preparedStatement.setString(5, updatedCustomer.getPhone());
        preparedStatement.setInt(6, updatedCustomer.getDivisionId());
        preparedStatement.setInt(7, updatedCustomer.getCustomerId());
        preparedStatement.executeUpdate();

        System.out.println(preparedStatement.toString());
    }
}
