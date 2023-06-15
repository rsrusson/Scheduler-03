package sample.dao;


import com.mysql.cj.protocol.Resultset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.helper.JDBC;
import sample.model.Appointments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public abstract class AppointmentsDAO {

    private static ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();

    private static ObservableList<Appointments> byMonthAppointments = FXCollections.observableArrayList();

    private static ObservableList<Appointments> byWeekAppointments = FXCollections.observableArrayList();

    public static ObservableList<Appointments> getAllAppointments(){
        return allAppointments;
    }

    public static void setAllAppointments() throws SQLException{
        String sql = "SELECT * FROM appointments";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int appointmentId = resultSet.getInt("Appointment_ID");
            String title = resultSet.getString("Title");
            String description = resultSet.getString("Description");
            String location = resultSet.getString("Location");
            String type = resultSet.getString("Type");
            LocalDateTime start = resultSet.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = resultSet.getTimestamp("End").toLocalDateTime();
            int customerId = resultSet.getInt("Customer_ID");
            int userId = resultSet.getInt("User_ID");
            int contactId = resultSet.getInt("Contact_ID");

            Appointments appointment = new Appointments(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);
            allAppointments.add(appointment);
        }
    }

    public static ObservableList<Appointments> getByMonthAppointments() {
        return byMonthAppointments;
    }

    public static void setByMonthAppointments() throws SQLException {

        String sql = "SELECT * FROM appointments WHERE Start >= CURDATE() AND Start <= CURDATE() + INTERVAL 1 MONTH;";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int appointmentId = resultSet.getInt("Appointment_ID");
            String title = resultSet.getString("Title");
            String description = resultSet.getString("Description");
            String location = resultSet.getString("Location");
            String type = resultSet.getString("Type");
            LocalDateTime start = resultSet.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = resultSet.getTimestamp("End").toLocalDateTime();
            int customerId = resultSet.getInt("Customer_ID");
            int userId = resultSet.getInt("User_ID");
            int contactId = resultSet.getInt("Contact_ID");

            Appointments appointment = new Appointments(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);
           byMonthAppointments.add(appointment);
        }
    }

    public static ObservableList<Appointments> getByWeekAppointments() {
        return byWeekAppointments;
    }

    public static void setByWeekAppointments() throws SQLException {
        String sql = "SELECT * FROM appointments WHERE Start >= CURDATE() AND Start <= CURDATE() + INTERVAL 1 WEEK;";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int appointmentId = resultSet.getInt("Appointment_ID");
            String title = resultSet.getString("Title");
            String description = resultSet.getString("Description");
            String location = resultSet.getString("Location");
            String type = resultSet.getString("Type");
            LocalDateTime start = resultSet.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = resultSet.getTimestamp("End").toLocalDateTime();
            int customerId = resultSet.getInt("Customer_ID");
            int userId = resultSet.getInt("User_ID");
            int contactId = resultSet.getInt("Contact_ID");

            Appointments appointment = new Appointments(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);
            byWeekAppointments.add(appointment);
        }
    }
}
