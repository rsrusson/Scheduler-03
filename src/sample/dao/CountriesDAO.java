package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.helper.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountriesDAO {

    private static ObservableList allCountryIds = FXCollections.observableArrayList();

    public static ObservableList getAllCountryIds() {
        return allCountryIds;
    }

    public static void setAllCountryIds() throws SQLException {
        String sql = "SELECT country_id FROM countries;";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            int countryId = resultSet.getInt("country_id");
            allCountryIds.add(countryId);
        }
    }
}
