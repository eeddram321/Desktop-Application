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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Reports Controller
 */
public class ReportsController implements Initializable {
    /**
     *Contact table
     */
    public TableView<AppointmentModel> contactsTableView;
    /**
     *Customer by country table
     */
    public TableView<CustomerModel> customerByCountryTV;
    /**
     *Back button
     */
    public Button reportsBack;
    /**
     *Month Combo box
     */
    public ComboBox<String> reportsMonthComboBox;
    /**
     *Type combo box
     */
    public ComboBox<String> reportsTypeComboBox;
    /**
     *Go button
     */
    public Button reportsGo;
    /**
     *Id column
     */
    public TableColumn<?, ?> reports2IDColumn;
    /**
     *Title column
     */
    public TableColumn<?, ?> reports2TitleColumn;
    /**
     *Description column
     */
    public TableColumn<?, ?> reports2Description;
    /**
     *Location column
     */
    public TableColumn<?, ?> reports2Location;
    /**
     *Type column
     */
    public TableColumn<?, ?> reports2Type;
    /**
     *End time column
     */
    public TableColumn<?, ?> reports2EndColumn;
    /**
     *Customer Id column
     */
    public TableColumn<?, ?>reports2customerIDColumn;
    /**
     *Contact column
     */
    public TableColumn <?, ?>reports2contactColumn;
    /**
     *User id column
     */
    public TableColumn<?, ?> reports2userIDColumn;
    /**
     *Start time and date column
     */
    public TableColumn<?, ?> reports2StartColumn;
    /**
     *Contacts combo box
     */
    public ComboBox<String> contactsComboBox;
    /**
     *Month and type label
     */
    public Label monthTypeCountLBL;
    /**
     *Country Combo box
     */
    public ComboBox<CountryModel> countryComboBox;
    /**
     *Divion column
     */
    public TableColumn<?, ?> divisionColumn;
    /**
     *Phone column
     */
    public TableColumn<?, ?> phoneColumn;
    /**
     *Postal code column
     */
    public TableColumn<?, ?> postalCodeColumn;
    /**
     *Address column
     */
    public TableColumn<?, ?> addressColumn;
    /**
     *Customer Name column
     */
    public TableColumn<?, ?> customerNameColumn;
    /**
     *Customer id column
     */
    public TableColumn<?, ?> customerIDColumn;
    /**
     *Observable list for contacts.
     */
    private ObservableList<ContactModel> contacts = FXCollections.observableArrayList();

//    ObservableList<AppointmentModel> appointmentOL = null;
    /**
     *Back button takes user to the main menu screen
     */
    public void clickReportsBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ViewForms/MainScreenForm.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 500);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }
    /**
     *Go button. It displays the total amount of appointments there by Month and Type.
     */
    public void clickReportsGo(ActionEvent actionEvent) {
        String month = reportsMonthComboBox.getValue();
        String type = reportsTypeComboBox.getValue();
        if (month.isBlank() || type.isBlank()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a month and a type!");
            alert.showAndWait();
            return;
        }
        int count = 0;
        try {
            count = ReportDAO.getMonthType(month, type);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        monthTypeCountLBL.setText(String.valueOf(count));

    }
    /**
     *When selecting a contact from the Contacts combo box, the appointment will display on the Contact table.
     */
    public void onContactChange(ActionEvent actionEvent) throws SQLException {
        //get the contact from the contact combobox a contact object.
        //get id from the contact to get  it's appointments.
        //set the list item to the list of appointments.
        try {

            int contactID = 0;

            ObservableList<AppointmentModel> getAllAppointmentData = AppointmentDAO.getAllAppointments();
            ObservableList<AppointmentModel> appointmentInfo = FXCollections.observableArrayList();
            ObservableList<ContactModel> getAllContacts = ContactDAO.getAlleditContacts();

            AppointmentModel apptContactInfo;

            String contactName = contactsComboBox.getSelectionModel().getSelectedItem();

            for (ContactModel contact: getAllContacts) {
                if (contactName.equals(contact.getContactName())) {
                    contactID = contact.getContactID();
                }
            }

            for (AppointmentModel appointment: getAllAppointmentData) {
                if (appointment.getAppointmentContactID() == contactID) {
                    apptContactInfo = appointment;
                    appointmentInfo.add(apptContactInfo);
                }
            }
            contactsTableView.setItems(appointmentInfo);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     *Month combo box displays months from January-December
     */
    public void populateReportByMonthComboBox(){
        ObservableList<String> monthOfAppt = FXCollections.observableArrayList();
        for (int i = 0; i < 12; i++){
            monthOfAppt.add(Month.of(i + 1).getDisplayName(TextStyle.FULL, Locale.getDefault()));
        }
        reportsMonthComboBox.setItems(monthOfAppt);
    }

    public void populateTypeComboBox() throws SQLException {

        reportsTypeComboBox.setItems(AppointmentDAO.getAllTypes());
    }
    /**
     *When user selects a country from the country combo box, it will display how many customers are from that country.
     */
    public void onCountryChange(ActionEvent actionEvent) throws SQLException {
        try {

            CustomerModel customerInfo;
            ObservableList<CustomerModel> cust =FXCollections.observableArrayList();
            CountryModel country= countryComboBox.getSelectionModel().getSelectedItem();
            if (countryComboBox.getSelectionModel().getSelectedIndex() > 0){
                 cust = CustomerDAO.getAllCustomersByCountry(country);

            }else {
                cust = CustomerDAO.getAllCustomers();
            }

        customerByCountryTV.setItems(cust);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     *Initialize method
     */
        @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            try {

                ObservableList<String> allContactsNames = FXCollections.observableArrayList();
                contactsComboBox.setItems(ContactDAO.getAllContacts());
                reports2IDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
                reports2TitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
                reports2Description.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
                reports2Location.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
                reports2Type.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
                reports2EndColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
                reports2customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentCustomerID"));
                reports2contactColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentContactID"));
                reports2userIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentUserID"));
                reports2StartColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));


                ObservableList<CountryModel> country = CountryDAO.getAllCountries();
                ObservableList<DivisionModel> allFirstLD = DivisionDAO.getAll_F_L_D();
                country.add(0, new CountryModel(0, "All"));
                //getAllDivisionsdetails in observablelist, make a call to firstleveldivisionDAO.getalldivisions
                //getAllCountryDetails in observablelist make a call to countrYDAO.getAllCountries
                ObservableList<CustomerModel> allCustomers = CustomerDAO.getAllCustomers();
                //In a for loop match the divisionID of each customer with the divisionDetails observablelist.
                //Next step match this divisions country id to the getAllCountryDetails observablelist
                customerByCountryTV.setItems(allCustomers);
                customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
                customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
                addressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
                phoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
                postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
                divisionColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));

            countryComboBox.setItems(country);

                populateReportByMonthComboBox();
                populateTypeComboBox();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

       }



}
