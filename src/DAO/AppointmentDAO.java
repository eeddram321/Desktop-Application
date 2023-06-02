package DAO;

import Model.AppointmentModel;
import Model.ContactModel;
import Model.CustomerModel;
import Model.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.JDBC;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

/**
 * Appointment DAO class allows user to extract data from the data base
 */
public class AppointmentDAO {
    /**
     *gets appointment information
     * Observable list for appointments in database
     * @return appointmentOL
     *
     */
    public static ObservableList<AppointmentModel> getAllAppointments() throws SQLException {
        ObservableList<AppointmentModel> appointmentOL = FXCollections.observableArrayList();
        String sql = "SELECT * FROM client_schedule.appointments";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            int appointmentID = rs.getInt("Appointment_ID");
            String appointmentTitle = rs.getString("Title");
            String appointmentDescription = rs.getString("Description");
            String appointmentLocation = rs.getString("Location");
            String appointmentType = rs.getString("Type");
            Timestamp appointmentStart = rs.getTimestamp("Start");
            Timestamp appointmentEnd = rs.getTimestamp("End");
            int appointmentCustomerID = rs.getInt("Customer_ID");
            int appointmentUserID = rs.getInt("User_ID");
            int appointmentContactID = rs.getInt("Contact_ID");
            AppointmentModel appointment = new AppointmentModel( appointmentID,  appointmentTitle,  appointmentDescription,
                     appointmentLocation,  appointmentType,  appointmentStart,  appointmentEnd, appointmentCustomerID, appointmentUserID,  appointmentContactID);
            appointmentOL.add(appointment);
        }
        return appointmentOL;
    }

    /**
     * Deletes appointment in the database
     * @param customerID
     *
     * @return
     * @throws SQLException
     */
    public  static int deleteCustomerAppt(int customerID) throws SQLException{
        String sqlStatment = "DELETE FROM appointments WHERE Customer_ID = ?";
        PreparedStatement rs = JDBC.getConnection().prepareStatement(sqlStatment);
        rs.setInt(1, customerID);
        int resultSet = rs.executeUpdate();
        return resultSet;
    }
    public  static int deleteAppt(int appointmentID) throws SQLException{
        String sqlStatment = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement rs = JDBC.getConnection().prepareStatement(sqlStatment);
        rs.setInt(1, appointmentID);
        int resultSet = rs.executeUpdate();
        return resultSet;
    }
    /**
     *Allows user to add appointments to the database
     */
    public static int addAppt(String apptTitle, String apptDescription, String location, String Type, LocalDateTime startTime, LocalDateTime endTime
    , int customerID, int  contactID, int UserID) throws SQLException {
        String sqlStatement = "INSERT INTO appointments ( Title, Description, Location, Type, Start, End, Customer_ID, Contact_ID, User_ID)" +
                "Values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlStatement);

        ps.setString(1, apptTitle);
        ps.setString(2, apptDescription);
        ps.setString(3, location);
        ps.setString(4, Type);
        ps.setTimestamp(5, Timestamp.valueOf(startTime));
        ps.setTimestamp(6, Timestamp.valueOf(endTime));
        ps.setInt(7, customerID);
        ps.setInt(8, contactID);
        ps.setInt(9, UserID);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected;
        }
    /**
     *Filters Appointments by date
     */
    public static ObservableList<AppointmentModel> filterAppointmentsByDate(LocalDateTime start) throws SQLException {
        //SELECT * FROM client_schedule.appointments WHERE DATE_FORMAT(Start, '%Y-%m-%d') = '2023-05-08';
        ObservableList<AppointmentModel> appointments = FXCollections.observableArrayList();
        System.out.println("appointments");
        String sqlStatement = "SELECT * FROM client_schedule.appointments WHERE DATE_FORMAT(Start, '%Y-%m-%d') = DATE_FORMAT(?, '%Y-%m-%d')";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlStatement);
        ps.setTimestamp(1, Timestamp.valueOf(start));
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            appointments.add(new AppointmentModel(rs.getTimestamp("Start").toLocalDateTime()));

        }
        return appointments;
    }
    /**
     *Allows user to get a specific customer appointment
     */
    public static ObservableList<AppointmentModel> getSpecificCustomerAppt(LocalDate apptStartDate, LocalDate ApptEndDate, int sCustomerID) throws SQLException{
        ObservableList<AppointmentModel> filterAppt = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM appointments as a LEFT OUTER JOIN contacts as c ON a.Contact_ID = c.Contact_ID WHERE datediff(a.Start, ?)" +
                "= 0 AND Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlStatement);
        ps.setString(1, apptStartDate.toString());
        ps.setString(2, ApptEndDate.toString());
        ps.setInt(3, sCustomerID);
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            int AppointmentID = rs.getInt("Appointment_ID");
            String  appointmentTitle = rs.getString("Title");
            String appointmentDescription = rs.getString("Description");
            String appointmentLocation = rs.getString("Location");
            String appointmentType = rs.getString("Type");
            Timestamp appointmentStart = rs.getTimestamp("Start");
            Timestamp appointmentEnd = rs.getTimestamp("End");
            int appointmentCustomerID = rs.getInt("Customer_ID");
            int appointmentUserID = rs.getInt("User_ID");
            int appointmentContactID = rs.getInt("Contact_ID");
            AppointmentModel new_Appt = new AppointmentModel(AppointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType,
            appointmentStart, appointmentEnd, appointmentCustomerID, appointmentUserID, appointmentContactID);
            filterAppt.add(new_Appt);
        }
       return filterAppt;
    }
    /**
     *Allows user to update appointments from the data base
     */
    public static void updateAppointment(AppointmentModel appointment) {
        try {
            String sqlStatement = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Contact_ID=?," +
                    " Customer_ID =?," +
                    "User_ID=?" +
                    " WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlStatement);

            ps.setString(1, appointment.getAppointmentTitle());
            ps.setString(2, appointment.getAppointmentDescription());
            ps.setString(3, appointment.getAppointmentLocation());
            ps.setString(4, appointment.getAppointmentType());
            ps.setTimestamp(5, appointment.getAppointmentStart());
            ps.setTimestamp(6, appointment.getAppointmentEnd());
            ps.setInt(7, appointment.getAppointmentContactID());
            ps.setInt(8, appointment.getAppointmentUserID());
            ps.setInt(9, appointment.getAppointmentCustomerID());
            ps.setInt(10, appointment.getAppointmentID());
            System.out.println(ps.toString());
            ps.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    /**
     *Gets Type from data base
     */
    public static ObservableList<String> getAllTypes() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        String sqlStatement = "SELECT DISTINCT Type FROM appointments";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlStatement);

        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            String appointmentType = rs.getString("Type");
            list.add(appointmentType);
        }
        return list;
    }
    /**
     *Updates appointments in the data base
     */
    public static int updateAppts(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int
            userId, int customerId, int appointmentId)throws SQLException{
        String sqlStatement = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
                " Customer_ID =?, User_ID=? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlStatement);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setInt(7, userId);
        ps.setInt(8, customerId);
        ps.setInt(9, appointmentId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    public  static ObservableList<AppointmentModel> apptByMonth() throws SQLException {
        String sqlStatement = "SELECT * FROM client_schedule.appointments WHERE month(start ) = month(now())";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        ObservableList<AppointmentModel> appointments = FXCollections.observableArrayList();
        while (rs.next()){
            int AppointmentID = rs.getInt("Appointment_ID");
            String  appointmentTitle = rs.getString("Title");
            String appointmentDescription = rs.getString("Description");
            String appointmentLocation = rs.getString("Location");
            String appointmentType = rs.getString("Type");
            Timestamp appointmentStart = rs.getTimestamp("Start");
            Timestamp appointmentEnd = rs.getTimestamp("End");
            int appointmentCustomerID = rs.getInt("Customer_ID");
            int appointmentUserID = rs.getInt("User_ID");
            int appointmentContactID = rs.getInt("Contact_ID");
            AppointmentModel new_Appt = new AppointmentModel(AppointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType,
                    appointmentStart, appointmentEnd, appointmentCustomerID, appointmentUserID, appointmentContactID);
           appointments.add(new_Appt);
        }
        return appointments;
    }
}
