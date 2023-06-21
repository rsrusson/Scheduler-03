package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.helper.JDBC;
import sample.model.Countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountriesDAO {

    private static ObservableList<Countries> allCountries = FXCollections.observableArrayList();

    private static ObservableList<Integer> allCountryIds = FXCollections.observableArrayList();

    public static ObservableList<Integer> getAllCountryIds() {
        return allCountryIds;
    }

    public static void setAllCountryIds() throws SQLException {
        String sql = "SELECT country_id FROM countries;";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            Integer countryId = (Integer) resultSet.getInt("country_id");
            allCountryIds.add(countryId);
        }
    }

    public static ObservableList<Countries> getAllCountries() {
        return allCountries;
    }

    public static void setAllCountries() throws SQLException {
        String sql = "SELECT * FROM countries;";

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
