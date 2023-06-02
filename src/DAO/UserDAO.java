package DAO;

import Model.UserModel;
import com.mysql.cj.jdbc.JdbcConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *User DAO Class
 */
public class UserDAO extends UserModel {

    /**
     * User constructor
     *
     * @param userName
     * @param userID
     */
    public UserDAO(String userName, int userID, String userPassword) {
        super(userName, userID, userPassword);
    }

    /**
     *Allows User to get all users from the data base
     */
    public static ObservableList<UserModel> getAllUser() throws SQLException{
        ObservableList<UserModel> allUsers = FXCollections.observableArrayList();
        String sqlStatement = "Select * FROM users";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String userPassword = rs.getString("Password");
            UserDAO user = new UserDAO(userName, userID, userPassword);
            allUsers.add(user);
        }
    return allUsers;

    }
    /**
     *Allows user to extract the user id from the data base
     */
    public static  ObservableList<String> getUsersID() throws SQLException {
        ObservableList<String> userID = FXCollections.observableArrayList();
        String User_ID = "SELECT DISTINCT User_ID FROM users";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(User_ID);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            userID.add(rs.getString("User_ID"));
        }
        return userID;
    }
    /**
     *Authenticates user by username and password
     */
    public static int authenticateUser(String username, String password)
    {
        try
        {
            String sqlStatement = "SELECT * FROM users WHERE User_name = '" + username + "' AND Password = '" + password +"'";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlStatement);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("User_ID");
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

}
