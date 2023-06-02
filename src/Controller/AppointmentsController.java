package Controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import Model.AppointmentModel;
import Model.CustomerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.JDBC;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Appointment controller allows user to sort appointments by week, month, or look all the appointments.
 */

public class AppointmentsController implements Initializable {
    /**
     * Appointment table
     */
    public TableView<AppointmentModel> appointmentTableView;
    /**
     * Appointment ID Column
     */
    public TableColumn<?, ?> appointmentIDColumn;
    /**
     *Appointment Title Column
     */
    public TableColumn<?, ?> appointmentTitleColumn;
    /**
     *Appointment Description column
     */
    public TableColumn<?, ?> appointmentDescription;
    /**
     *Appointment Location column
     */
    public TableColumn<?, ?> appointmentLocation;
    /**
     *Appointment Type Column
     */
    public TableColumn<?, ?> appointmentType;
    /**
     *Appointment Start Column
     */
    public TableColumn<?, ?> appointmentStartColun;
    /**
     *Appointment End Column
     */
    public TableColumn<?, ?> appointmentEndColumn;
    /**
     *Customer Id COLUMN
     */
    public TableColumn<?, ?> customerIDColumn;
    /**
     *Contact Id column
     */
    public TableColumn<?, ?> contactColumn;
    /**
     *User Id column
     */
    public TableColumn<?, ?> userIDColumn;
    /**
     *Filter by week radio button
     */
    public RadioButton appointmentWeekRadioButton;
    /**
     * Toggles all radio button
     */
    public ToggleGroup tgroup;
    /**
     * Filter by month Radio button
     */
    public RadioButton appointmentMonthRadioButton;
    /**
     *Filter all appointments radio button
     */
    public RadioButton allAppointmentsRadioButton;
    /**
     *Add button
     */
    public Button addAppointmentsButton;
    /**
     *delete button
     */
    public Button deleteAppointmentButton;
    /**
     *Main menu button
     */
    public Button mainMenuButton;
    /**
     *edit button
     */
    public Button editAppointment;
    ObservableList<AppointmentModel> appointmentOL = null;
  ZonedDateTime start;
  ZonedDateTime end;

    /**
     * Radio button for week. Allows users to view appointments scheduled by week.
     * Lambda expression #3
     * @param actionEvent
     * @throws SQLException
     */
    public void appointmentWeekRadioSelected(ActionEvent actionEvent) throws SQLException {
        try {
            ObservableList<AppointmentModel> allAppts = AppointmentDAO.getAllAppointments();
            ObservableList<AppointmentModel> weekOfAppt = FXCollections.observableArrayList();

            // LocalDateTime start = LocalDateTime.now().minusYears(1);
            //LocalDateTime end = LocalDateTime.now().plusWeeks(1);
            LocalDate localDate = LocalDate.now();
            int weekNumber = localDate.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
            int currentYear = LocalDate.now().getYear();

            //Timestamp timestamp = new Timestamp(System.currentTimeMillis());


            if (allAppts != null) {
                allAppts.forEach(appointment -> {
                    LocalDateTime tstmp = appointment.getAppointmentStart().toLocalDateTime();
                    //LocalDate nowDate = timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


                    int wNumber = tstmp.get(WeekFields.ISO.weekOfWeekBasedYear());
                    int wYear = tstmp.getYear();

                    if (weekNumber == wNumber && currentYear == wYear /*the year has to equal to the current year*/) {
                        weekOfAppt.add(appointment);

                    }
                    appointmentTableView.setItems(weekOfAppt);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *radio button for month. Allows user to view appointments by month.
     *
     */
    public void appointmentMonthRadioSelected(ActionEvent actionEvent) throws SQLException {
        try {
            appointmentTableView.setItems(AppointmentDAO.apptByMonth());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     *All appointments radio button. User can view all of the appointments.
     */
    public void allAppointmentsRadioSelected(ActionEvent actionEvent) throws SQLException {
        ObservableList<AppointmentModel> allAppts = AppointmentDAO.getAllAppointments();
        appointmentTableView.setItems(allAppts);

    }
    /**
     *Add button
     */
    public void clickAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ViewForms/AddAppointmentsForm.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 500);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Delete button. Allows user to select the appointment and delete it by appointment id and type.
     * @param actionEvent
     */
    public void clickDeleteAppointment(ActionEvent actionEvent) {
        try{
            int deleteApptID = appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentID();
            String deleteAppointment= appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentType();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete the selected appointment with appointment id:" + deleteApptID + " and appointment type " +
                     deleteAppointment);
            Optional<ButtonType> confirmation = alert.showAndWait();
            if (confirmation.isPresent() && confirmation.get()==ButtonType.OK){
                AppointmentDAO.deleteAppt(deleteApptID );
                ObservableList<AppointmentModel> allApptList = AppointmentDAO.getAllAppointments();
                appointmentTableView.setItems(allApptList);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     *Back button takes you back to the main menu.
     */
    public void clickBackToMainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ViewForms/MainScreenForm.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 400);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }
    /**
     *Edit Appointment Button
     */
    public void clickEditAppointment(ActionEvent actionEvent) throws IOException, SQLException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ViewForms/EditAppointmentForm.fxml"));
        Parent parent = loader.load();
        EditAppointmentController edit = loader.getController();
        edit.sendAppointmentData(appointmentTableView.getSelectionModel().getSelectedItem(), appointmentTableView.getSelectionModel().getSelectedItem());

        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent, 900, 400);
        stage.setTitle("Edit Customer");
        stage.setScene(scene);
        stage.show();

    }
    /**
     *Populates Appointment table
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //populate months combo box and type combo box
      //  ObservableList<AppointmentModel> appointmentOL = null;
        try {
            appointmentOL = AppointmentDAO.getAllAppointments();
            appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
            appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
            appointmentLocation.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
            appointmentType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            appointmentStartColun.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
            appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
            customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentCustomerID"));
            userIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentUserID"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentContactID"));
            appointmentTableView.setItems(appointmentOL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }

        }



    }

