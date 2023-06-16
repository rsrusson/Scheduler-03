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
import sample.dao.CountriesDAO;
import sample.dao.CustomersDAO;
import sample.dao.DivisionsDAO;
import sample.model.Customers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerCont implements Initializable {

    @FXML
    private TableColumn<Customers, String> addressCol;

    @FXML
    private TextField addressTxt;

    @FXML
    private ComboBox<Integer> countryIdCB;

    @FXML
    private TableColumn<Customers, Integer> countryIdCol;

    @FXML
    private TableView<Customers> customerTV;

    @FXML
    private TableColumn<Customers, Integer> idCol;

    @FXML
    private TextField idTxt;

    @FXML
    private Button menuBtt;

    @FXML
    private TableColumn<Customers, String> nameCol;

    @FXML
    private TextField nameTxt;

    @FXML
    private TableColumn<Customers, String> phoneCol;

    @FXML
    private TextField phoneTxt;

    @FXML
    private TableColumn<Customers, String> postalCol;

    @FXML
    private TextField postalTxt;

    @FXML
    private ComboBox<Customers> divisionIdCB;

    @FXML
    private TableColumn<Customers, Integer> divisionIdCol;

    public ComboBox<Integer> getCountryIdCB() {
        return countryIdCB;
    }

    public void setCountryIdCB() {
        countryIdCB.getItems().addAll(CountriesDAO.getAllCountryIds());
    }

    public ComboBox<Customers> getDivisionIdCB() {
        return divisionIdCB;
    }

    public void setDivisionIdCB() {
        divisionIdCB.getItems().addAll(DivisionsDAO.getAllDivisonIds());
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
            CustomersDAO.setAllCustomers();
            DivisionsDAO.setAllDivisonIds();
            CountriesDAO.setAllCountryIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        setCountryIdCB();
        setDivisionIdCB();

        customerTV.setItems(CustomersDAO.getAllCustomers());
        idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        countryIdCol.setCellValueFactory(new PropertyValueFactory<>("countryId"));
        divisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
    }
}
