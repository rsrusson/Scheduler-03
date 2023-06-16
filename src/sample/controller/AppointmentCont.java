package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
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
import sample.dao.ContactsDAO;
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

    public ComboBox<Integer> getContactIdCB() {
        return contactIdCB;
    }

    public void setContactIdCB() {
        contactIdCB.getItems().addAll(ContactsDAO.getAllContactIds());
        System.out.println("Combo Box set!");
    }

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
    void addAction(ActionEvent event) throws SQLException {
        String title = titleTxt.getText();
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        String type = typeTxt.getText();
        LocalDateTime start = LocalDateTime.parse(startTxt.getText());
        LocalDateTime end = LocalDateTime.parse(endTxt.getText());
        int customerId = Integer.parseInt(customerIdTxt.getText());
        int userId = Integer.parseInt(userIdTxt.getText());
        int contactId = contactIdCB.getValue();

        Appointments parsedAppointment = new Appointments(title, description, location, type, start, end, customerId, userId, contactId);
        AppointmentsDAO.addAppointment(parsedAppointment);
        appointmentsTableView.getItems().clear();
        if (allAppointmentBtt.isSelected()) {
            AppointmentsDAO.setAllAppointments();
            appointmentsTableView.setItems(AppointmentsDAO.getAllAppointments());
            appointmentsTableView.refresh();
        } else if (byMonthBtt.isSelected()){
            AppointmentsDAO.setByMonthAppointments();
            appointmentsTableView.setItems(AppointmentsDAO.getByMonthAppointments());
            appointmentsTableView.refresh();
        } else {
            AppointmentsDAO.setByWeekAppointments();
            appointmentsTableView.setItems(AppointmentsDAO.getByWeekAppointments());
            appointmentsTableView.refresh();
        }
    }

    @FXML
    void deleteAction(ActionEvent event) throws SQLException {
        Appointments selectedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();

        AppointmentsDAO.deleteAppointment(selectedAppointment);
        appointmentsTableView.getItems().clear();
        if (allAppointmentBtt.isSelected()) {
            AppointmentsDAO.setAllAppointments();
            appointmentsTableView.setItems(AppointmentsDAO.getAllAppointments());
            appointmentsTableView.refresh();
        } else if (byMonthBtt.isSelected()){
            AppointmentsDAO.setByMonthAppointments();
            appointmentsTableView.setItems(AppointmentsDAO.getByMonthAppointments());
            appointmentsTableView.refresh();
        } else {
            AppointmentsDAO.setByWeekAppointments();
            appointmentsTableView.setItems(AppointmentsDAO.getByWeekAppointments());
            appointmentsTableView.refresh();
        }
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
    void updateAction(ActionEvent event) throws SQLException{
        int appointmentId = Integer.parseInt(appointmentIdTxt.getText());
        String title = titleTxt.getText();
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        String type = typeTxt.getText();
        LocalDateTime start = LocalDateTime.parse(startTxt.getText());
        LocalDateTime end = LocalDateTime.parse(endTxt.getText());
        int customerId = Integer.parseInt(customerIdTxt.getText());
        int userId = Integer.parseInt(userIdTxt.getText());
        int contactId = contactIdCB.getValue();

        Appointments parsedAppointment = new Appointments(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);

        /*for(Appointments chosenAppointment : AppointmentsDAO.getAllAppointments()){
            if(parsedAppointment.getAppointmentId() == chosenAppointment.getAppointmentId()) {
                chosenAppointment.setTitle(parsedAppointment.getTitle());
                chosenAppointment.setDescription(parsedAppointment.getDescription());
                chosenAppointment.setLocation(parsedAppointment.getLocation());
                chosenAppointment.setType(parsedAppointment.getType());
                chosenAppointment.setStart(parsedAppointment.getStart());
                chosenAppointment.setEnd(parsedAppointment.getEnd());
                chosenAppointment.setCustomerId(parsedAppointment.getCustomerId());
                chosenAppointment.setUserId(parsedAppointment.getUserId());
                chosenAppointment.setContactId(parsedAppointment.getContactId());
            }
        }*/

        AppointmentsDAO.updateAppointment(parsedAppointment);
        appointmentsTableView.getItems().clear();
        if (allAppointmentBtt.isSelected()) {
            AppointmentsDAO.setAllAppointments();
            appointmentsTableView.setItems(AppointmentsDAO.getAllAppointments());
            appointmentsTableView.refresh();
        } else if (byMonthBtt.isSelected()){
            AppointmentsDAO.setByMonthAppointments();
            appointmentsTableView.setItems(AppointmentsDAO.getByMonthAppointments());
            appointmentsTableView.refresh();
        } else {
            AppointmentsDAO.setByWeekAppointments();
            appointmentsTableView.setItems(AppointmentsDAO.getByWeekAppointments());
            appointmentsTableView.refresh();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            AppointmentsDAO.setAllAppointments();
            ContactsDAO.setAllContactIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        setContactIdCB();
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

        appointmentsTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                appointmentIdTxt.setText(String.valueOf(newSelection.getAppointmentId()));
                titleTxt.setText(String.valueOf(newSelection.getTitle()));
                descriptionTxt.setText(String.valueOf(newSelection.getDescription()));
                locationTxt.setText(String.valueOf(newSelection.getLocation()));
                typeTxt.setText(String.valueOf(newSelection.getType()));
                startTxt.setText(String.valueOf(newSelection.getStart()));
                endTxt.setText(String.valueOf(newSelection.getEnd()));
                customerIdTxt.setText(String.valueOf(newSelection.getCustomerId()));
                userIdTxt.setText(String.valueOf(newSelection.getUserId()));
                contactIdCB.setValue(newSelection.getContactId());
            }
            else{
                appointmentIdTxt.clear();
                titleTxt.clear();
                descriptionTxt.clear();
                locationTxt.clear();
                typeTxt.clear();
                startTxt.clear();
                endTxt.clear();
                customerIdTxt.clear();
                userIdTxt.clear();
                contactIdCB.setValue(null);
            }
        });
    }


}
