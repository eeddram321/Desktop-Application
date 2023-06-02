package DAO;

import Model.CountryModel;
import Model.CustomerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.JDBC;

import java.sql.*;
/**
 *Customer DAO Class
 */
public class CustomerDAO {
    /**
     * @param customerID
     * @param customerName
     * @param customerAddress
     * @param customerPostalCode
     * @param customerPhone
     * @param divisionID
     */
    public CustomerDAO(int customerID, String customerName, String customerAddress, String customerPostalCode, String customerPhone, int divisionID) {
    }

    public CustomerDAO(int customerID, String customerName, String customerAddress, String customerPostalCode, String customerPhone, int divisionName, String country) {
    }
    /**
     *Allows user to get all customers information from the data base
     */
    public static ObservableList<CustomerModel> getAllCustomers() throws SQLException {
        ObservableList<CustomerModel> customerModelObservableList = FXCollections.observableArrayList();
        String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, customers.Division_ID, first_level_divisions.Division FROM client_schedule.customers INNER JOIN client_schedule.first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String customerAddress = rs.getString(("Address"));
            String customerPostalCode = rs.getString("Postal_Code");
            String customerPhone = rs.getString("Phone");
            String divisionName = rs.getString("Division");
            int divisionID = rs.getInt("Division_ID");
            System.out.println(customerID);
            CustomerModel customer = new CustomerModel(customerID, customerName, customerAddress, customerPostalCode, customerPhone, divisionName, divisionID);
            customerModelObservableList.add(customer);

        }
        return customerModelObservableList;
    }
    /**
     *Allows user to get a specific division from the data base
     */
    public static ObservableList<String > getSpecificDiv(String division) throws SQLException{
        ObservableList<String> sDivision = FXCollections.observableArrayList();
        String sqlStatement = "SELECT countries.Country, countries.Country_ID,  division.Division_ID, division.Division FROM countries RIGHT OUTER JOIN " +
                "first_level_divisions AS division ON countries.Country_ID = division.Country_ID WHERE countries.Country = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            sDivision.add(rs.getString("Division"));
        }
        return sDivision;
    }
    /**
     *Allows user to update customer information from the data base
     */
    public static void saveCustomer(CustomerModel customer) throws SQLException {
        String sqlStatement = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlStatement);

        ps.setString(1, customer.getCustomerName());
        ps.setString(2, customer.getCustomerAddress());
        ps.setString(3, customer.getCustomerPostalCode());
        ps.setString(4, customer.getCustomerPhone());
        ps.setInt(5, customer.getDivisionID() );
        ps.setInt(6, customer.getCustomerID());

        ps.executeUpdate();
    }

    /**
     *Allows user to get unique division from the data base
     */
    public static int getUniqueDiv(int divisionID) throws SQLException {
        int division_ID = 0;
        String sqlStatemnt = ("SELECT Division, Division_ID FROM " +
                "first_level_divisions WHERE Division = ?");
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlStatemnt);
        ps.setInt(1, divisionID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            division_ID =rs.getInt("Division_ID");
        }
        return division_ID;

    }
    /**
     *Allows user to delete a customer and all his information from the data base
     */
    public  static int deleteCustomerAppt(int customerID) throws SQLException {
        String sqlStatment = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlStatment);
        ps.setInt(1, customerID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    /**
     *Allows user to get a Customer id from the data base
     */
    public static  ObservableList<String> getCustomersID() throws SQLException{
        ObservableList<String> custID = FXCollections.observableArrayList();
        String Customer_ID = "SELECT DISTINCT Customer_ID FROM customers";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(Customer_ID);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            custID.add(rs.getString("Customer_ID"));
        }
        return  custID;
    }
    /**
     *Allows user to add a customer into the data base
     */
    public  static int insertCustomer(String customerName, String customerAddress, String customerPostalCode, String customerPhone, int divisionID) throws SQLException{
        //watch jdbc webinar
        String sqlStatement = "INSERT INTO customers  (Customer_Name , Address, Postal_Code, Phone, Division_ID  ) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlStatement);

        ps.setString(1, customerName);
        ps.setString(2, customerAddress);
        ps.setString(3, customerPhone);
        ps.setString(4, customerPostalCode);
        ps.setInt(5, divisionID);
       // ps.setInt(6, customer.getCustomerID());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    /**
     *Allows user to get all customers by country
     */
    public static ObservableList<CustomerModel> getAllCustomersByCountry(CountryModel country) throws SQLException {
        ObservableList<CustomerModel> customerModelObservableList = FXCollections.observableArrayList();
        String sql = "SELECT customers.Division_ID, customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, first_level_divisions.Division" +
        " FROM client_schedule.customers INNER JOIN client_schedule.first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID" +
       " WHERE first_level_divisions.Country_ID = ?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, country.getCountryID());
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String customerAddress = rs.getString(("Address"));
            String customerPostalCode = rs.getString("Postal_Code");
            String customerPhone = rs.getString("Phone");
            String divisionName = rs.getString("Division");
           int divisionID = rs.getInt("Division_ID");
            System.out.println(customerID);
            CustomerModel customer = new CustomerModel(customerID, customerName, customerAddress, customerPostalCode, customerPhone, divisionName, divisionID);
            customerModelObservableList.add(customer);

        }
        return customerModelObservableList;
    }

        }


