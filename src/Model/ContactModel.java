package Model;

public class ContactModel {
    /**
     * Contact name
     */
    public String contactName;
    /**
     * Contact id
     */
    public int contactID;
    /**
     *Contact email
     */
    public String contactE_mail;
    /**
     *ContactModel constructor
     */
    public ContactModel(String contactName, int contactID, String contactE_mail) {
        this.contactName = contactName;
        this.contactID = contactID;
        this.contactE_mail = contactE_mail;
    }
    public ContactModel( int ContactID, String contactName){
        this.contactName = contactName;
        this.contactID = ContactID;
    }
    /**
     *getter for contact name
     */
    public String getContactName() {
        return contactName;
    }
    /**
     *setter for contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * getter for contact id
     * @return
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * setter for contact id
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Getter for contact email
     * @return
     */
    public String getContactE_mail() {
        return contactE_mail;
    }

    /**
     * setter for contact email
     * @param contactE_mail
     */
    public void setContactE_mail(String contactE_mail) {
        this.contactE_mail = contactE_mail;
    }

    /**
     * to string method for contact name
     * @return
     */
    public String toString() {
        return contactName;
    }
}
