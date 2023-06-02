package DAO;

import Model.ContactModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *Contact DAO Class
 */
public class ContactDAO {
    /**
     *Gets all contacts from the data base
     */
    public static ObservableList<String > getAllContacts() throws SQLException{
        ObservableList<String> contactOL = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM contacts";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){

            contactOL.add(rs.getString("Contact_Name"));
        }
        return contactOL;
    }
    /**
     *Gets all contacts from the data base
     */
    public static ObservableList<ContactModel> getAlleditContacts() throws SQLException{
        ObservableList<ContactModel > contactOL = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM contacts";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            contactOL.add(new ContactModel(contactId, contactName));
        }
        return contactOL;
    }
    /**
     *Look up Contact ID from the data base
     */
    public static String lookupContactID(String contactName) throws SQLException {
        String sqlStatemtn = " SELECT Contact_ID FROM contacts WHERE Contact_Name = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlStatemtn);
        ps.setString(1, contactName);
        ResultSet rs = ps.executeQuery();
        String contactID = "";
        while (rs.next()) {
            contactID = rs.getString("Contact_ID");
        }
        return contactID;
    }
}
