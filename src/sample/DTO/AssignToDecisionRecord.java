package sample.DTO;

/**
 * Created by m80028770 on 11/13/2017.
 */
public class AssignToDecisionRecord {

    private String domain;
    private String alarmName;
    private String assignTo;


    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public String getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(String assignTo) {
        this.assignTo = assignTo;
    }

    public AssignToDecisionRecord(String domain, String alarmName, String assignTo) {
        this.domain = domain;
        this.alarmName = alarmName;
        this.assignTo = assignTo;
    }

    @Override
    public String toString() {
        return "AssignToDecisionRecord{" +
                "domain='" + domain + '\'' +
                ", alarmName='" + alarmName + '\'' +
                ", assignTo='" + assignTo + '\'' +
                '}';
    }
}
