package Model;

/**
 * Country Model Class
 */
public class CountryModel {
    /**
     * Country ID
     */
    private int countryID;
    /**
     * Country Name
     */
    private String countryName;

    /**
     * Constructor for Country model class
     * @param countryID
     * @param countryName
     */
    public CountryModel(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**
     * Getter for country id
     * @return
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * setter for country id
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
    /**
     *getter for country name
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * setter for country name
     * @param countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public String toString(){
        return this.countryName;
    }
}
