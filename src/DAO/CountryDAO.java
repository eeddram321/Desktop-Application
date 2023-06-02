package DAO;

import Model.CountryModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *Country DAO Class
 */
public class CountryDAO extends CountryModel {

    public CountryDAO(int countryID, String countryName) {
        super(countryID, countryName);
    }
    /**
     *Gets Country from the data base
     */
    public static CountryModel getCountry(int divisionID) throws SQLException{
        ObservableList<CountryModel> countryOL = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM client_schedule.countries tabA\n" +
                "INNER JOIN client_schedule.first_level_divisions tabB\n" +
                "ON tabA.Country_ID = tabB.Country_ID\n" +
                "WHERE tabB.Division_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlStatement);
        ps.setInt(1, divisionID);
        ResultSet rs = ps.executeQuery();
        CountryModel country = null;
        while(rs.next()){
            int countryID = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
             country = new CountryDAO(countryID, countryName);
            countryOL.add(country);
        }
        return country;
    }
    /**
     *Allows user to get all the countries from the data base
     */
    public static ObservableList<CountryModel> getAllCountries() throws SQLException{
        ObservableList<CountryModel> allCountries = FXCollections.observableArrayList();
        String sqlStatement = "SELECT Country_ID, Country FROM countries";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
          int countryID = rs.getInt("Country_ID");
          String countryName = rs.getString("Country");
          CountryModel country = new CountryModel(countryID, countryName);
          allCountries.add(country);
        }
        return allCountries;
    }
}
