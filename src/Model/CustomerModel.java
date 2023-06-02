package Model;

import DAO.CountryDAO;
import DAO.CustomerDAO;

import java.sql.SQLException;

/**
 * Customer class
 * @author Eduardo Ramirez
 */

public class CustomerModel {
    /**
     * customer id
     */
    private int customerID;
    /**
     * customer name
     */
    private String customerName;
    /**
     * customer address
     */
    private String customerAddress;
    /**
     * customer postal code
     */
    private String customerPostalCode;
    /**
     * customer phone
     */
    private String customerPhone;
    /**
     * division id
     */
    private int divisionID;
    /**
     * division name
     */
    private String divisionName;
    /**
     * country
     */
    private String country;
    /**
     * country id
     */
    private int countryID;

    /**
     * Constructor for customer model class
     * @param customerID
     * @param customerName
     * @param customerAddress
     * @param customerPostalCode
     * @param customerPhone
     * @param divisionName
     * @param divisionID
     */
    public CustomerModel(int customerID, String customerName, String customerAddress, String customerPostalCode, String customerPhone, String divisionName, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
        this.divisionName = divisionName;
        this.divisionID = divisionID;

    }

    /**
     *
     * @param customerID
     * @param customerName
     * @param customerAddress
     * @param customerPostalCode
     * @param customerPhone
     * @param divisionID
     */
    public CustomerModel(int customerID, String customerName, String customerAddress, String customerPostalCode, String customerPhone, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
        this.divisionID = divisionID;

    }

    /**
     *
     * @param customerName
     * @param CustomerID
     */
    public CustomerModel(String customerName, int CustomerID) {
        this.customerName = customerName;
        this.customerID = CustomerID;
    }

    /**
     * getter for Division name
     * @return
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * setter for Division name
     * @param divisionName
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }
    /**
     *getter for country
     */
    public String getCountry() throws SQLException {
        CountryModel country = CountryDAO.getCountry(this.divisionID);

        return country.getCountryName();

    }

    /**
     * Setter for country
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * getter for country id
     * @return
     * @throws SQLException
     */
    public int getCountryID() throws SQLException {
        CountryModel country = CountryDAO.getCountry(this.divisionID);

        return country.getCountryID();    }

    /**
     * Setter for country id
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * getter customerID
     * @return
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * setter Customer ID
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * getter CustomerName
     * @return
     */
    public String getCustomerName() {
        return customerName;
    }
    /**
     * setter Customer Name
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * getter Customer Address
     * @return
     */
    public String getCustomerAddress() {
        return customerAddress;
    }
    /**
     * setter customer Address
     * @param customerAddress
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * getter Customer Postal Code
     * @return
     */
    public String getCustomerPostalCode() {
        return customerPostalCode;
    }
    /**
     * setter customerPostal Code
     * @param customerPostalCode
     */
    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    /**
     * getter Customer Phone Number
     * @return
     */
    public String getCustomerPhone() {
        return customerPhone;
    }
    /**
     * setter Customer Phone Number
     * @param customerPhone
     */
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    /**
     * getter Division Id
     * @return
     */
    public int getDivisionID() {
        return divisionID;
    }
    /**
     * setter division ID
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
    public String toString() {
       return "[" + customerID + "]" ; //customerName;
    }

}
