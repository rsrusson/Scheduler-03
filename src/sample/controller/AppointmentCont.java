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
import java.util.ResourceBundle;

public class AppointmentCont implements Initializable {

    @FXML
    private TableColumn<?, ?> ApptIdCol;

    @FXML
    private TableView<?> allApptTV;

    @FXML
    private TextField appointmentTxt;

    @FXML
    private ComboBox<?> contactCB;

    @FXML
    private TableColumn<?, ?> contactCol;

    @FXML
    private TableColumn<?, ?> customerIdCol;

    @FXML
    private TextField customerIdTxt;

    @FXML
    private TableColumn<?, ?> descriptionCol;

    @FXML
    private TextField descriptionTxt;

    @FXML
    private TableColumn<?, ?> endCol;

    @FXML
    private TextField endTxt;

    @FXML
    private TableColumn<?, ?> locationCol;

    @FXML
    private TextField locationTxt;

    @FXML
    private TableColumn<?, ?> startCol;

    @FXML
    private TextField startTxt;

    @FXML
    private ToggleGroup tableViewTG;

    @FXML
    private TableColumn<?, ?> titleCol;

    @FXML
    private TextField titleTxt;

    @FXML
    private TableColumn<?, ?> typeCol;

    @FXML
    private TextField typeTxt;

    @FXML
    private TableColumn<?, ?> userIdCol;

    @FXML
    private TextField userIdTxt;

    @FXML
    private Button menuBtt;

    @FXML
    void addAction(ActionEvent event) {

    }

    @FXML
    void deleteAction(ActionEvent event) {

    }

    @FXML
    void menuAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/menu.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) menuBtt.getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.show();
    }

    @FXML
    void updateAction(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}
