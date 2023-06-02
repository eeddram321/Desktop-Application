package sample;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import Model.AppointmentModel;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Controller.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("../ViewForms/Log_InForm.fxml"));
        primaryStage.setTitle("Main");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {
        JDBC.makeConnection();
//        LocalDate startDate = LocalDate.of(2023,5,8);
//        LocalTime startTime = LocalTime.now();
//        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
//        ObservableList<AppointmentModel> appointments = AppointmentDAO.filterAppointmentsByDate(startDateTime);
//        for (AppointmentModel am: appointments){
//            System.out.println(am.getStart());
//        }
//        ResourceBundle rb = ResourceBundle.getBundle("language_property/loginPage", Locale.getDefault());
//
//        if (Locale.getDefault().getLanguage().equals("en") || (Locale.getDefault().getLanguage().equals("fr") )){
//            System.out.println(rb.getString("usernameField"));
//        }
        launch(args);
        JDBC.closeConnection();
    }
}
