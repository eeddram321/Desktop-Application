package Controller;

import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.AppointmentDAO;
import DAO.DivisionDAO;
import Model.AppointmentModel;
import Model.CountryModel;
import Model.CustomerModel;
import Model.DivisionModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.JDBC;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Customer Controller
 */
public class CustomerController implements Initializable {
    /**
     * customer table view
     */
    public TableView<CustomerModel> customerTableView;
    /**
     * customer id column
     */
    public TableColumn<?, ?> customerIDColumn;
    /**
     *customer name column
     */
    public TableColumn<?, ?> customerNameColumn;
    /**
     *customer address column
     */
    public TableColumn<?, ?> customerAddressColumn;
    /**
     *customer postal code
     */
    public TableColumn<?, ?> customerPostalCodeColumn;
    /**
     *customer phone column
     */
    public TableColumn<?, ?> customerPhoneColumn;
    /**
     * back button
     */
    public Button customerBack;
    /**
     *delete button
     */
    public Button customerDelete;
    /**
     *edit button
     */
    public Button customerEdit;
    /**
     *add button
     */
    public Button customerAdd;
    /**
     *division column
     */
    public TableColumn<?, ?> customerDivisionColumn;
    /**
     *country column
     */
    public TableColumn<?, ?> countryNameColumn;
    /**
     *division id column
     */
    public TableColumn<?, ?> customerDivisionIDColumn;

    /**
     * Back button takes user back to the main screen when clicked.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void clickCustomerBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ViewForms/MainScreenForm.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 400);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Deletes the selected customer from the customer table.
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void clickCustomerDelete(ActionEvent actionEvent) throws SQLException, IOException {

        int deleteC = customerTableView.getSelectionModel().getSelectedItem().getCustomerID();
        int deleteCustomer = customerTableView.getSelectionModel().getSelectedItem().getCustomerID();

        ObservableList<AppointmentModel> allApptList = AppointmentDAO.getAllAppointments();
        ObservableList<CustomerModel> customerList = CustomerDAO.getAllCustomers();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected customer and all existing records?");
        Optional<ButtonType> confirmation = alert.showAndWait();
        if(confirmation.isPresent() && confirmation.get() == ButtonType.OK){
            AppointmentDAO.deleteCustomerAppt(deleteCustomer);

            CustomerDAO.deleteCustomerAppt(deleteC);

        }
        Parent customerMenu = FXMLLoader.load(getClass().getResource("/ViewForms/CustomerForm.fxml"));
        Scene scene = new Scene(customerMenu);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    /**
     * Allows user to select the desired row they want to edit and sends them to the Edit cutomer row.
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void clickCustomerEdit(ActionEvent actionEvent) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ViewForms/EditCustomer.fxml"));
        Parent parent = loader.load();
        EditCustomerController edit = loader.getController();
        edit.sendCustomerData(customerTableView.getSelectionModel().getSelectedItem(), customerTableView.getSelectionModel().getSelectedItem());

        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent, 400, 400);
        stage.setTitle("Edit Customer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Takes user to the add customer screen when the add button is clicked.
     * @param actionEvent
     * @throws IOException
     */
    public void clickCustomerAdd(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ViewForms/AddCustomerForm.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 400);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Populates the customer table.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ObservableList<CountryModel> country = CountryDAO.getAllCountries();
            ObservableList<DivisionModel> allFirstLD = DivisionDAO.getAll_F_L_D();
            //getAllDivisionsdetails in observablelist, make a call to firstleveldivisionDAO.getalldivisions
            //getAllCountryDetails in observablelist make a call to countrYDAO.getAllCountries
            ObservableList<CustomerModel> allCustomers = CustomerDAO.getAllCustomers();
            //In a for loop match the divisionID of each customer with the divisionDetails observablelist.
            //Next step match this divisions country id to the getAllCountryDetails observablelist
            customerTableView.setItems(allCustomers);
            customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
            customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
            customerDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
            countryNameColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
            customerDivisionIDColumn.setCellValueFactory(new PropertyValueFactory<>("divisionID"));


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



    }
}