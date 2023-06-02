package Controller;

import DAO.*;
import Model.*;
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
import java.util.ResourceBundle;

/**
 * Edit Appointment Controller
 */
public class EditAppointmentController implements Initializable {
    AppointmentModel appt;
    /**
     * selected variable
     */
    private static AppointmentModel selected;
    /**
     *Id textfield
     */
    public TextField editAppointmentID;
    /**
     *Title textfield
     */
    public TextField editAppointmentTitle;
    /**
     *Description textfield
     */
    public TextField editAppointmentDescription;
    /**
     *Location Textfield
     */
    public TextField editAppointmentLocation;
    /**
     *Contact combo box
     */
    public ComboBox<ContactModel> editAppointmentContact;
    /**
     *Type textfield
     */
    public TextField editAppointmentType;
    /**
     *Customer id combo box
     */
    public ComboBox<String> editAppointmentCustomerID;
    /**
     *Appointment start datepicker
     */
    public DatePicker editAppointmentDate;
    /**
     *Start time combo box
     */
    public ComboBox<LocalTime> editAppointmentStart;
    /**
     *End time combo box
     */
    public ComboBox<LocalTime> editAppointmentEnd;
    /**
     *Save button
     */
    public Button editAppointmentSaveButton;
    /**
     *Cancel button
     */
    public Button editAppointmentCancel;
    /**
     *User id combo box
     */
    public ComboBox<String> editAppointmentUserID;
    /**
     *End date datepicker
     */
    public DatePicker editAppointmentEndDate;

    private ObservableList<ContactModel> contacts = FXCollections.observableArrayList();
    private ObservableList<String> users = FXCollections.observableArrayList();
    private ObservableList<String> customers = FXCollections.observableArrayList();
    /**
     * Observable list for end time
     */
    private ObservableList<LocalTime> endTime = FXCollections.observableArrayList();
    /**
     * Observable list for start time
     */
    private ObservableList<LocalTime> startTime = FXCollections.observableArrayList();

    /**
     *Allows user to save the edited appointment. It will notify user with an error if there is an overlap or a textfield is left blank.
     * Lambda #1
     * @param actionEvent
     * @throws IOException
     */

