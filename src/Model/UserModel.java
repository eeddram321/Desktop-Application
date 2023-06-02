package Model;

/**
 * User model class
 */
public class UserModel {
    /**
     * user name
     */
    private String userName;
    /**
     * user id
     */
    private int userID;
    /**
     * user password
     */
    private String userPassword;

    /**
     * User constructor
     * @param userName
     * @param userID
     * @param userPassword
     */

    public UserModel(String userName, int userID, String userPassword) {
        this.userName = userName;
        this.userID = userID;
        this.userPassword = userPassword;
    }

    public UserModel(String userName, int userID) {
        this.userName = userName;
        this.userID = userID;

    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * get User Name method;
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     * User Name setter
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * User ID getter
     * @return
     */
    public int getUserID() {
        return userID;
    }

    /**
     * User ID setter
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "[" + userID + "]" ;
    }
}

