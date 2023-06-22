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
import java.time.Month;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

public class ReportCont implements Initializable {

    private ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();

    private ObservableList<Contacts> allContacts = FXCollections.observableArrayList();

    ObservableList<String> monthList = FXCollections.observableArrayList();

    private Set<String> typeSet = new HashSet<>();

    @FXML
    private TableView<Appointments> allApptTV;

    @FXML
    private TableColumn<Appointments, Integer> appointmentIdCol;

    @FXML
    private TableColumn<Appointments, Integer> contactIdCol;

    @FXML
    private TableColumn<Appointments, Integer> customerIdCol;

    @FXML
    private TableColumn<Appointments, String> descriptionCol;

    @FXML
    private TableColumn<Appointments, LocalDateTime> endCol;

    @FXML
    private Button menuBtt;

    @FXML
    private ComboBox<String> monthCB;

    @FXML
    private TextField monthTxt;

    @FXML
    private TableColumn<Appointments, LocalDateTime> startCol;

    @FXML
    private TableColumn<Appointments, String> titleCol;

    @FXML
    private ComboBox<String> typeCB;

    @FXML
    private TableColumn<Appointments, String> typeCol;

    @FXML
    private TextField typeTxt;

    @FXML
    private ComboBox<Contacts> contactCB;

    @FXML
    void contactCBAction(ActionEvent event) {

    }

    @FXML
    void typeCBAction(ActionEvent event) {

    }

    @FXML
    void monthCBAction(ActionEvent event) {

    }

    @FXML
    void byContactBtt(ActionEvent event) {

    }

    @FXML
    void perTypeBtt(ActionEvent event) {

    }

    @FXML
    void perMonthBtt(ActionEvent event) {

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            AppointmentsDAO.setAllAppointments();
            allAppointments = AppointmentsDAO.getAllAppointments();
            ContactsDAO.setAllContacts();
            allContacts = ContactsDAO.getAllContacts();
        } catch (SQLException throwables) {
            alertInfo("Error", "SQL Exception Error", "Please ensure proper data is input");
        }

        for (Month month : Month.values()) {
            String monthName = month.toString();
            monthList.add(monthName);
        }

        for (Appointments appointments : allAppointments){
            typeSet.add(appointments.getType().toUpperCase(Locale.ROOT));
        }

        monthCB.getItems().addAll(monthList);

        contactCB.getItems().addAll(allContacts);

        typeCB.getItems().addAll(typeSet);

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