    public void clickEditApptSave(ActionEvent actionEvent) throws IOException, SQLException{
        ObservableList<AppointmentModel> filterApptCustomers = FXCollections.observableArrayList();
        try {
            String title = editAppointmentTitle.getText();
            String description = editAppointmentDescription.getText();
            String location = editAppointmentLocation.getText();
            ContactModel contact = editAppointmentContact.getValue();
            String type = editAppointmentType.getText();
            LocalDate apptDateStart = editAppointmentDate.getValue();
            LocalDate apptDateEnd = editAppointmentEndDate.getValue();
            LocalTime apptStartTime = editAppointmentStart.getValue();
            LocalTime apptEndTime = editAppointmentEnd.getValue();
            LocalDateTime start = null;
            LocalDateTime end = null;
            ZonedDateTime zonedStartTime = null;
            ZonedDateTime zonedEndTime = null;
//            int contactID = Integer.parseInt(ContactDAO.lookupContactID(contact));
            int apptCustomerID = Integer.parseInt(editAppointmentCustomerID.getValue());
            int apptUserID = Integer.parseInt(editAppointmentUserID.getValue());


            //lambda expression ->
            filterApptCustomers = AppointmentDAO.getAllAppointments().filtered(appt -> appt.getAppointmentCustomerID() == apptCustomerID);
            System.out.println(filterApptCustomers.size());
            //if statement for missing textfield or combo box values.
            if (title.isBlank() || description.isBlank() || contact == null || apptDateStart == null || location.isBlank() ||
                    apptStartTime == null || apptEndTime == null || apptDateEnd == null || type.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "There are some missing values! Please fill in all the boxes with values to proceed.");
                alert.showAndWait();
                return;

            }
            //start and end (time and date)
            start = LocalDateTime.of(apptDateStart, apptStartTime);
            end = LocalDateTime.of(apptDateEnd, apptEndTime);

            if (end.isBefore(start)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Start time must be before End time.");
                alert.showAndWait();
                return;
            }
            //For loop to check if the appointment is overlapping with another appointment from same ID
            for (AppointmentModel appt : filterApptCustomers) {
                System.out.println("Appointmentid = " + appt.getAppointmentID() + " ... Selected id =" + selected.getAppointmentID());
                if (appt.getAppointmentID() == selected.getAppointmentID()) {
                    continue;
                }
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
                if (start.isBefore(apptStart) && end.isAfter(apptEnd)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, " Appointment Overlap, please change the appointment time!");
                    alert.showAndWait();
                    return;
                }
                if (start.equals(apptEnd)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, " Appointment Overlap, please change the appointment time!");
                    alert.showAndWait();
                    return;
                }
            }
           int rowsAffected = AppointmentDAO.updateAppts(title, description, location, type, start, end, apptCustomerID, apptUserID, selected.getAppointmentID());
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
                return;

            }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    /**
     *Cancel button. Takes user back to the Appointments screen
     */
    public void clickEditAppointmentCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ViewForms/Appointments.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }
    /**
     *Populates Contact Combo Box
     */
    public void populateContactComboBox() throws SQLException {
        contacts = ContactDAO.getAlleditContacts();
        editAppointmentContact.setItems(contacts);
    }
    /**
     *Populates User Combo box
     */
    public void populateUserComboBox() throws SQLException {
        users= UserDAO.getUsersID();
        editAppointmentUserID.setItems(users);
    }
    /**
     *Populates cutomer combo box
     */
    public void populateCustomerComboBox() throws SQLException {
        customers = CustomerDAO.getCustomersID();
        editAppointmentCustomerID.setItems(customers);
    }
    /**
     * Sends data to the appoiintment table.
     * @param appt
     * @param selectedRow
     * @throws SQLException
     */
    public void sendAppointmentData(AppointmentModel appt, AppointmentModel selectedRow) throws SQLException {
        this.appt = appt;
           selected = selectedRow;
        editAppointmentTitle.setText(String.valueOf(appt.getAppointmentTitle()));
        editAppointmentDescription.setText(String.valueOf(appt.getAppointmentDescription()));
        editAppointmentType.setText(String.valueOf(appt.getAppointmentType()));
        editAppointmentLocation.setText(String.valueOf(appt.getAppointmentLocation()));
        editAppointmentDate.setValue(appt.getAppointmentStart().toLocalDateTime().toLocalDate());
        editAppointmentEndDate.setValue(appt.getAppointmentEnd().toLocalDateTime().toLocalDate());
       editAppointmentEnd.setValue(appt.getAppointmentEnd().toLocalDateTime().toLocalTime());
        editAppointmentStart.setValue(appt.getAppointmentStart().toLocalDateTime().toLocalTime());
//        editAppointmentContact.setValue(new CustomerModel(appt.getAppointmentContactName()));
        System.out.println("Contact: " + appt.getAppointmentContactID());

        editAppointmentUserID.setValue(String.valueOf(appt.getAppointmentUserID()));
        System.out.println("User:" + appt.getAppointmentUserID());
        editAppointmentCustomerID.setValue(String.valueOf(appt.getAppointmentCustomerID()));
        System.out.println("Customer" + appt.getAppointmentCustomerID());

        for (ContactModel cm: editAppointmentContact.getItems()){
            if(cm.getContactID() ==appt.getAppointmentContactID()){
                editAppointmentContact.setValue(cm);
                break;
            }

        }
    }
    /**
     *Start End time Combo Box
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
        editAppointmentStart.setItems(startTime);
        editAppointmentEnd.setItems(endTime);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            populateContactComboBox();
            populateCustomerComboBox();
            populateUserComboBox();
            startEndTimeComboBox();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
