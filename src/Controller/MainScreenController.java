package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main Screen Controller
 */
public class MainScreenController {
    /**
     *Customer button
     */
    public Button customerMainMenu;
    /**
     *Appointment button
     */
    public Button appointmentMainMenu;
    /**
     *Reports button
     */
    public Button reportsMainMenu;
    /**
     *Exit button
     */
    public Button exitMainMenu;

    /**
     * Customer Button on the Main screen fxml
     * @param actionEvent
     * @throws IOException
     */
    public void clickCustomerMainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ViewForms/CustomerForm.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Appointment button on Main Menu screen
     * @param actionEvent
     * @throws IOException
     */
    public void clickAppointmentMainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ViewForms/Appointments.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Report button on Main Menu screen.
     * @param actionEvent
     * @throws IOException
     */
    public void clickReportsMainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ViewForms/ReportsForm.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Exit button closes the application
     * @param exitButton
     */
    public void clickExitMainMenu(ActionEvent exitButton) {
        Stage stage = (Stage) ((Node)exitButton.getSource()).getScene().getWindow();
        stage.close();
    }
}
