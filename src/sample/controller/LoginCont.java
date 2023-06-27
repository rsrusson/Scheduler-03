package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Main;
import sample.dao.UsersDAO;
import sample.model.Users;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class for the login functionality.
 */
public class LoginCont implements Initializable {

    private ObservableList<Users> allUsers = FXCollections.observableArrayList();

    @FXML
    private TextField errorTxt;

    @FXML
    private Button exitBtt;

    @FXML
    private Label locationLbl;

    @FXML
    private Label locationTxtLbl;

    @FXML
    private Button loginBtt;

    @FXML
    private Label passwordLbl;

    @FXML
    private TextField passwordTxt;

    @FXML
    private Label titleLbl;

    @FXML
    private Label usernameLbl;

    @FXML
    private TextField usernameTxt;

    /**
     * Initializes the LoginCont controller.
     *
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        try {
            UsersDAO.setAllUsers();
            allUsers = UsersDAO.getAllUsers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        ZoneId myId = ZonedDateTime.now().getZone();

        locationTxtLbl.setText(String.valueOf(myId));

        String titleLblTxt = Main.getResourceBundle().getString("Schedule") + " " + Main.getResourceBundle().getString("Manager");
        String usernameLblTxt = Main.getResourceBundle().getString("Username");
        String passwordLblTxt = Main.getResourceBundle().getString("Password");
        String loginBttTxt = Main.getResourceBundle().getString("Login");
        String locationLblTxt = Main.getResourceBundle().getString("User") + " " + Main.getResourceBundle().getString("Location");
        String exitBttTxt = Main.getResourceBundle().getString("Exit");

        titleLbl.setText(titleLblTxt);
        usernameLbl.setText(usernameLblTxt);
        passwordLbl.setText(passwordLblTxt);
        loginBtt.setText(loginBttTxt);
        locationLbl.setText(locationLblTxt);
        exitBtt.setText(exitBttTxt);
    }

    /**
     * Displays an information alert dialog with the given title, header text, and content text.
     *
     * @param title       The title of the alert.
     * @param headerText  The header text of the alert.
     * @param contentText The content text of the alert.
     */
    static void alertInfo(String title, String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /**
     * Displays a confirmation dialog with the given title, header text, and content text to confirm the exit action.
     * If the user chooses to exit, the application is closed.
     *
     * @param title       The title of the confirmation dialog.
     * @param headerText  The header text of the confirmation dialog.
     * @param contentText The content text of the confirmation dialog.
     */
    static void exitConfirmation(String title, String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)
            System.exit(0);
    }

    /**
     * Handles the action event when the exit button is clicked.
     * Displays a confirmation dialog to confirm the exit action.
     *
     * @param event The action event.
     */
    @FXML
    void exitAction(ActionEvent event) {
        exitConfirmation(Main.getResourceBundle().getString("Warning"), Main.getResourceBundle().getString("Exit"), Main.getResourceBundle().getString("Confirm"));
    }

    /**
     * Handles the action event when the login button is clicked.
     * Performs the login functionality by checking the username and password entered by the user.
     * If the login is successful, it loads the menu view.
     *
     * @param event The action event.
     */
    @FXML
    void loginAction(ActionEvent event) throws IOException {
        boolean login = false;

        for (Users user : allUsers) {
            if (usernameTxt.getText().equals(user.getUserName()) && passwordTxt.getText().equals(user.getPassword())){
                login = true;
                break;
            }
        }

        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        try (PrintWriter writer = new PrintWriter(new FileWriter("login_activity.txt", true))) {
            writer.print("Username: " + username + " ");
            writer.print("Password: " + password + " ");
            writer.print("Successful: " + login + " ");
            writer.println("Login time: " + LocalDateTime.now());
        } catch (IOException e) {
            alertInfo(Main.getResourceBundle().getString("Activity_Error_Title"), Main.getResourceBundle().getString("Activity_Error_Header"), Main.getResourceBundle().getString("Activity_Error_Text"));
        }

        if (login) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/menu.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) loginBtt.getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.show();
        } else {
            alertInfo(Main.getResourceBundle().getString("Warning"), Main.getResourceBundle().getString("Incorrect") + Main.getResourceBundle().getString("Credentials"), Main.getResourceBundle().getString("Try") + Main.getResourceBundle().getString("Again"));
            return;
        }
    }

}
