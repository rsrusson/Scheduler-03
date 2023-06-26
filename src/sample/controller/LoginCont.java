package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Main;


import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginCont implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){


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

    static void alertInfo(String title, String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    static void exitConfirmation(String title, String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)
            System.exit(0);
    }

    @FXML
    void exitAction(ActionEvent event) {
        exitConfirmation("Warning!", "Exit initiated", "Are you sure you want to exit?");
    }

    @FXML
    void loginAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/menu.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) loginBtt.getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.show();
    }

    public TextField getErrorTxt() {
        return errorTxt;
    }

    public void setErrorTxt(TextField errorTxt) {
        this.errorTxt = errorTxt;
    }

    public Button getExitBtt() {
        return exitBtt;
    }

    public void setExitBtt(Button exitBtt) {
        this.exitBtt = exitBtt;
    }

    public Label getLocationLbl() {
        return locationLbl;
    }

    public void setLocationLbl(Label locationLbl) {
        this.locationLbl = locationLbl;
    }

    public Label getLocationTxt() {
        return locationTxtLbl;
    }

    public void setLocationTxt(String locationString) {
    }

    public Button getLoginBtt() {
        return loginBtt;
    }

    public void setLoginBtt(Button loginBtt) {
        this.loginBtt = loginBtt;
    }

    public Label getPasswordLbl() {
        return passwordLbl;
    }

    public void setPasswordLbl(Label passwordLbl) {
        this.passwordLbl = passwordLbl;
    }

    public TextField getPasswordTxt() {
        return passwordTxt;
    }

    public void setPasswordTxt(TextField passwordTxt) {
        this.passwordTxt = passwordTxt;
    }

    public Label getTitleLbl() {
        return titleLbl;
    }

    public void setTitleLbl(Label titleLbl) {
        this.titleLbl = titleLbl;
    }

    public Label getUsernameLbl() {
        return usernameLbl;
    }

    public void setUsernameLbl(Label usernameLbl) {
        this.usernameLbl = usernameLbl;
    }

    public TextField getUsernameTxt() {
        return usernameTxt;
    }

    public void setUsernameTxt(TextField usernameTxt) {
        this.usernameTxt = usernameTxt;
    }
}
