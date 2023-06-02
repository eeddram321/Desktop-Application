package Model;

import sample.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;

public class LogOnModel {
    /**
     * User time zone
     */
    private static ZoneId userTimeZone;
    /**
     * Logged User
     */
    private static UserModel loggedOnUser;
    /**
     * User locale
     */
    private static Locale userLocale;


    public LogOnModel() {};

    /**
     * Prepared Statement
     * @param userName
     * @param userPassword
     * @return
     * @throws SQLException
     */
    public static boolean logOnAttempt(String userName, String userPassword) throws SQLException{

        PreparedStatement sqlStatement = JDBC.getConnection().prepareStatement("SELECT * FROM users WHERE " +
                "User_Name = ? AND Password = ?");
        sqlStatement.setString(1, userName);
        sqlStatement.setString(2, userPassword);
        System.out.println("Executing query...");
        ResultSet rs =  sqlStatement.executeQuery();
        if (!rs.next()) {
            return false;
            //Log failed login attempt

        }
        else {
            loggedOnUser = new UserModel(rs.getString("User_Name"), rs.getInt("User_ID"));
            userLocale = Locale.getDefault();
            userTimeZone = ZoneId.systemDefault();
            return true;

        }


    }

    /**
     *
     * @return
     */
    public static UserModel getLoggedOnUser() {
        return loggedOnUser;
    }

    /**
     * Setter for logged on user
     * @param loggedOnUser
     */
    public static void setLoggedOnUser(UserModel loggedOnUser) {
        LogOnModel.loggedOnUser = loggedOnUser;
    }

    /**
     * getter for user locale
     * @return
     */
    public static Locale getUserLocale() {
        return userLocale;
    }

    /**
     * Setter for user locale
     * @param userLocale
     */
    public static void setUserLocale(Locale userLocale) {
        LogOnModel.userLocale = userLocale;
    }

    /**
     * getter for user time zone
     * @return
     */
    public static ZoneId getUserTimeZone() {
        return userTimeZone;
    }

    /**
     * setter for userTimeZone
     * @param userTimeZone
     */
    public static void setUserTimeZone(ZoneId userTimeZone) {
        LogOnModel.userTimeZone = userTimeZone;
    }
}
