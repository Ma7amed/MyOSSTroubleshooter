package sample.model.DAO;


import sample.DTO.AssignToDecisionRecord;
import sample.DTO.AssignToFLEStatRecord;
import sample.DTO.TroubleTicket;
import sample.Utlities;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class SDMDatabase {

    private final String USERNAME="inoc_sdm_servicedesk_1";
    private final String PASSWORD="DB_Service_!23";
    private final String IP="10.74.155.13";
    private final String PORT="1522";
    private final String SID="inoc";

//    ArrayList<SollyTicket> ticketList;
//    ArrayList<SollyWorkOrder> workOrderList;



    private Connection conn = null;


    ArrayList<TroubleTicket> ticketList;


    public ArrayList<TroubleTicket> queryTicketByID(ArrayList<String> ticketIDs)  {

        String query_sql="";
        ArrayList<String> ticketStringList = sample.Utlities.arrayToSQL_Advance(ticketIDs);

        if (ticketStringList.size()==1) {
            query_sql
                    = "select " +
                    "t.orderid, " +
                    "t.processstatus " +
                    "from " +
                    "TBL_TROUBLETICKET_DATASOURCE t " +
                    "where " +
                    "t.orderid in " +
                    "(" +
                    Utlities.arrayToSQL(ticketIDs) +
                    ")";
        }else {
            query_sql
                    = "select " +
                    "t.orderid, " +
                    "t.processstatus " +
                    "from " +
                    "TBL_TROUBLETICKET_DATASOURCE t " +
                    "where " +
                    "t.orderid in " +
                    "(" +
                    ticketStringList.get(0) +
                    ")";
            for(int i=1;i<ticketStringList.size();i++) {
                query_sql += " or t.orderid in " +
                        "(" +
                        ticketStringList.get(i) +
                        ")";
            }
        }

        System.out.println("SDMDatabase.queryTicketByID, query_sql: " + query_sql);

        ticketList = new ArrayList<TroubleTicket>();
        Statement stmt= null;

        try {
            // Connect
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn=DriverManager.getConnection(
                    "jdbc:oracle:thin:@" + IP + ":" + PORT +":" + SID,USERNAME,PASSWORD);

            // Query

            stmt = conn.createStatement();
            ResultSet rs=stmt.executeQuery(query_sql);

            // Get Results
            while(rs.next()) {

                String orderid = rs.getString("orderid");
                orderid = rs.wasNull()?"":orderid;

                String processstatus = rs.getString("processstatus");
                processstatus = rs.wasNull()?"":processstatus;



                TroubleTicket ticket = new TroubleTicket(orderid,processstatus);

//                System.out.println("SDMDatabase.queryTicketsHandling: " + ticket);
                ticketList.add(ticket);
            }
//            System.out.println("SDMDatabase.queryTicketsHandling, TicketCount: " + ticketList.size());
            //rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return ticketList;

    }


    public ArrayList<TroubleTicket> getFMRunngingUnclearTTs()  {

        String query_sql="";


        query_sql
                = "select " +
                "orderid, " +
                "processstatus, " +
                "title_, " +
                "substr(faultno_,1,instr(FAULTNO_,'_POS')-1) \"ServerSerial\" " +
                "from " +
                "TBL_TROUBLETICKET_DATASOURCE " +
                "where " +
                "PROCESSSTATUS='running' " +
                "and FAULTRECOVERYTIME_ is null " +
                "and Ticketsource_='FMSystem' " +
                "and createtime < (sysdate -3/24)";


        System.out.println("SDMDatabase.queryTicketByID, query_sql: " + query_sql);

        ticketList = new ArrayList<TroubleTicket>();
        Statement stmt= null;

        try {
            // Connect
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn=DriverManager.getConnection(
                    "jdbc:oracle:thin:@" + IP + ":" + PORT +":" + SID,USERNAME,PASSWORD);

            // Query

            stmt = conn.createStatement();
            ResultSet rs=stmt.executeQuery(query_sql);

            // Get Results
            while(rs.next()) {

                String orderid = rs.getString("orderid");
                orderid = rs.wasNull()?"":orderid;

                String processstatus = rs.getString("processstatus");
                processstatus = rs.wasNull()?"":processstatus;

                String serverSerial = rs.getString("ServerSerial");
                serverSerial = rs.wasNull()?"":serverSerial;



                TroubleTicket ticket = new TroubleTicket(orderid,processstatus,serverSerial);

//                System.out.println("SDMDatabase.queryTicketsHandling: " + ticket);
                ticketList.add(ticket);
            }
//            System.out.println("SDMDatabase.queryTicketsHandling, TicketCount: " + ticketList.size());
            //rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return ticketList;

    }


    public ArrayList<AssignToDecisionRecord> getAssignToDecisionRules()  {

        String query_sql="";


        query_sql
                = "select  " +
                "(select option_label from INOC_SDM_SDMREPORT_1.V_REPORT_DS_ELSE d where d.DATASOURCE_ID='Domain' and d.option_id=s.domain) \"Domain\", " +
                "s.ALARM_NAME, " +
                "s.ASSIGN_TO_NAME " +
                "from " +
                "INOC_SDM_SERVICEDESK_1.T_INTG_WB_ASSIGNEDTODECISION s";


        System.out.println("SDMDatabase.getAssignToDecisionRules, query_sql: " + query_sql);

        ArrayList<AssignToDecisionRecord> ruleList = new ArrayList<AssignToDecisionRecord>();
        Statement stmt= null;

        try {
            // Connect
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn=DriverManager.getConnection(
                    "jdbc:oracle:thin:@" + IP + ":" + PORT +":" + SID,USERNAME,PASSWORD);

            // Query

            stmt = conn.createStatement();
            ResultSet rs=stmt.executeQuery(query_sql);

            // Get Results
            while(rs.next()) {

                String domain = rs.getString("domain");
                domain = rs.wasNull()?"":domain;

                String alarmName = rs.getString("alarm_name");
                alarmName = rs.wasNull()?"":alarmName;

                String assignTo = rs.getString("Assign_to_name");
                assignTo = rs.wasNull()?"":assignTo;



                AssignToDecisionRecord rule = new AssignToDecisionRecord(domain,alarmName,assignTo);

//                System.out.println("SDMDatabase.getAssignToDecisionRules: " + rule);
                ruleList.add(rule);
            }
            System.out.println("SDMDatabase.getAssignToDecisionRules, Rule Count: " + ruleList.size());
            //rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return ruleList;

    }


    public ArrayList<AssignToFLEStatRecord> getAlarmToFLEbyTimePeriod(int numberOfDays)  {

        String query_sql="";


        query_sql
                = "  select  " +
                "    count(*) \"Count\", " +
                "  t.alarmname_ \"Alarm_Name\", " +
                "  t.alarmid_ \"Alarm_ID\", " +
                "  ds.OPTION_LABEL \"Domain\" " +
                "  from TBL_TROUBLETICKET_DATASOURCE t " +
                "  left join INOC_SDM_SDMREPORT_1.V_REPORT_DS_ELSE ds on (ds.DATASOURCE_ID='Domain' and ds.option_id=t.DOMAIN_)\n" +
                "  where " +
                "  CREATEASSIGNTO_ ='group:7000' " +
                "  and t.CREATETIME+2/24 > (sysdate-" + numberOfDays + ") " +
                "  group by t.alarmname_,t.alarmid_,ds.OPTION_LABEL\n" +
                "  order by 1 desc";


        System.out.println("SDMDatabase.getAlarmToFLEbyTimePeriod, query_sql: " + query_sql);

        ArrayList<AssignToFLEStatRecord> recordList = new ArrayList<>();
        Statement stmt= null;

        try {
            // Connect
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn=DriverManager.getConnection(
                    "jdbc:oracle:thin:@" + IP + ":" + PORT +":" + SID,USERNAME,PASSWORD);

            // Query

            stmt = conn.createStatement();
            ResultSet rs=stmt.executeQuery(query_sql);

            // Get Results
            while(rs.next()) {

                String count = rs.getString("count");
                count = rs.wasNull()?"":count;

                String alarmName = rs.getString("alarm_name");
                alarmName = rs.wasNull()?"":alarmName;

                String alarmID = rs.getString("alarm_id");
                alarmID = rs.wasNull()?"":alarmID;

                String domain = rs.getString("domain");
                domain = rs.wasNull()?"":domain;



                AssignToFLEStatRecord record = new AssignToFLEStatRecord(count,alarmName,alarmID,domain);

//                System.out.println("SDMDatabase.getAssignToDecisionRules: " + rule);
                recordList.add(record);
            }
            System.out.println("SDMDatabase.getAlarmToFLEbyTimePeriod, Record Count: " + recordList.size());
            //rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return recordList;

    }


}
