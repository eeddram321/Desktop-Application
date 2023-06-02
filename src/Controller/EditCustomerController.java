package Controller;

import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.DivisionDAO;
import Model.CountryModel;
import Model.CustomerModel;
import Model.DivisionModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.JDBC;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Edit Customer Controller
 */
public class EditCustomerController implements Initializable {
    private static CustomerModel selected;
    /**
     *Customer Id text field
     */
    public TextField editCustomerID;
    /**
     *Customer Name textfield
     */
    public TextField editCustomerName;
    /**
     *Address textfield
     */
    public TextField editCustomerAddress;
    /**
     *Phone textfield
     */
    public TextField editCustomerPhone;
    /**
     *Country combo box
     */
    public ComboBox<CountryModel> editCustomerCountry;
    /**
     *State/Province combo box
     */
    public ComboBox<DivisionModel> editCustomerState;
    /**
     *Postal code textfield
     */
    public TextField editCustoemrPostalCode;
    /**
     *Save button
     */
    public Button editCustomerSave;
    /**
     *Cancel button
     */
    public Button editCustomerCancel;
private ObservableList<CountryModel> countries = FXCollections.observableArrayList();
private ObservableList<DivisionModel> divisions = FXCollections.observableArrayList();
CustomerModel customer;

    /**
     * Saves the edited customer data
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void clickEditCustomerSave(ActionEvent actionEvent) throws IOException, SQLException {
        // String country = editCustomerCountry.getValue();
        // String division = editCustomerState.getValue();
            String name = editCustomerName.getText();
            String address = editCustomerAddress.getText();
            String postal = editCustoemrPostalCode.getText();
            String phone = editCustomerPhone.getText();
           // populateCountryComboBox();
            //populateDivisionComboBox();
            CountryModel country = editCustomerCountry.getValue();
            DivisionModel division = editCustomerState.getValue();
            int ID = Integer.parseInt(editCustomerID.getText());

            if ( country== null || division == null || name.isBlank() || address.isBlank() || postal.isBlank() || phone.isBlank()) {
                ButtonType okay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                Alert empty = new Alert(Alert.AlertType.WARNING, "There are some fields with missing values", okay);
                empty.showAndWait();

            }else{
                CustomerModel modifiedCustomer = new CustomerModel( customer.getCustomerID(),  name,  address, postal, phone,  division.getDivisionName(), division.getDivisionID());
                CustomerDAO.saveCustomer(modifiedCustomer);
                Parent root = FXMLLoader.load(getClass().getResource("/ViewForms/CustomerForm.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1000, 700);
                stage.setTitle("Customer");
                stage.setScene(scene);
                stage.show();
                return;
            }
    }

    /**
     *Cancel button takes user back to the Customer form
     */
    public void clickEditCustomerCancel(ActionEvent actionEvent) throws IOException {
        //Alert for cancel
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel?");
        alert.showAndWait();

        Parent root = FXMLLoader.load(getClass().getResource("/ViewForms/CustomerForm.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Customer");
        stage.setScene(scene);
        stage.show();
        return;

    }
    /**
     *Populates the Country combo box
     */
    public void populateCountryComboBox() throws SQLException {
        countries = CountryDAO.getAllCountries();
        editCustomerCountry.setItems(countries);
    }
    /**
     *Populates the division combo box
     */
    public void populateDivisionComboBox() throws SQLException {
        divisions = DivisionDAO.getAll_F_L_D();
        editCustomerState.setItems(divisions);
    }
    /**
     *
     */
    public void onSelectCountry(ActionEvent actionEvent) {

        ObservableList<DivisionModel> filterDivision = FXCollections.observableArrayList();
//        editCustomerState.setDisable(false);
        editCustomerState.setValue(null);
        for(DivisionModel div : divisions){
            System.out.println(div.getDivisionName());
            if(div.getCountryID() == editCustomerCountry.getValue().getCountryID()){
                filterDivision.add(div);

            }
        }
        editCustomerState.setItems(filterDivision);
        /**
         *Sends data to the Customer Table
         */
    }
    public void sendCustomerData(CustomerModel selectedRow, CustomerModel customer) throws SQLException {
        this.customer = customer;
        System.out.println(customer.getCountry());
        selected = selectedRow;
        editCustomerID.setText(String.valueOf(customer.getCustomerID()));
        editCustomerName.setText(customer.getCustomerName());
        editCustomerAddress.setText(String.valueOf(customer.getCustomerAddress()));
        editCustomerPhone.setText(String.valueOf(customer.getCustomerPhone()));
        //typo editcustomer
        editCustoemrPostalCode.setText(String.valueOf(customer.getCustomerPostalCode()));
        populateCountryComboBox();
        populateDivisionComboBox();
        editCustomerCountry.setValue(new CountryModel(customer.getCountryID(), customer.getCountry()));
        editCustomerState.setValue(new DivisionModel(customer.getDivisionID(), customer.getDivisionName(), customer.getCountryID()));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            populateCountryComboBox();
            populateDivisionComboBox();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



    }
}
