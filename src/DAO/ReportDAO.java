package DAO;

import Model.AppointmentModel;
import Model.ReportModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ReportDAO extends AppointmentModel {

    public ReportDAO(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, Timestamp appointmentStart, Timestamp appointmentEnd, int appointmentCustomerID, int appointmentUserID, int appointmentContactID) {
        super(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, appointmentStart, appointmentEnd, appointmentCustomerID, appointmentUserID, appointmentContactID);
    }
    /**
     *Allows user to get the Country and it's count from the data base
     */
    public static ObservableList<ReportModel> getCountry() throws SQLException {
        ObservableList<ReportModel> countryOL = FXCollections.observableArrayList();
        String sqlStatement = "SELECT countries.Country, count(*) AS countryCount FROM customers INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID" +
                "INNER JOIN countries on countries.Country_ID = first_level_divisions.Country_ID WHERE customers.Division_ID = first_level_divisions.Divison_ID group by" +
                "by first_level_divisions.Country_ID order by count(*) desc ";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet rs =ps.executeQuery();
        while(rs.next()){
            String countryName = rs.getString("Country");
            int countryCount = rs.getInt("countryCount");
            ReportModel report = new ReportModel(countryName, countryCount);
            countryOL.add(report);

        }
        return countryOL;
    }

    public static int getMonthType(String month, String type) throws SQLException {
        int count = 0;
        String sqlStatement = " SELECT count(*) AS cnt FROM client_schedule.appointments WHERE Type = ? AND monthname(start) = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlStatement);
        ps.setString(1, type);
        ps.setString(2, month);
        ResultSet rs =ps.executeQuery();
        while(rs.next()){
            count = rs.getInt("cnt");

        }
        return count;
    }
}
