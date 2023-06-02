package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Report Model Class
 */
public class ReportModel{
    /**
     * Country Name
     */
    private String countryName;
    /**
     * Country count
     */
    private int countryCount;
    /**
     * Appointment total
     */
    public int totalAppointment;
    /**
     * Appointment month
     */
    public String appointment_Month;
    /**
     * Appointment type
     */
    public String appointmentType;


    /**
     * Constructor
     * @param countryName
     * @param countryCount
     */
    public ReportModel(String countryName, int countryCount) {
        this.countryName = countryName;
        this.countryCount = countryCount;
    }

    /**
     * getter for country name
     * @return
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * setter for country name
     * @param countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * getter for country count
     * @return
     */
    public int getCountryCount() {
        return countryCount;
    }

    /**
     * setter for country count
     * @param countryCount
     */
    public void setCountryCount(int countryCount) {
        this.countryCount = countryCount;
    }

    /**
     * getter for appointment total
     * @return
     */
    public int getTotalAppointment() {
        return totalAppointment;
    }

    /**
     * setter for appointment total
     * @param totalAppointment
     */
    public void setTotalAppointment(int totalAppointment) {
        this.totalAppointment = totalAppointment;
    }

    /**
     * getter for appointment month/
     * @return
     */
    public String getAppointment_Month() {
        return appointment_Month;
    }

    /**
     * setter for appointment month
     * @param appointment_Month
     */
    public void setAppointment_Month(String appointment_Month) {
        this.appointment_Month = appointment_Month;
    }

    /**
     * getter for appointment type
     * @return
     */
    public String getAppointmentType() {
        return appointmentType;
    }

    /**
     * setter for appointment type
     * @param appointmentType
     */
    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }
}
