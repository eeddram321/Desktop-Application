package Controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import Model.AppointmentModel;
import Model.LogOnModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Add Appointment Controller
 */
public class addAppointmentController implements Initializable {
    boolean validOverlap = true;
    public TextField addApptID;
    public TextField addApptTitle;
    public TextField addApptDescription;
    public ComboBox<String> addAppointmentContact;
    public DatePicker addAppointmentStartDate;
    public TextField addAppointmentLocation;
    public ComboBox<LocalTime> addAppointmentStartTime;
    public DatePicker addAppointmentEndDate;
    public ComboBox<LocalTime> addAppointmentEndTime;
    public TextField addAppointmentType;
    public Button addAppointmentSave;
    public Button addAppointmentCancel;
    public ComboBox<String> addAppointmentCustomerID;
    public ComboBox<String> addAppointmentUserID;
    /**
     * Observable list for customer
     */
    private ObservableList<String> customer = FXCollections.observableArrayList();
    /**
     * Observable list for users
     */
    private ObservableList<String> user = FXCollections.observableArrayList();
    /**
     * Observable list for contact
     */
    private ObservableList<String> contact = FXCollections.observableArrayList();
    /**
     * Observable list for start time
     */
    private ObservableList<LocalTime> startTime = FXCollections.observableArrayList();
    /**
     * Observable list for end time
     */
    private ObservableList<LocalTime> endTime = FXCollections.observableArrayList();
    private ZoneId Zon;

    /**
     * Save button allows the user to save an appointment. It will notify the user if there is an overlap or if textfields or combo boxes are left blank.
     * Lambda expression
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void clickAddAppointmentSaveButton(ActionEvent actionEvent) throws SQLException, IOException {
        ObservableList<AppointmentModel> filterApptCustomers = FXCollections.observableArrayList();

        try {
        String apptTitle = addApptTitle.getText();
        String apptDescription = addApptDescription.getText();
        String apptContact = addAppointmentContact.getValue();
        LocalDate apptStartDate = addAppointmentStartDate.getValue();
        String apptLocation = addAppointmentLocation.getText();
        LocalTime apptStartTime = addAppointmentStartTime.getValue();
        LocalTime apptEndTime = addAppointmentEndTime.getValue();
        LocalDate apptEndDate = addAppointmentEndDate.getValue();
        String apptType = addAppointmentType.getText();
        int apptCustomerID = Integer.parseInt(addAppointmentCustomerID.getValue());
        int apptUserID = Integer.parseInt(addAppointmentUserID.getValue());
        LocalDateTime start = null;
        LocalDateTime end = null;
        ZonedDateTime zonedStartTime = null;
        ZonedDateTime zonedEndTime = null;
        int contactID = Integer.parseInt(ContactDAO.lookupContactID(apptContact));
        //lambda expression ->
        filterApptCustomers = AppointmentDAO.getAllAppointments().filtered(appt -> appt.getAppointmentCustomerID() ==apptCustomerID);
        System.out.println(filterApptCustomers.size());

            //if statement for missing textfield or combo box values.
            if (apptTitle.isBlank() || apptDescription.isBlank() || apptContact.isBlank() || apptStartDate == null || apptLocation.isBlank() ||
                    apptStartTime == null || apptEndTime == null || apptEndDate == null || apptType.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "There are some missing values! Please fill in all the boxes with values to proceed.");
                alert.showAndWait();
                return;

            }
            //start and end (time and date)
            start = LocalDateTime.of(apptStartDate, apptStartTime);
            end = LocalDateTime.of(apptEndDate, apptEndTime);

            if (end.isBefore(start)){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Start time must be before End time.");
                alert.showAndWait();
                return;
            }
            //For loop to check if the appointment is overlapping with another appointment from same ID
            for (AppointmentModel appt : filterApptCustomers) {
                LocalDateTime apptStart = appt.getAppointmentStart().toLocalDateTime();
                LocalDateTime apptEnd = appt.getAppointmentEnd().toLocalDateTime();

                if (start.equals(apptStart) || end.equals(apptEnd)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, " Appointment Overlap, please change the appointment time!");
                    alert.showAndWait();
                    return;
                }
                if (start.isAfter(apptStart) && start.isBefore(apptEnd)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, " Appointment Overlap, please change the appointment time!");
                    alert.showAndWait();
                    return;
                }
                if (end.isAfter(apptStart) && end.isBefore(apptEnd)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, " Appointment Overlap, please change the appointment time!");
                    alert.showAndWait();
                    return;
                }
                if (start.isBefore(apptStart) && end.isAfter(apptEnd)){
                    Alert alert = new Alert(Alert.AlertType.ERROR, " Appointment Overlap, please change the appointment time!");
                    alert.showAndWait();
                    return;
                }
                if (start.equals(apptEnd)){
                    Alert alert = new Alert(Alert.AlertType.ERROR, " Appointment Overlap, please change the appointment time!");
                    alert.showAndWait();
                    return;
                }

                //localDateTime start and end
            }
            int rowsAffected = AppointmentDAO.addAppt(apptTitle, apptDescription, apptLocation, apptType, start, end, apptCustomerID,
                    apptUserID, contactID);
            if (rowsAffected < 1) {
                Alert alert = new Alert(Alert.AlertType.ERROR, " Error Appointment Not Added!");
                alert.showAndWait();
                return;
            }

            Parent root = FXMLLoader.load(getClass().getResource("/ViewForms/Appointments.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1100, 700);
            stage.setTitle("Appointments");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
       //watch is about time webinar

    }

    /**
     * Cancel button sends the user back to the Appointments screen
     * @param actionEvent
     * @throws IOException
     */
    public void clickAddAppointmentCancelButton(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/ViewForms/Appointments.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Start End time combo box method
     */
    public void startEndTimeComboBox(){
        ZonedDateTime startEst = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8, 0), ZoneId.of("America/New_York"));
        LocalDateTime startLocal = startEst.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endLocal = startLocal.plusHours(14);

        while (startLocal.isBefore(endLocal)){
            startTime.add(startLocal.toLocalTime());
            startLocal = startLocal.plusMinutes(30);
            endTime.add(startLocal.toLocalTime());
        }


        addAppointmentStartTime.setItems(startTime);
        addAppointmentEndTime.setItems(endTime);
    }

    /**
     * Populates the customer combo box
     * @throws SQLException
     */
    public void populateCustomerComboBox() throws SQLException {
        customer = CustomerDAO.getCustomersID();
        addAppointmentCustomerID.setItems(customer);
    }

    /**
     * Populates the user combo box
     * @throws SQLException
     */
    public void populateUsersComboBox() throws SQLException{
        user = UserDAO.getUsersID();
        addAppointmentUserID.setItems(user);
}

    /**
     * Populates the contact combo box
     * @throws SQLException
     */
    public void populateContactComboBox() throws SQLException{
        contact = ContactDAO.getAllContacts();
        addAppointmentContact.setItems(contact);
    }

    /**
     * Initialize method
     * Populates all combo boxes.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateCustomerComboBox();
        populateUsersComboBox();
        populateContactComboBox();
            startEndTimeComboBox();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
