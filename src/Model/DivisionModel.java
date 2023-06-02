package Model;

/**
 * First level division model class
 */
public class DivisionModel {
    /**
     * Division id
     */
    private int divisionID;
    /**
     * Division name
     */
    private String divisionName;
    /**
     * Country id
     */
    public int countryID;

    /**
     * Constructor for Division model
     * @param divisionID
     * @param divisionName
     * @param countryID
     */
    public DivisionModel(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    /**
     * getter for division id
     * @return
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * setter for division id
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
    /**
     *getter for division name
     * @return
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * setter for division name
     * @param divisionName
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }
    /**
     *getter for country id
     * @return
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Setter for country id
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
    public String toString(){
        return this.divisionName;}
}

