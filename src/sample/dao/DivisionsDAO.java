package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.helper.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionsDAO {

    private static ObservableList allDivisonIds = FXCollections.observableArrayList();

    public static ObservableList getAllDivisonIds() {
        return allDivisonIds;
    }

    public static void setAllDivisonIds() throws SQLException {
        String sql = "SELECT Division_ID FROM first_level_divisions;";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            int divisionId = resultSet.getInt("Division_ID");
            allDivisonIds.add(divisionId);
        }
    }
}
