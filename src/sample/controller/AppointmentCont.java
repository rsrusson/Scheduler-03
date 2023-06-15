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
import java.sql.SQLException;
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
    private RadioButton allAppointmentBtt;

    @FXML
    private RadioButton byMonthBtt;

    @FXML
    private RadioButton byWeekBtt;

    private Appointments appointmentSelected = appointmentsTableView.getSelectionModel().getSelectedItem();

    @FXML
    void allAppointmentAction(ActionEvent event) throws SQLException {
        appointmentsTableView.getItems().clear();
        AppointmentsDAO.setAllAppointments();
        appointmentsTableView.setItems(AppointmentsDAO.getAllAppointments());
        appointmentsTableView.refresh();
    }

    @FXML
    void byMonthAction(ActionEvent event) throws SQLException {
        appointmentsTableView.getItems().clear();
        AppointmentsDAO.setByMonthAppointments();
        appointmentsTableView.setItems(AppointmentsDAO.getByMonthAppointments());
        appointmentsTableView.refresh();
    }

    @FXML
    void byWeekAction(ActionEvent event) throws SQLException {
        appointmentsTableView.getItems().clear();
        AppointmentsDAO.setByWeekAppointments();
        appointmentsTableView.setItems(AppointmentsDAO.getByWeekAppointments());
        appointmentsTableView.refresh();
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

        try {
            AppointmentsDAO.setAllAppointments();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

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

        if (appointmentSelected != null) {
            appointmentIdTxt.setText(String.valueOf(appointmentSelected.getAppointmentId()));
            titleTxt.setText(String.valueOf(appointmentSelected.getAppointmentId()));
            descriptionTxt.setText(String.valueOf(appointmentSelected.getAppointmentId()));
            locationTxt.setText(String.valueOf(appointmentSelected.getAppointmentId()));
            typeTxt.setText(String.valueOf(appointmentSelected.getAppointmentId()));
            startTxt.setText(String.valueOf(appointmentSelected.getAppointmentId()));
            endTxt.setText(String.valueOf(appointmentSelected.getAppointmentId()));
            customerIdTxt.setText(String.valueOf(appointmentSelected.getAppointmentId()));
            userIdTxt.setText(String.valueOf(appointmentSelected.getAppointmentId()));
            contactIdCB.commitValue(String.valueOf(appointmentSelected.getAppointmentId()));
        }

    }


}
