package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.helper.JDBC;
import sample.model.Countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The data access object (DAO) class for managing countries.
 */
public class CountriesDAO {

    private static ObservableList<Countries> allCountries = FXCollections.observableArrayList();

    private static ObservableList<Integer> allCountryIds = FXCollections.observableArrayList();

    /**
     * Retrieves all country IDs.
     *
     * @return The list of all country IDs.
     */
    public static ObservableList<Integer> getAllCountryIds() {
        return allCountryIds;
    }

    /**
     * Sets the list of all country IDs by querying the database.
     *
     */
    public static void setAllCountryIds() throws SQLException {
        String sql = "SELECT country_id FROM countries;";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            Integer countryId = (Integer) resultSet.getInt("country_id");
            allCountryIds.add(countryId);
        }
    }

    /**
     * Retrieves all countries.
     *
     * @return The list of all countries.
     */
    public static ObservableList<Countries> getAllCountries() {
        return allCountries;
    }

    /**
     * Sets the list of all countries by querying the database.
     *
     */
    public static void setAllCountries() throws SQLException {
        String sql = "SELECT * FROM countries;";
        allCountries.clear();
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            Integer countryId = (Integer) resultSet.getInt("Country_ID");
            String country = resultSet.getString("Country");

            Countries addedCountry = new Countries(countryId, country);
            allCountries.add(addedCountry);
        }
    }
}
