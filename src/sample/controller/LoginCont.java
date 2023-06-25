package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
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
    private TextField locationTxt;

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

        ResourceBundle resourceBundle = ResourceBundle.getBundle("Locales", Locale.getDefault());

        if (Locale.getDefault().getLanguage().equals("en") || Locale.getDefault().getLanguage().equals("fr"))
            System.out.println(resourceBundle.getString("", ""));
        /*
        ResourceBundle bundle;
        boolean isFrench;
        if(USER NOT FRENCH)
            bundle = ResourceBundle.getBundle("Locales", Locale.getDefault);
        else
            bundle = ResourceBundle.getBundle("Locales", Locale.FRENCH);
        */
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

    public TextField getLocationTxt() {
        return locationTxt;
    }

    public void setLocationTxt(TextField locationTxt) {
        this.locationTxt = locationTxt;
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
