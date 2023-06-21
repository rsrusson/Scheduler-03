package sample.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.dao.AppointmentsDAO;
import sample.model.Appointments;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReportCont implements Initializable {

    private ObservableList<Appointments> allAppointments;

    @FXML
    private TableView<Appointments> allApptTV;

    @FXML
    private TableColumn<?, ?> appointmentIdCol;

    @FXML
    private TableColumn<?, ?> contactIdCol;

    @FXML
    private TableColumn<?, ?> customerIdCol;

    @FXML
    private TableColumn<?, ?> descriptionCol;

    @FXML
    private TableColumn<?, ?> endCol;

    @FXML
    private Button menuBtt;

    @FXML
    private ComboBox<?> monthCB;

    @FXML
    private TextField monthTxt;

    @FXML
    private TableColumn<?, ?> startCol;

    @FXML
    private TableColumn<?, ?> titleCol;

    @FXML
    private ComboBox<?> typeCB;

    @FXML
    private TableColumn<?, ?> typeCol;

    @FXML
    private TextField typeTxt;

    @FXML
    private ComboBox<?> contactCB;

    @FXML
    void byContactBtt(ActionEvent event) {

    }

    @FXML
    void contactCBAction(ActionEvent event) {

    }

    static void alertInfo(String title, String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
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
    void perMonthAction(ActionEvent event) {

    }

    @FXML
    void perTypeAction(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            AppointmentsDAO.setAllAppointments();
            allAppointments = AppointmentsDAO.getAllAppointments();
        } catch (SQLException throwables) {
            alertInfo("Error", "SQL Exception Error", "Please ensure proper data is input");
        }

        allApptTV.setItems(AppointmentsDAO.getAllAppointments());
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        contactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        allApptTV.sort();
    }
}
