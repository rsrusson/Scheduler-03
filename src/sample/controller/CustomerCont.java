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

public class CustomerCont implements Initializable {

    @FXML
    private TableColumn<?, ?> addressCol;

    @FXML
    private TextField addressTxt;

    @FXML
    private ComboBox<?> countryCB;

    @FXML
    private TableColumn<?, ?> countryCol;

    @FXML
    private TableView<?> customerTV;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TextField idTxt;

    @FXML
    private Button menuBtt;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TextField nameTxt;

    @FXML
    private TableColumn<?, ?> phoneCol;

    @FXML
    private TextField phoneTxt;

    @FXML
    private TableColumn<?, ?> postalCol;

    @FXML
    private TextField postalTxt;

    @FXML
    private ComboBox<?> stateCB;

    @FXML
    private TableColumn<?, ?> stateCol;

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
