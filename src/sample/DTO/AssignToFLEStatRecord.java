package sample.DTO;

/**
 * Created by m80028770 on 11/13/2017.
 */
public class AssignToFLEStatRecord {

    private String count;
    private String alarmName;
    private String alarmID;
    private String domain;

    public AssignToFLEStatRecord(String count, String alarmName, String alarmID, String domain) {
        this.count = count;
        this.alarmName = alarmName;
        this.alarmID = alarmID;
        this.domain = domain;
    }

    public String getAlarmID() {
        return alarmID;
    }

    public void setAlarmID(String alarmID) {
        this.alarmID = alarmID;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return "AssignToFLEStatRecord{" +
                "count='" + count + '\'' +
                ", alarmName='" + alarmName + '\'' +
                ", domain='" + domain + '\'' +
                '}';
    }
}
