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
import javafx.scene.input.MouseEvent;
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
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomerCont implements Initializable {

    CustomerCont customerCont;

    private AtomicBoolean isUpdating = new AtomicBoolean(false);

    private Divisions selectedDivision;

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
    void divisionCBAction(ActionEvent event) {
        if (!isUpdating.get()){
            System.out.println("CBAction engaged after isUpdating check");
            isUpdating.set(true);
            for (Divisions div : allDivisions) {
                if (div.equals(divisionCB.getSelectionModel().getSelectedItem())) {
                    selectedDivision = div;
                }
            }
            isUpdating.set(false);
        }
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

    public void setDivisionCB() {
        divisionCB.getItems().addAll(allDivisions);
    }

    @FXML
    void addAction(ActionEvent event) throws RuntimeException {
        try {
            String name = nameTxt.getText();
            String address = addressTxt.getText();
            String postalCode = postalTxt.getText();
            String phone = phoneTxt.getText();
            System.out.println("Start of add method checks!");
            Integer divisionId = selectedDivision.getDivisionId();
            Integer countryId = selectedDivision.getCountryId();

            Customers parsedCustomer = new Customers(name, address, postalCode, phone, divisionId, countryId);
            System.out.println("Object constructed");

            CustomersDAO.addCustomer(parsedCustomer);
            customerTV.getItems().clear();
            CustomersDAO.setAllCustomers();
            customerTV.setItems(CustomersDAO.getAllCustomers());
            customerTV.refresh();
        } catch (ClassCastException e) {
            System.out.println("Class Cast Exception caught in add action");
            alertInfo("Class Cast Exception", "Division ID and Country ID do not match", "Please enter a Country and Division ID that match");
        } catch (SQLException e){
            System.out.println("SQL Exception caught in add action");
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
    void updateAction(ActionEvent event) throws RuntimeException{
        try {
            int customerId = Integer.parseInt(idTxt.getText());
            String name = nameTxt.getText();
            String address = addressTxt.getText();
            String postalCode = postalTxt.getText();
            String phone = phoneTxt.getText();
            Integer divisionId = selectedDivision.getDivisionId();
            Integer countryId = selectedDivision.getCountryId();

            Customers parsedCustomer = new Customers(customerId, name, address, postalCode, phone, divisionId, countryId);
            CustomersDAO.updateCustomer(parsedCustomer);
            customerTV.getItems().clear();
            CustomersDAO.setAllCustomers();
            customerTV.setItems(CustomersDAO.getAllCustomers());
            customerTV.refresh();
        } catch (ClassCastException e){
            alertInfo("Error", "Class Cast Exception", "Please input appropriate data");
        } catch (SQLException throwables){
            alertInfo("Error", "SQL Exception", "Please input appropriate data");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        isUpdating.set(false);

        try {
            CustomersDAO.setAllCustomers();
            DivisionsDAO.setAllDivisonIds();
            DivisionsDAO.setAllDivisions();
            CountriesDAO.setAllCountryIds();
            setAllDivisions();
            setDivisionCB();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        customerTV.setItems(CustomersDAO.getAllCustomers());
        idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        countryIdCol.setCellValueFactory(new PropertyValueFactory<>("countryId"));
        divisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

        try {
            customerTV.getSelectionModel().selectedItemProperty().addListener((obsTV, oldSelectionTV, newSelectionTV) -> {
                if (newSelectionTV != null && !isUpdating.get()) {
                    isUpdating.set(true);
                    idTxt.setText(String.valueOf(newSelectionTV.getCustomerId()));
                    nameTxt.setText(String.valueOf(newSelectionTV.getCustomerName()));
                    addressTxt.setText(String.valueOf(newSelectionTV.getAddress()));
                    postalTxt.setText(String.valueOf(newSelectionTV.getPostalCode()));
                    phoneTxt.setText(String.valueOf(newSelectionTV.getPhone()));
                    for (Divisions div : allDivisions) {
                        if (div.getDivisionId().equals(newSelectionTV.getDivisionId())) {
                            selectedDivision = div;
                            divisionCB.getSelectionModel().select(selectedDivision);
                        }
                    }
                    isUpdating.set(false);
                }
            });
        } catch (RuntimeException e){
            System.out.println("Caught in Table View Listener");
        }
    }
}

