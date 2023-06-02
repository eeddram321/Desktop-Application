package Model;

/**
 * Report type model class
 */
public class ReportTypeModel {
    /**
     * appointment total
     */
    public int appointment_Total;
    /**
     * appointment type
     */
    public String appointmentType;

    /**
     * Report Type Constructor
     * @param appointment_Total
     * @param appointmentType
     */
    public ReportTypeModel(int appointment_Total, String appointmentType) {
        this.appointment_Total = appointment_Total;
        this.appointmentType = appointmentType;
    }

    /**
     * Appointment Total Getter
     * @return
     */
    public int getAppointment_Total() {
        return appointment_Total;
    }

    /**
     * Appointment Total Setter
     * @param appointment_Total
     */
    public void setAppointment_Total(int appointment_Total) {
        this.appointment_Total = appointment_Total;
    }

    /**
     * Appointment Type Getter
     * @return
     */
    public String getAppointmentType() {
        return appointmentType;
    }

    /**
     * Appointment Type Setter
     * @param appointmentType
     */
    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }
}
