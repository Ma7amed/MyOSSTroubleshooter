package sample.DTO;

/**
 * Created by m80028770 on 11/6/2017.
 */
public class TroubleTicket {

    private String orderid;
    private String processstatus;
    private String serverSerial;
    private String title;


    public static String[] HEADERS = {
            "Ticket ID",
            "Title",
            "Status",
            "Server Serial Number"
    };


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getServerSerial() {
        return serverSerial;
    }

    public void setServerSerial(String serverSerial) {
        this.serverSerial = serverSerial;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getProcessstatus() {
        return processstatus;
    }

    public void setProcessstatus(String processstatus) {
        this.processstatus = processstatus;
    }


    public TroubleTicket(String orderid, String processstatus) {
        this.orderid = orderid;
        this.processstatus = processstatus;
    }

    public TroubleTicket(String orderid, String processstatus, String serverSerial) {
        this.orderid = orderid;
        this.processstatus = processstatus;
        this.serverSerial = serverSerial;
    }

    public TroubleTicket(String orderid, String processstatus, String serverSerial, String title) {
        this.orderid = orderid;
        this.processstatus = processstatus;
        this.serverSerial = serverSerial;
        this.title = title;
    }

    @Override
    public String toString() {
        return "TroubleTicket{" +
                "orderid='" + orderid + '\'' +
                ", processstatus='" + processstatus + '\'' +
                ", serverSerial='" + serverSerial + '\'' +
                '}';
    }
}
