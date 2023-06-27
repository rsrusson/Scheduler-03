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
import sample.dao.ContactsDAO;
import sample.model.Appointments;
import sample.model.Contacts;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Controller class for managing appointments.
 * Implements the Initializable interface to initialize the screen.
 */
public class AppointmentCont implements Initializable {

    private Contacts selectedContact;

    @FXML
    private TableColumn<Appointments, Integer> appointmentIdCol;

    @FXML
    private TableView<Appointments> appointmentsTableView;

    @FXML
    private TextField appointmentIdTxt;

    @FXML
    private ComboBox<Contacts> contactCB;

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
    private TableColumn<Appointments, ZonedDateTime> endCol;

    @FXML
    private TextField endTxt;

    @FXML
    private TableColumn<Appointments, String> locationCol;

    @FXML
    private TextField locationTxt;

    @FXML
    private TableColumn<Appointments, ZonedDateTime> startCol;

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

    /**
     * Displays an information alert.
     *
     * @param title        the title of the alert
     * @param headerText   the header text of the alert
     * @param contentText  the content text of the alert
     */
    static void alertInfo(String title, String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /**
     * Sets the contact combo box with all available contacts.
     */
    public void setContactCB() {
        contactCB.setItems(ContactsDAO.getAllContacts());
    }

    /**
     * Event handler for the contact combo box selection.
     *
     * @param event  the ActionEvent triggered by the combo box selection
     */
    @FXML
    void contactCBAction(ActionEvent event) {
        for (Contacts thisContact : ContactsDAO.getAllContacts()) {
            if (thisContact.equals(contactCB.getSelectionModel().getSelectedItem()));
                selectedContact = thisContact;
        }
    }

    /**
     * Event handler for the "All Appointments" radio button.
     * Retrieves and displays all appointments.
     *
     * @param event  the ActionEvent triggered by selecting the radio button
     */
    @FXML
    void allAppointmentAction(ActionEvent event) throws SQLException {
        appointmentsTableView.getItems().clear();
        if (AppointmentsDAO.getAllAppointments().isEmpty())
            AppointmentsDAO.setAllAppointments();
        appointmentsTableView.setItems(AppointmentsDAO.getAllAppointments());
        appointmentsTableView.refresh();
    }

    /**
     * Event handler for the "By Month" radio button.
     * Retrieves and displays appointments for the current month.
     *
     * @param event  the ActionEvent triggered by selecting the radio button
     */
    @FXML
    void byMonthAction(ActionEvent event) throws SQLException {
        appointmentsTableView.getItems().clear();
        AppointmentsDAO.setByMonthAppointments();
        appointmentsTableView.setItems(AppointmentsDAO.getByMonthAppointments());
        appointmentsTableView.refresh();
    }

    /**
     * Event handler for the "By Week" radio button.
     * Retrieves and displays appointments for the current week.
     *
     * @param event  the ActionEvent triggered by selecting the radio button
     */
    @FXML
    void byWeekAction(ActionEvent event) throws SQLException {
        appointmentsTableView.getItems().clear();
        AppointmentsDAO.setByWeekAppointments();
        appointmentsTableView.setItems(AppointmentsDAO.getByWeekAppointments());
        appointmentsTableView.refresh();
    }

    /**
     * Event handler for the "Add" button.
     * Adds a new appointment.
     *
     * @param event  the ActionEvent triggered by clicking the button
     */
    @FXML
    void addAction(ActionEvent event) throws SQLException {
        ZoneId eastId = ZoneId.of("America/New_York");
        ZoneId localId = ZonedDateTime.now().getZone();

        String title = titleTxt.getText();
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        String type = typeTxt.getText();
        LocalDateTime startLDT = LocalDateTime.parse(startTxt.getText());
        LocalDateTime endLDT = LocalDateTime.parse(endTxt.getText());
        ZonedDateTime start = startLDT.atZone(localId);
        ZonedDateTime end = endLDT.atZone(localId);
        int customerId = Integer.parseInt(customerIdTxt.getText());
        int userId = Integer.parseInt(userIdTxt.getText());
        int contactId = selectedContact.getContactId();

        if (start.withZoneSameInstant(eastId).getHour() < 8 || start.withZoneSameInstant(eastId).getHour() > 22 || end.withZoneSameInstant(eastId).getHour() < 8 || end.withZoneSameInstant(eastId).getHour() > 22) {
            alertInfo("Error", "Business hours are between 0800 and 2200", "Please schedule an appointment within business hours");
            return;
        }

        if (start.isAfter(end)){
            alertInfo("Error", "Start time is after the end time.", "Please enter a start time before the end time");
            return;
        }

        for (Appointments thisAppointment : AppointmentsDAO.getAllAppointments()){
            if (thisAppointment.getCustomerId() == customerId){
                ZonedDateTime existingStart = thisAppointment.getStart();
                ZonedDateTime existingEnd = thisAppointment.getEnd();

                if (start.isBefore(existingEnd) && end.isAfter(existingStart)){
                    alertInfo("Error", "This appointment overlaps for this customer", "Please input a non overlapping time for this appointment.");
                    return;
                }
            }
        }

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

    /**
     * Event handler for the "Delete" button.
     * Deletes the selected appointment.
     *
     * @param event  the ActionEvent triggered by clicking the button
     */
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

    /**
     * Event handler for the "Menu" button.
     * Navigates back to the main menu.
     *
     * @param event  the ActionEvent triggered by clicking the button
     */
    @FXML
    void menuAction(ActionEvent event) throws IOException {
        appointmentsTableView.getItems().clear();
        contactCB.getItems().removeAll(ContactsDAO.getAllContacts());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/menu.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) menuBtt.getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.show();
    }

    /**
     * Event handler for the "Update" button.
     * Updates an existing appointment.
     *
     * @param event  the ActionEvent triggered by clicking the button
     */
    @FXML
    void updateAction(ActionEvent event) throws SQLException{
        ZoneId eastId = ZoneId.of("America/New_York");
        ZoneId localId = ZonedDateTime.now().getZone();

        int appointmentId = Integer.parseInt(appointmentIdTxt.getText());
        String title = titleTxt.getText();
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        String type = typeTxt.getText();
        LocalDateTime startLDT = LocalDateTime.parse(startTxt.getText());
        LocalDateTime endLDT = LocalDateTime.parse(endTxt.getText());
        ZonedDateTime start = startLDT.atZone(localId);
        ZonedDateTime end = endLDT.atZone(localId);
        int customerId = Integer.parseInt(customerIdTxt.getText());
        int userId = Integer.parseInt(userIdTxt.getText());
        int contactId = selectedContact.getContactId();


        if (start.withZoneSameInstant(eastId).getHour() < 8 || start.withZoneSameInstant(eastId).getHour() > 22 || end.withZoneSameInstant(eastId).getHour() < 8 || end.withZoneSameInstant(eastId).getHour() > 22) {
            alertInfo("Error", "Business hours are between 0800 and 2200 Eastern Standard Time", "Please schedule an appointment within business hours");
            return;
        }

        if (start.isAfter(end)){
            alertInfo("Error", "Start time is after the end time.", "Please enter a start time before the end time");
            return;
        }

        for (Appointments thisAppointment : AppointmentsDAO.getAllAppointments()){
            if (thisAppointment.getCustomerId() == customerId){
                ZonedDateTime existingStart = thisAppointment.getStart();
                ZonedDateTime existingEnd = thisAppointment.getEnd();

                if ((start.isBefore(existingEnd) && end.isAfter(existingStart)) && thisAppointment.getAppointmentId() != appointmentId){
                    alertInfo("Error", "This appointment overlaps for this customer", "Please input a non overlapping time for this appointment.");
                    return;
                }
            }
        }

        Appointments parsedAppointment = new Appointments(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);

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


    /**
     * Initializes the appointment management screen.
     * Retrieves and sets all available contacts and contact IDs.
     *
     * @param url             the location used to resolve relative paths
     * @param resourceBundle  the resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ContactsDAO.setAllContacts();
            ContactsDAO.setAllContactIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        appointmentsTableView.getItems().clear();
        setContactCB();
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
                startTxt.setText(String.valueOf(newSelection.getStart().toLocalDateTime()));
                endTxt.setText(String.valueOf(newSelection.getEnd().toLocalDateTime()));
                customerIdTxt.setText(String.valueOf(newSelection.getCustomerId()));
                userIdTxt.setText(String.valueOf(newSelection.getUserId()));
                for (Contacts thisContact : ContactsDAO.getAllContacts()){
                    if (thisContact.getContactId() == newSelection.getContactId()){
                        selectedContact = thisContact;
                    }
                }
                contactCB.setValue(selectedContact);
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
                contactCB.setValue(null);
            }
        });
    }
}
