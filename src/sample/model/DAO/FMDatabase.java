package sample.model.DAO;

import sample.DTO.Alarm;
import sample.DTO.TroubleTicket;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by m80028770 on 11/6/2017.
 */
public class FMDatabase {

    private final String USERNAME="inoc_sdm_servicedesk_1";
    private final String PASSWORD="DB_Service_!23";
    private final String IP="10.74.155.13";
    private final String PORT="1522";
    private final String SID="inoc";

    private Connection conn = null;


    ArrayList<Alarm> alarmList;



    public void getAlarmCount() {
        Connection con = null;
        Statement stmt;

        stmt = null;


        try {
            // Connect
            //Class.forName("oracle.jdbc.driver.OracleDriver");

            Class.forName("com.sybase.jdbc4.jdbc.SybDriver");
//            con = DriverManager.getConnection("jdbc:sybase:Tds:10.76.2.55:4100", "sa", "emsems");
            con = DriverManager.getConnection("jdbc:sybase:Tds:10.74.123.2:4110/POS1?charset=utf8", "root", "Fmos_001");
            System.out.println("connect: " + con);

            // Query

            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select count(*)  from status");
            while (rs.next()) {
                System.out.println("Count of TTs in POS1: " + rs.getString(1));
            }
            // ResultSet rs = stmt.executeQuery(sql_select_solly_tt);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                    sqlEx.printStackTrace();
                } // ignore

                stmt = null;
            }
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public ArrayList<Alarm> getAlarmDetails() {
        Connection con = null;
        Statement stmt;


        alarmList = new ArrayList<Alarm>();

        stmt = null;


        try {
            // Connect
            //Class.forName("oracle.jdbc.driver.OracleDriver");

            Class.forName("com.sybase.jdbc4.jdbc.SybDriver");
//            con = DriverManager.getConnection("jdbc:sybase:Tds:10.76.2.55:4100", "sa", "emsems");
            con = DriverManager.getConnection("jdbc:sybase:Tds:10.74.123.2:4110/POS1?charset=utf8", "root", "Fmos_001");
            System.out.println("connect: " + con);

            // Query

            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select ServerSerial,AlarmName,SiteName,TicketID,TicketStatus from status where TicketID!='' and AlarmName='Power Alarm'");
            while (rs.next()) {


                String serverSerial = rs.getString("ServerSerial");
                serverSerial = rs.wasNull()?"":serverSerial;

                String alarmName = rs.getString("AlarmName");
                alarmName = rs.wasNull()?"":alarmName;

                String siteName = rs.getString("SiteName");
                siteName = rs.wasNull()?"":siteName;

                String ticketID = rs.getString("TicketID").trim();
                ticketID = rs.wasNull()?"":ticketID;

                String ticketStatus = rs.getString("TicketStatus");
                ticketStatus = rs.wasNull()?"":ticketStatus;

                Alarm alarm = new Alarm(serverSerial,alarmName,siteName,ticketID,ticketStatus);

                alarmList.add(alarm);

//                System.out.println("Alarm: " + alarm);


            }

            System.out.println("Alarm Count: " + alarmList.size());


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                    sqlEx.printStackTrace();
                } // ignore

                stmt = null;
            }
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return alarmList;
    }


    public ArrayList<Alarm> getUnclearServerSerials() {
        Connection con = null;
        Statement stmt;


        alarmList = new ArrayList<Alarm>();

        stmt = null;


        try {
            // Connect
            //Class.forName("oracle.jdbc.driver.OracleDriver");

            Class.forName("com.sybase.jdbc4.jdbc.SybDriver");
//            con = DriverManager.getConnection("jdbc:sybase:Tds:10.76.2.55:4100", "sa", "emsems");
            con = DriverManager.getConnection("jdbc:sybase:Tds:10.74.123.2:4110/POS1?charset=utf8", "root", "Fmos_001");
            System.out.println("connect: " + con);

            // Query

            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select ServerSerial,AlarmName,SiteName,TicketID,TicketStatus from status where ProcessReq=0 and ( TicketID!='' or TicketStatus=3) order by TicketID;");
            while (rs.next()) {


                String serverSerial = rs.getString("ServerSerial").trim();
                serverSerial = rs.wasNull()?"":serverSerial;

                String alarmName = rs.getString("AlarmName");
                alarmName = rs.wasNull()?"":alarmName;

                String siteName = rs.getString("SiteName");
                siteName = rs.wasNull()?"":siteName;

                String ticketID = rs.getString("TicketID").trim();
                ticketID = rs.wasNull()?"":ticketID;

                String ticketStatus = rs.getString("TicketStatus");
                ticketStatus = rs.wasNull()?"":ticketStatus;

                Alarm alarm = new Alarm(serverSerial,alarmName,siteName,ticketID,ticketStatus);

                alarmList.add(alarm);

//                System.out.println("Alarm: " + alarm);


            }

            System.out.println("Alarm Count: " + alarmList.size());


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                    sqlEx.printStackTrace();
                } // ignore

                stmt = null;
            }
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return alarmList;
    }


}
