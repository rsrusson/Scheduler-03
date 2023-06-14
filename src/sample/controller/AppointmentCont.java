package sample.controller;

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
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AppointmentCont implements Initializable {

    @FXML
    private TableColumn<Appointments, Integer> appointmentIdCol;

    @FXML
    private TableView<Appointments> appointmentsTableView;

    @FXML
    private TextField appointmentIdTxt;

    @FXML
    private ComboBox<Integer> contactIdCB;

    @FXML
    private TableColumn<Appointments, Integer> contactIdCol;

    @FXML
    private TableColumn<Appointments, Integer> customerIdCol;

    @FXML
    private TextField customerIdTxt;

    @FXML
    private TableColumn<Appointments, String> descriptionCol;

    @FXML
    private TextField descriptionTxt;

    @FXML
    private TableColumn<Appointments, LocalDateTime> endCol;

    @FXML
    private TextField endTxt;

    @FXML
    private TableColumn<Appointments, String> locationCol;

    @FXML
    private TextField locationTxt;

    @FXML
    private TableColumn<Appointments, LocalDateTime> startCol;

    @FXML
    private TextField startTxt;

    @FXML
    private ToggleGroup tableViewTG;

    @FXML
    private TableColumn<Appointments, String> titleCol;

    @FXML
    private TextField titleTxt;

    @FXML
    private TableColumn<Appointments, String> typeCol;

    @FXML
    private TextField typeTxt;

    @FXML
    private TableColumn<Appointments, Integer> userIdCol;

    @FXML
    private TextField userIdTxt;

    @FXML
    private Button menuBtt;

    @FXML
    void allAppointmentAction(ActionEvent event) {
        appointmentsTableView.getItems().clear();
        appointmentsTableView.setItems(AppointmentsDAO.getAllAppointments());
    }

    @FXML
    void byMonthAction(ActionEvent event) {
        appointmentsTableView.getItems().clear();
        appointmentsTableView.setItems(AppointmentsDAO.getByMonthAppointments());
    }

    @FXML
    void byWeekAction(ActionEvent event) {
        appointmentsTableView.getItems().clear();
        appointmentsTableView.setItems(AppointmentsDAO.getByWeekAppointments());
    }

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

        appointmentsTableView.setItems(AppointmentsDAO.getAllAppointments());

        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        contactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));

    }


}
