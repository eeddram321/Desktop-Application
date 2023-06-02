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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Add Customer Controller
 */
public class AddCustomerController implements Initializable {
    /**
     * customer id textfield
     */
    public TextField addCustomerID;
    /**
     * customer name textfield
     */
    public TextField addCustomerName;
    /**
     * customer phone text field
     */
    public TextField addCustomerPhone;
    /**
     *customer address textfield
     */
    public TextField addCustomerAddress;
    /**
     *country combo box
     */
    public ComboBox<CountryModel> addCustomerCountry;
    /**
     *division combo box
     */
    public ComboBox<DivisionModel> addCustomerState;
    /**
     *postal code textfield
     */
    public TextField addCustomerPostalCode;
    /**
     * save button
     */
    public Button addCustomerSave;
    /**
     *cancel button
     */
    public Button addCustomerCancel;

    private ObservableList<CountryModel> countries = FXCollections.observableArrayList();
    private ObservableList<DivisionModel> divisions = FXCollections.observableArrayList();
    CustomerModel customer;

    /**
     * Save button. Allows user to add a new customer.
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void clickAddCustomerSave(ActionEvent actionEvent) throws SQLException, IOException {
        String name = addCustomerName.getText();
        String address = addCustomerAddress.getText();
        String postal = addCustomerPostalCode.getText();
        String phone = addCustomerPhone.getText();
        CountryModel country = addCustomerCountry.getValue();
        DivisionModel division = addCustomerState.getValue();

        int rowsAffected = CustomerDAO.insertCustomer( name, address, postal, phone, division.getDivisionID());
        if(rowsAffected < 1){
            Alert alert = new Alert(Alert.AlertType.ERROR, " Error Customer Not Added!");
            alert.showAndWait();
            return;
        }
        Parent root = FXMLLoader.load(getClass().getResource("/ViewForms/CustomerForm.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Customer");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Cancel button.
     * @param actionEvent
     * @throws IOException
     */
    public void clickAddCustomerCancel(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/ViewForms/CustomerForm.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Customer");
        stage.setScene(scene);
        stage.show();
        return;

    }

    /**
     *  method to populate the Country combo box
     * @throws SQLException
     */
    public void populateCountryComboBox() throws SQLException {
        countries = CountryDAO.getAllCountries();
        addCustomerCountry.setItems(countries);}
    /**
     *method to populate the division combo box
     */
    public void populateDivisionComboBox() throws SQLException {
        divisions = DivisionDAO.getAll_F_L_D();
        addCustomerState.setItems(divisions);    }
    /**
     *In this method you have to choose a country first and then it allows you to choose a state or province from the division combo box.
     */
    public void onSelectCountry(ActionEvent actionEvent) {
        ObservableList<DivisionModel> filterDivision = FXCollections.observableArrayList();
        addCustomerState.setDisable(false);
        for(DivisionModel div : divisions){
            System.out.println(div.getDivisionName());
            if(div.getCountryID() == addCustomerCountry.getValue().getCountryID()){
                filterDivision.add(div);
            }
        }
        addCustomerState.setItems(filterDivision);

    }

    /**
     * Populates the combo boxes.
     * @param url
     * @param resourceBundle
     */
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
