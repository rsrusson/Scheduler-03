package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.dao.AppointmentsDAO;
import sample.dao.CustomersDAO;
import sample.helper.JDBC;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    private static ResourceBundle rb;

    public static ResourceBundle getResourceBundle() {
        return rb;
    }

    @Override
    public void start(Stage primaryStage) throws IOException{

        if (Locale.getDefault().getLanguage().equals("fr")) {
            rb = ResourceBundle.getBundle("sample.locale", new Locale("fr"));
        } else {
            rb = ResourceBundle.getBundle("sample.locale", new Locale("en"));
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/login.fxml"));
        loader.setResources(rb);

        Parent root = loader.load();
        primaryStage.setTitle("Scheduler Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();

        ResourceBundle rb = ResourceBundle.getBundle("sample.locale", Locale.getDefault());

        if (Locale.getDefault().getLanguage().equals("en") || Locale.getDefault().getLanguage().equals("fr")){
            System.out.println(rb.getString("Schedule") + " " + rb.getString("User"));
        }

        launch(args);

        JDBC.closeConnection();
    }
}
