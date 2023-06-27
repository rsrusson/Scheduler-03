package sample.dao;


import com.mysql.cj.protocol.Resultset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.helper.JDBC;
import sample.model.Appointments;

import java.sql.*;
import java.time.*;

/**
 * The data access object (DAO) class for managing appointments.
 */
public class AppointmentsDAO {

    private static ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();

    private static ObservableList<Appointments> byMonthAppointments = FXCollections.observableArrayList();

    private static ObservableList<Appointments> byWeekAppointments = FXCollections.observableArrayList();

    /**
     * Retrieves all appointments.
     *
     * @return The list of all appointments.
     */
    public static ObservableList<Appointments> getAllAppointments(){
        return allAppointments;
    }

    /**
     * Sets the list of all appointments by querying the database.
     *
     */
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

            Timestamp startTimestamp = resultSet.getTimestamp("Start");
            LocalDateTime startLocalDateTime = startTimestamp.toLocalDateTime();
            ZonedDateTime startUTC = ZonedDateTime.of(startLocalDateTime, ZoneOffset.UTC);
            ZonedDateTime start = startUTC;

            Timestamp endTimestamp = resultSet.getTimestamp("End");
            LocalDateTime endLocalDateTime = endTimestamp.toLocalDateTime();
            ZonedDateTime endUTC = ZonedDateTime.of(endLocalDateTime, ZoneOffset.UTC);
            ZonedDateTime end = endUTC;

            int customerId = resultSet.getInt("Customer_ID");
            int userId = resultSet.getInt("User_ID");
            int contactId = resultSet.getInt("Contact_ID");

            Appointments appointment = new Appointments(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);
            allAppointments.add(appointment);
        }
    }

    /**
     * Retrieves the appointments filtered by month.
     *
     * @return The list of appointments filtered by month.
     */
    public static ObservableList<Appointments> getByMonthAppointments() {
        return byMonthAppointments;
    }

    /**
     * Sets the list of appointments filtered by month by querying the database.
     *
     */
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

            Timestamp startTimestamp = resultSet.getTimestamp("Start");
            LocalDateTime startLocalDateTime = startTimestamp.toLocalDateTime();
            ZonedDateTime startUTC = ZonedDateTime.of(startLocalDateTime, ZoneOffset.UTC);
            ZonedDateTime start = startUTC;

            Timestamp endTimestamp = resultSet.getTimestamp("End");
            LocalDateTime endLocalDateTime = endTimestamp.toLocalDateTime();
            ZonedDateTime endUTC = ZonedDateTime.of(endLocalDateTime, ZoneOffset.UTC);
            ZonedDateTime end = endUTC;

            int customerId = resultSet.getInt("Customer_ID");
            int userId = resultSet.getInt("User_ID");
            int contactId = resultSet.getInt("Contact_ID");

            Appointments appointment = new Appointments(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);
            byMonthAppointments.add(appointment);
        }
    }

    /**
     * Retrieves the appointments filtered by week.
     *
     * @return The list of appointments filtered by week.
     */
    public static ObservableList<Appointments> getByWeekAppointments() {
        return byWeekAppointments;
    }

    /**
     * Sets the list of appointments filtered by week by querying the database.
     *
     */
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

            Timestamp startTimestamp = resultSet.getTimestamp("Start");
            LocalDateTime startLocalDateTime = startTimestamp.toLocalDateTime();
            ZonedDateTime startUTC = ZonedDateTime.of(startLocalDateTime, ZoneOffset.UTC);
            ZonedDateTime start = startUTC;

            Timestamp endTimestamp = resultSet.getTimestamp("End");
            LocalDateTime endLocalDateTime = endTimestamp.toLocalDateTime();
            ZonedDateTime endUTC = ZonedDateTime.of(endLocalDateTime, ZoneOffset.UTC);
            ZonedDateTime end = endUTC;

            int customerId = resultSet.getInt("Customer_ID");
            int userId = resultSet.getInt("User_ID");
            int contactId = resultSet.getInt("Contact_ID");

            Appointments appointment = new Appointments(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);
            byWeekAppointments.add(appointment);
        }
    }

    /**
     * Adds a new appointment to the database.
     *
     * @param newAppointment The new appointment to be added.
     */
    public static void addAppointment(Appointments newAppointment) throws SQLException {
        String sql = "INSERT INTO appointments(Title, Description, Location, `Type`, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        preparedStatement.setString(1, newAppointment.getTitle());
        preparedStatement.setString(2, newAppointment.getDescription());
        preparedStatement.setString(3, newAppointment.getLocation());
        preparedStatement.setString(4, newAppointment.getType());
        preparedStatement.setTimestamp(5, Timestamp.valueOf(newAppointment.getStart().toLocalDateTime()));
        preparedStatement.setTimestamp(6, Timestamp.valueOf(newAppointment.getEnd().toLocalDateTime()));
        preparedStatement.setInt(7, newAppointment.getCustomerId());
        preparedStatement.setInt(8, newAppointment.getUserId());
        preparedStatement.setInt(9, newAppointment.getContactId());
        preparedStatement.executeUpdate();

        System.out.println("Start in add SQL " + Timestamp.valueOf(newAppointment.getStart().withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()));

    }

    /**
     * Updates an existing appointment in the database.
     *
     * @param updatedAppointment The updated appointment.
     */
    public static void updateAppointment(Appointments updatedAppointment) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, `Type` = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        preparedStatement.setString(1, updatedAppointment.getTitle());
        preparedStatement.setString(2, updatedAppointment.getDescription());
        preparedStatement.setString(3, updatedAppointment.getLocation());
        preparedStatement.setString(4, updatedAppointment.getType());
        preparedStatement.setTimestamp(5, Timestamp.valueOf(updatedAppointment.getStart().toLocalDateTime()));
        preparedStatement.setTimestamp(6, Timestamp.valueOf(updatedAppointment.getEnd().toLocalDateTime()));
        preparedStatement.setInt(7, updatedAppointment.getCustomerId());
        preparedStatement.setInt(8, updatedAppointment.getUserId());
        preparedStatement.setInt(9, updatedAppointment.getContactId());
        preparedStatement.setInt(10, updatedAppointment.getAppointmentId());
        preparedStatement.executeUpdate();

        System.out.println("Start in update SQL " + Timestamp.valueOf(updatedAppointment.getStart().withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()));

    }

    /**
     * Deletes an appointment from the database.
     *
     * @param selectedAppointment The appointment to be deleted.
     */
    public static void deleteAppointment(Appointments selectedAppointment) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        preparedStatement.setInt(1, selectedAppointment.getAppointmentId());
        preparedStatement.executeUpdate();
    }
}
