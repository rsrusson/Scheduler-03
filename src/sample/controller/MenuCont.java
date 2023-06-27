package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import sample.dao.AppointmentsDAO;
import sample.model.Appointments;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Stack;

public class MenuCont implements Initializable {

    @FXML
    private Button appointmentBtt;

    @FXML
    private Button customerBtt;

    @FXML
    private Button reportBtt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            AppointmentsDAO.setAllAppointments();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        ZonedDateTime myZDT = ZonedDateTime.now();
        ZonedDateTime fifteenMinutesLater = myZDT.plusMinutes(15);
        ZoneId myZID = ZonedDateTime.now().getZone();
        ZoneId estZID = ZoneId.of("America/New_York");

        boolean closeAppointment = false;

        for (Appointments appointment : AppointmentsDAO.getAllAppointments()){
            if ((appointment.getStart().toLocalDateTime().isEqual(myZDT.toLocalDateTime()) ||
                    appointment.getStart().toLocalDateTime().isAfter(myZDT.toLocalDateTime())) &&
                    appointment.getStart().toLocalDateTime().isBefore(fifteenMinutesLater.toLocalDateTime())) {
                closeAppointment = true;
                alertInfo("Warning", "There is an appointment in 15 minutes or less", "ID: " + String.valueOf(appointment.getAppointmentId()) + " " + "Hour: " + String.valueOf(appointment.getStart().getHour()) + " " + "Minute: " + String.valueOf(appointment.getStart().getMinute()));
                break;
            }
        }
        if (!closeAppointment)
        alertInfo("Notice", "There are no upcoming appointments", "Enjoy your free time!");
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
    void appointmentAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/appointment.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) appointmentBtt.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void customerAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/customer.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) customerBtt.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void exitAction(ActionEvent event) {
        exitConfirmation("Warning!", "Exit initiated", "Are you sure you want to exit?");

    }

    @FXML
    void reportAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/report.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) reportBtt.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public Button getAppointmentBtt() {
        return appointmentBtt;
    }

    public void setAppointmentBtt(Button appointmentBtt) {
        this.appointmentBtt = appointmentBtt;
    }

    public Button getCustomerBtt() {
        return customerBtt;
    }

    public void setCustomerBtt(Button customerBtt) {
        this.customerBtt = customerBtt;
    }

    public Button getReportBtt() {
        return reportBtt;
    }

    public void setReportBtt(Button reportBtt) {
        this.reportBtt = reportBtt;
    }
}
