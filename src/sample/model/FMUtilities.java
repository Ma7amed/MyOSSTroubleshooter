package sample.model;

/**
 * Created by m80028770 on 11/6/2017.
 */
public class FMUtilities {

    public String convertTicketStatus(String ticketStatus) {

        switch (ticketStatus) {
            case "100":
                return "aborted";
            case "2":
                return "B";

        }

        return "";
    }
}
