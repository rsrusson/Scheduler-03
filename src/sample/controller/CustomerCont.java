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
import sample.dao.CountriesDAO;
import sample.dao.CustomersDAO;
import sample.dao.DivisionsDAO;
import sample.model.Appointments;
import sample.model.Customers;
import sample.model.Divisions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class CustomerCont implements Initializable{

    private Integer selectedDivisionId;

    private Integer selectedCountryId;

    private ObservableList<Integer> emptyIdList = null;

    private Map<Integer, Integer> divisionCountryPair = new HashMap();

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
    private ComboBox<Integer> divisionIdCB;

    @FXML
    private TableColumn<Customers, Integer> divisionIdCol;

    static void alertInfo(String title, String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public int getCountryIdCB() {
        return countryIdCB.getSelectionModel().getSelectedItem();
    }

    public boolean divisionCountryCheck(Customers checkingCustomer){
        int divisionId = checkingCustomer.getDivisionId();
        int countryId = checkingCustomer.getCountryId();

        for(Divisions division : DivisionsDAO.getAllDivisions()) {
            if (divisionId == division.getDivisionId() && countryId == division.getCountryId()){
                return true;
            }
        }
        //alertInfo("Warning!", "Division ID and Country ID do not match.", "Please select appropriate Country Id and Division ID");
        return false;
    }

    public void setCountryIdCB() {
        countryIdCB.setItems(CountriesDAO.getAllCountryIds());
    }

    public int getDivisionIdCB() {
        return divisionIdCB.getSelectionModel().getSelectedIndex();
    }

    public void setDivisionIdCB() {
        divisionIdCB.setItems(DivisionsDAO.getAllDivisonIds());
    }

    @FXML
    void addAction(ActionEvent event) throws ClassCastException {
        try {
                String name = nameTxt.getText();
                String address = addressTxt.getText();
                String postalCode = postalTxt.getText();
                String phone = phoneTxt.getText();
                Integer divisionId = selectedDivisionId;
                Integer countryId = selectedCountryId;
                Customers parsedCustomer = new Customers(name, address, postalCode, phone, divisionId, countryId);


            try {
                for (Divisions div : DivisionsDAO.getAllDivisions()) {
                    if (div.getDivisionId() == parsedCustomer.getDivisionId() && div.getCountryId() == parsedCustomer.getCountryId()) {
                        try {
                            CustomersDAO.addCustomer(parsedCustomer);
                            CustomersDAO.setAllCustomers();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        customerTV.getItems().clear();
                        customerTV.refresh();
                    }
                }
            }catch (ClassCastException c){
                alertInfo("Warning!", "Improper data entry", "Please enter proper data for a new customer. Note that not all divisions and countries match up");
            }
        }catch (ClassCastException c) {
            alertInfo("Warning", "Improper data for a customer", "Please enter the appropriate data");
        }
    }

    @FXML
    void deleteAction(ActionEvent event) throws SQLException {
        Customers selectedCustomer = customerTV.getSelectionModel().getSelectedItem();

        CustomersDAO.deleteCustomer(selectedCustomer);
        customerTV.getItems().clear();
        CustomersDAO.setAllCustomers();
        customerTV.setItems(CustomersDAO.getAllCustomers());
        customerTV.refresh();
    }

    @FXML
    void menuAction(ActionEvent event) throws IOException {
        customerTV.getItems().clear();
        countryIdCB.getItems().removeAll(CountriesDAO.getAllCountryIds());
        divisionIdCB.getItems().removeAll(DivisionsDAO.getAllDivisonIds());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/menu.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) menuBtt.getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.show();
    }

    @FXML
    void updateAction(ActionEvent event) throws SQLException{
        int customerId = Integer.parseInt(idTxt.getText());
        String name = nameTxt.getText();
        String address = addressTxt.getText();
        String postalCode = postalTxt.getText();
        String phone = phoneTxt.getText();
        int divisionId = divisionIdCB.getValue();
        int countryId = countryIdCB.getValue();

        Customers parsedCustomer = new Customers(customerId, name, address, postalCode, phone, divisionId, countryId);
        divisionCountryCheck(parsedCustomer);
        CustomersDAO.updateCustomer(parsedCustomer);
        customerTV.getItems().clear();
        CustomersDAO.setAllCustomers();
        customerTV.setItems(CustomersDAO.getAllCustomers());
        customerTV.refresh();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            CustomersDAO.setAllCustomers();
            DivisionsDAO.setAllDivisonIds();
            DivisionsDAO.setAllDivisions();
            CountriesDAO.setAllCountryIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        setCountryIdCB();
        setDivisionIdCB();

        countryIdCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedCountryId = newValue;
            countryIdCB.setValue(newValue);
        });

        divisionIdCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedDivisionId = newValue;
            divisionIdCB.setValue(newValue);
        });

        customerTV.setItems(CustomersDAO.getAllCustomers());
        idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        countryIdCol.setCellValueFactory(new PropertyValueFactory<>("countryId"));
        divisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

        customerTV.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idTxt.setText(String.valueOf(newSelection.getCustomerId()));
                nameTxt.setText(String.valueOf(newSelection.getCustomerName()));
                addressTxt.setText(String.valueOf(newSelection.getAddress()));
                postalTxt.setText(String.valueOf(newSelection.getPostalCode()));
                phoneTxt.setText(String.valueOf(newSelection.getPhone()));
                countryIdCB.setValue(newSelection.getCountryId());
                divisionIdCB.setValue(newSelection.getDivisionId());
                selectedCountryId = newSelection.getCountryId();
                selectedDivisionId = newSelection.getDivisionId();
            }
            else{
                idTxt.clear();
                nameTxt.clear();
                addressTxt.clear();
                postalTxt.clear();
                phoneTxt.clear();
                countryIdCB.setValue(null);
                divisionIdCB.setValue(null);
                selectedCountryId = null;
                selectedDivisionId = null;
            }
        });
    }
}
