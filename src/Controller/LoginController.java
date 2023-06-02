package Controller;

import DAO.AppointmentDAO;
import DAO.UserDAO;
import Model.AppointmentModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Login Controller
 */
public class LoginController implements Initializable {
    /**
     *User Name text field
     */
    public TextField userNameLogInForm;
    /**
     *Password textfield
     */
    public TextField passwordLogInForm;
    /**
     *Login Label
     */
    public Label loginField;
    /**
     *Login Button
     */
    public Button loginButton;
    /**
     *Cancel Button
     */
    public Button cancelButton;
    /**
     *Username Label
     */
    public Label usernameField;
    /**
     *Password label
     */
    public Label passwordField;
    /**
     *Location Textfield
     */
    public TextField LocationFieldLogin;
    /**
     *Location Label
     */
    public Label locationLabelField;
    /**
     *Resource bundle language property
     */
    ResourceBundle rb = ResourceBundle.getBundle("language_property/loginPage", Locale.getDefault());
    /**
     *Login button allows user to access the application.
     * Lambda expression #2
     */
    public void clickLoginButton(ActionEvent actionEvent) throws IOException, SQLException {
        try {
            String usernameInput = userNameLogInForm.getText();
            String passwordInput = passwordLogInForm.getText();
            int userId = UserDAO.authenticateUser(usernameInput, passwordInput);

            FileWriter fileWriter = new FileWriter("login_activity.txt", true);
            PrintWriter outputFile = new PrintWriter(fileWriter);

            if (userId > 0) {
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime nowPlus15Min =now.plusMinutes(15);
                int getAppointmentID = 0;
                LocalDateTime displayTime = null;
                boolean appointmentWithin30Min = false;
                //log the successful login
                outputFile.append("user: " + usernameInput + " successfully logged in at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");
//                outputFile.append("This is a test.");
                outputFile.close();
                ObservableList<AppointmentModel> upcoming = AppointmentDAO.getAllAppointments().stream()
                        .filter(a -> !( a.getAppointmentStart().toLocalDateTime().isAfter(nowPlus15Min) ||
                                        a.getAppointmentStart().toLocalDateTime().isBefore(now)))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));

                if (upcoming.isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No upcoming appointments.");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("no upcoming appointments");
                }else{

                    String message = "Appointment(s) within 15 minutes:";
                    for (AppointmentModel appt : upcoming){
                        message += "\nId:" +  appt.getAppointmentID() + " starting at: " +
                                appt.getAppointmentStart().toLocalDateTime().toLocalDate() + " " +
                                appt.getAppointmentStart().toLocalDateTime().toLocalTime();
                    }

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message );
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("There is an appointment within 30 minutes");
                }

            } else if (userId < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("Error"));
                alert.setContentText(rb.getString("Incorrect"));
                alert.show();

                //log the failed login attempt
                outputFile.append("user: " + usernameInput + " failed login attempt at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");
                outputFile.close();
                return;

            }

            Parent root = FXMLLoader.load(getClass().getResource("/ViewForms/MainScreenForm.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 600, 500);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.show();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    /**
     *Exits user out of the application
     */
    public void clickCancelButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
    /**
     *Initialize method
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            Locale locale = Locale.getDefault();
            Locale.setDefault(locale);
//        Locale.setDefault(Locale.FRENCH);
            ZoneId zone = ZoneId.systemDefault();
            rb = ResourceBundle.getBundle("language_property/loginPage", Locale.getDefault());

            LocationFieldLogin.setText(String.valueOf(zone));
            usernameField.setText(rb.getString("Username"));
            passwordField.setText(rb.getString("Password"));
            loginButton.setText(rb.getString("Login"));
            cancelButton.setText(rb.getString("Exit"));
            locationLabelField.setText(rb.getString("Location"));
            loginField.setText(rb.getString("Log-in"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    }
