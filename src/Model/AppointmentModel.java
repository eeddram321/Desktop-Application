package Model;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Appointment Class
 * @author Eduardo Ramirez
 */

public class AppointmentModel {
    /**
     *appointment id
     */
    private  int appointmentID;
    /**
     *appointment title
     */
    private String appointmentTitle;
    /**
     *appointment description
     */
    private String appointmentDescription;
    /**
     *appointment location
     */
    private  String appointmentLocation;
    /**
     *appointment type
     */
    private String appointmentType;
    /**
     *appointment start time
     */
    private Timestamp appointmentStart;
    /**
     *appointment end time
     */
    private Timestamp appointmentEnd;
    /**
     *appointment customer id
     */
    private int appointmentCustomerID;
    /**
     *appointment user id
     */
    private int appointmentUserID;
    /**
     *appointment contact id
     */
    private  int appointmentContactID;
    /**
     *appointment start date and time
     */
    private LocalDateTime start;
    /**
     *appointment contact name
     */
    private String appointmentContactName;
    /**
     *appointment user name
     */
    private String appointmentUserName;
    /**
     *appointment customer name
     */
    private String appointmentCustomerName;

    private LocalDateTime end;

    /**
     *
     * @param appointmentID primary key.
     * @param appointmentTitle foreign key.
     * @param appointmentDescription appointment description.
     * @param appointmentLocation appointment location.
     * @param appointmentType appointment type.
     * @param appointmentStart appointment start date and time.
     * @param appointmentEnd appointment end date and time.
     * @param appointmentCustomerID customer ID(Foreign key).
     * @param appointmentUserID User ID (Foreign key).
     * @param appointmentContactID (Foreign key) Contact ID.

     */

    public AppointmentModel(int appointmentID, String appointmentTitle, String appointmentDescription,
                            String appointmentLocation, String appointmentType, Timestamp appointmentStart, Timestamp appointmentEnd, int appointmentCustomerID,
                             int appointmentUserID, int appointmentContactID) {
        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.appointmentStart = appointmentStart;
        this.appointmentEnd = appointmentEnd;
        this.appointmentCustomerID = appointmentCustomerID;
        this.appointmentUserID = appointmentUserID;
        this.appointmentContactID = appointmentContactID;
    }

    public AppointmentModel(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, Timestamp appointmentStart, Timestamp appointmentEnd, int appointmentCustomerID, int appointmentUserID, int appointmentContactID, LocalDateTime start, String appointmentContactName, String appointmentUserName, String appointmentCustomerName, LocalDateTime end) {
        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.appointmentStart = appointmentStart;
        this.appointmentEnd = appointmentEnd;
        this.appointmentCustomerID = appointmentCustomerID;
        this.appointmentUserID = appointmentUserID;
        this.appointmentContactID = appointmentContactID;
        this.start = start;
        this.appointmentContactName = appointmentContactName;
        this.appointmentUserName = appointmentUserName;
        this.appointmentCustomerName = appointmentCustomerName;
        this.end = end;
    }

    /**
     *getter for Customer Name
     */
    public String getAppointmentCustomerName() {
        return appointmentCustomerName;
    }

    /**
     * appointment for customer name
     * @param appointmentCustomerName
     */
    public void setAppointmentCustomerName(String appointmentCustomerName) {
        this.appointmentCustomerName = appointmentCustomerName;
    }
    /**
     *getter for User Name
     */
    public String getAppointmentUserName() {
        return appointmentUserName;
    }

    /**
     * Setter for appointment Username
     * @param appointmentUserName
     */
    public void setAppointmentUserName(String appointmentUserName) {
        this.appointmentUserName = appointmentUserName;
    }
    /**
     *getter for Contact Name
     */
    public String getAppointmentContactName() {
        return appointmentContactName;
    }

    public void setAppointmentContactName(String appointmentContactName) {
        this.appointmentContactName = appointmentContactName;
    }

    public AppointmentModel(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * getter method for appointmentID
     * @return
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * setter method for appointment ID
     * @param appointmentID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * getter method for appointment Title
     * @return
     */
    public String getAppointmentTitle() {
        return appointmentTitle;
    }
    /**
     * setter method for appointment Title
     * @param appointmentTitle
     */
    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }
    /**
     * getter method for appointment description
     * @return
     */
    public String getAppointmentDescription() {
        return appointmentDescription;
    }
    /**
     * setter method for appointment description
     * @param appointmentDescription
     */
    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }
    /**
     * getter method for appointment Location
     * @return
     */
    public String getAppointmentLocation() {
        return appointmentLocation;
    }
    /**
     * setter method for appointment Location
     * @param appointmentLocation
     */
    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }
    /**
     * getter method for appointment Type
     * @return
     */
    public String getAppointmentType() {
        return appointmentType;
    }
    /**
     * setter method for appointment Type
     * @param appointmentType
     */
    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }
    /**
     * getter method for appointment start date and time.
     * @return
     */
    public Timestamp getAppointmentStart() {
        return appointmentStart;
    }
    /**
     * setter method for appointment Start date and time
     * @param appointmentStart
     */
    public void setAppointmentStart(Timestamp appointmentStart) {
        this.appointmentStart = appointmentStart;
    }
    /**
     * getter method for appointment End date and time.
     * @return
     */
    public Timestamp getAppointmentEnd() {
        return appointmentEnd;
    }
    /**
     * setter method for appointment End date and time
     * @param appointmentEnd
     */
    public void setAppointmentEnd(Timestamp appointmentEnd) {
        this.appointmentEnd = appointmentEnd;
    }
    /**
     * getter method for appointment Customer ID.
     * @return
     */
    public int getAppointmentCustomerID() {
        return appointmentCustomerID;
    }
    /**
     * setter method for appointment customer ID
     * @param appointmentCustomerID
     */
    public void setAppointmentCustomerID(int appointmentCustomerID) {
        this.appointmentCustomerID = appointmentCustomerID;
    }


    /**
     * getter method for appointment User ID
     * @return
     */
    public int getAppointmentUserID() {
        return appointmentUserID;
    }
    /**
     * setter method for appointment user ID
     * @param appointmentUserID
     */
    public void setAppointmentUserID(int appointmentUserID) {
        this.appointmentUserID = appointmentUserID;
    }
    /**
     * getter method for appointment Contact Id
     * @return
     */
    public int getAppointmentContactID() {
        return appointmentContactID;
    }
    /**
     * setter method for appointment contact ID
     * @param appointmentContactID
     */
    public void setAppointmentContactID(int appointmentContactID) {
        this.appointmentContactID = appointmentContactID;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
    /**
     * getter method for appointment Contact Name
     * @return
     */
  /*  public String getAppointmentContactName() {
        return appointmentContactName;
    }

    public void setAppointmentContactName(String appointmentContactName) {
        this.appointmentContactName = appointmentContactName;
    }*/




    }

