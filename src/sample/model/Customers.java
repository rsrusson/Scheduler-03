package sample.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Customers {

    private int customerId;
    private String customerName;
    private String address;
    private String postalCodee;
    private String phone;
    private int divisionId;

    public Customers(int customerId, String customerName, String address, String postalCodee, String phone, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCodee = postalCodee;
        this.phone = phone;
        this.divisionId = divisionId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCodee() {
        return postalCodee;
    }

    public void setPostalCodee(String postalCodee) {
        this.postalCodee = postalCodee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
}
