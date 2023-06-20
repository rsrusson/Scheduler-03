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
import sample.dao.CountriesDAO;
import sample.dao.CustomersDAO;
import sample.dao.DivisionsDAO;
import sample.model.Customers;
import sample.model.Divisions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class CustomerCont implements Initializable {

    //private Integer selectedDivisionId;

    //private Integer selectedCountryId;

    private ObservableList<Divisions> allDivisions = DivisionsDAO.getAllDivisions();

    @FXML
    private TableColumn<Customers, String> addressCol;

    @FXML
    private TextField addressTxt;

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
    private ComboBox<Divisions> divisionCB = new ComboBox<Divisions>();

    @FXML
    private TableColumn<Customers, Integer> divisionIdCol;

    @FXML
    void divisionSelectionAction(ActionEvent event) {
        //divisionCB.setValue(divisionCB.getValue());
    }

    public void setAllDivisions() throws SQLException {
        DivisionsDAO.setAllDivisions();
        allDivisions = DivisionsDAO.getAllDivisions();
    }

    static void alertInfo(String title, String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /*public void divisionCountryCheck(Customers checkingCustomer){
        int divisionId = checkingCustomer.getDivisionId();
        int countryId = checkingCustomer.getCountryId();

        for(Divisions division : DivisionsDAO.getAllDivisions()) {
            if (divisionId == division.getDivisionId() && countryId == division.getCountryId()){
                return;
            }
        }
        //alertInfo("Warning!", "Division ID and Country ID do not match.", "Please select appropriate Country Id and Division ID");
    }*/

    public void setDivisionCB() {
        divisionCB.setItems(allDivisions);
    }

    @FXML
    void addAction(ActionEvent event) throws RuntimeException {
        try {
            setAllDivisions();
            setDivisionCB();

            String name = nameTxt.getText();
            String address = addressTxt.getText();
            String postalCode = postalTxt.getText();
            String phone = phoneTxt.getText();
            Divisions divisionObj = divisionCB.getSelectionModel().getSelectedItem();
            Integer divisionId = divisionObj.getDivisionId();
            System.out.println(divisionId);
            Divisions countryObj = divisionCB.getSelectionModel().getSelectedItem();
            Integer countryId = countryObj.getCountryId();
            System.out.println(countryId);
            Customers parsedCustomer = new Customers(name, address, postalCode, phone, divisionId, countryId);
            System.out.println("Object constructed");

            /*for (Divisions div : DivisionsDAO.getAllDivisions()) {
                if (div.getDivisionId().equals(parsedCustomer.getDivisionId()) && div.getCountryId().equals(parsedCustomer.getCountryId())) {
                    try {
                        customerTV.getItems().clear();
                        CustomersDAO.addCustomer(parsedCustomer);
                        CustomersDAO.setAllCustomers();
                        customerTV.setItems(CustomersDAO.getAllCustomers());
                        customerTV.refresh();
                    } catch (SQLException throwables) {
                        System.out.println("Exception Caught in small block.");
                        alertInfo("SQL Exception", "Input data does not match the correct data type", "Please use the correct units for data");
                    }
                    return;
                }
            }*/
            CustomersDAO.addCustomer(parsedCustomer);
            customerTV.getItems().clear();
            CustomersDAO.setAllCustomers();
            customerTV.setItems(CustomersDAO.getAllCustomers());
            customerTV.refresh();
        } catch (ClassCastException e) {
            System.out.println("Exception caught in big block A");
            alertInfo("Class Cast Exception", "Division ID and Country ID do not match", "Please enter a Country and Division ID that match");
        } catch (SQLException e){
            System.out.println("Exception caught in big block B");
            alertInfo("SQL Exception", "Input data does not match the correct data type", "Please use the correct units for data");
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
        divisionCB.getItems().removeAll(DivisionsDAO.getAllDivisions());

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
        Integer divisionId = divisionCB.getSelectionModel().getSelectedItem().getDivisionId();
        Integer countryId = divisionCB.getSelectionModel().getSelectedItem().getCountryId();

        Customers parsedCustomer = new Customers(customerId, name, address, postalCode, phone, divisionId, countryId);
        //divisionCountryCheck(parsedCustomer);
        CustomersDAO.updateCustomer(parsedCustomer);
        customerTV.getItems().clear();
        CustomersDAO.setAllCustomers();
        customerTV.setItems(CustomersDAO.getAllCustomers());
        customerTV.refresh();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Integer nullInteger = null;

        try {
            CustomersDAO.setAllCustomers();
            DivisionsDAO.setAllDivisonIds();
            DivisionsDAO.setAllDivisions();
            CountriesDAO.setAllCountryIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        setDivisionCB();

        /*countryIdCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedCountryId = newValue;
            countryIdCB.setValue(newValue);
        });

        divisionIdCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedDivisionId = newValue;
            divisionIdCB.setValue(newValue);
        });*/

        customerTV.setItems(CustomersDAO.getAllCustomers());
        idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        countryIdCol.setCellValueFactory(new PropertyValueFactory<>("countryId"));
        divisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

        try {
            customerTV.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    idTxt.setText(String.valueOf(newSelection.getCustomerId()));
                    nameTxt.setText(String.valueOf(newSelection.getCustomerName()));
                    addressTxt.setText(String.valueOf(newSelection.getAddress()));
                    postalTxt.setText(String.valueOf(newSelection.getPostalCode()));
                    phoneTxt.setText(String.valueOf(newSelection.getPhone()));
                    for (Divisions div : allDivisions){
                        if(newSelection.getDivisionId() == div.getDivisionId()){
                            divisionCB.setValue(div);
                        }
                    }
                    //selectedCountryId = newSelection.getCountryId();
                    //selectedDivisionId = newSelection.getDivisionId();
                }
            /*else{
                idTxt.clear();
                nameTxt.clear();
                addressTxt.clear();
                postalTxt.clear();
                phoneTxt.clear();
                countryIdCB.setValue(nullInteger);
                divisionIdCB.setValue(nullInteger);
                selectedCountryId = null;
                selectedDivisionId = null;
            }*/
            });
        }catch (ClassCastException e){
            System.out.println("Caught in Table View Listener");
        }
    }
}

