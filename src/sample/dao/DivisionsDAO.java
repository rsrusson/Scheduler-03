package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.helper.JDBC;
import sample.model.Divisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The data access object (DAO) class for managing divisions.
 */
public class DivisionsDAO {

    private static ObservableList<Divisions> allDivisions = FXCollections.observableArrayList();

    private static ObservableList<Integer> allDivisonIds = FXCollections.observableArrayList();

    /**
     * Retrieves all division IDs.
     *
     * @return The list of all division IDs.
     */
    public static ObservableList<Integer> getAllDivisonIds() {
        return allDivisonIds;
    }

    /**
     * Sets the list of all division IDs by querying the database.
     *
     */
    public static void setAllDivisonIds() throws SQLException {
        String sql = "SELECT Division_ID FROM first_level_divisions;";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            Integer divisionId = (Integer) resultSet.getInt("Division_ID");
            allDivisonIds.add(divisionId);
        }
    }

    /**
     * Retrieves all divisions.
     *
     * @return The list of all divisions.
     */
    public static ObservableList<Divisions> getAllDivisions() {
        return allDivisions;
    }

    /**
     * Sets the list of all divisions by querying the database.
     *
     */
    public static void setAllDivisions() throws SQLException {
        String sql = "SELECT * FROM first_level_divisions;";
        allDivisions.clear();
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            Integer divisionId = (Integer) resultSet.getInt("Division_ID");
            String divisionName = resultSet.getString("Division");
            Integer countryId = (Integer) resultSet.getInt("Country_ID");
            Divisions newDivision = new Divisions(divisionId, divisionName, countryId);
            allDivisions.add(newDivision);
        }
    }
}
