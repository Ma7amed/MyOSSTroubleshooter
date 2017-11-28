package sample.DTO;

/**
 * Created by m80028770 on 11/6/2017.
 */
public class Alarm {
    private String serverSerial;
    private String alarmName;
    private String siteName;
    private String ticketID;
    private String ticketStatus;


    public Alarm(String serverSerial, String alarmName, String siteName, String ticketID, String ticketStatus) {
        this.serverSerial = serverSerial;
        this.alarmName = alarmName;
        this.siteName = siteName;
        this.ticketID = ticketID;
        this.ticketStatus = ticketStatus;
    }

    public String getServerSerial() {
        return serverSerial;
    }

    public void setServerSerial(String serverSerial) {
        this.serverSerial = serverSerial;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "serverSerial='" + serverSerial + '\'' +
                ", alarmName='" + alarmName + '\'' +
                ", siteName='" + siteName + '\'' +
                ", ticketID='" + ticketID + '\'' +
                ", ticketStatus='" + ticketStatus + '\'' +
                '}';
    }
}
