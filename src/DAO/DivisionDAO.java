package DAO;

import Model.DivisionModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *Division Dao Class
 */
public class DivisionDAO extends DivisionModel {

    public DivisionDAO(int divisionID, String divisionName, int countryID) {
        super(divisionID, divisionName, countryID);
    }
    /**
     *Allows users to select all divisions from the data base
     */
    public static ObservableList<DivisionModel> getAll_F_L_D() throws SQLException{
        ObservableList<DivisionModel> firstLD_ObservableList = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int divisionID = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int countryID = rs.getInt("Country_ID");
            DivisionDAO firstLD = new DivisionDAO(divisionID, divisionName, countryID);
            firstLD_ObservableList.add(firstLD);
        }
        return  firstLD_ObservableList;
    }
}
