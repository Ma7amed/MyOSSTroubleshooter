package sample.model;

import sample.DTO.Alarm;
import sample.DTO.AssignToDecisionRecord;
import sample.DTO.AssignToFLEStatRecord;
import sample.DTO.TroubleTicket;
import sample.model.DAO.FMDatabase;
import sample.model.DAO.SDMDatabase;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by m80028770 on 11/6/2017.
 */
public class TroubleShooter {

    public void checkFMAlarmWithWrongStatus() {
        // Get List of Tickets with wrong ticketstatus in FM
        // Also get List of ServerSerials that need to change status in FM to fix the issue

        // Get list of alarms with ticket ID
        ArrayList<Alarm> alarmList;

        FMDatabase fmDatabase = new FMDatabase();
        alarmList = fmDatabase.getAlarmDetails();


        // Get their stats from SDM
        HashMap<String,String> distinctTicketID = new HashMap<>();

        for(Alarm alarm:alarmList) {
            if(!distinctTicketID.containsKey(alarm.getTicketID())) {
                distinctTicketID.put(alarm.getTicketID(),alarm.getTicketID());
            }
        }

        System.out.println("found distinct ticket ids: " + distinctTicketID.size());

        Collection<String> values = distinctTicketID.values();

        ArrayList<String> tickets = new ArrayList<>(values);


        ArrayList<TroubleTicket> ticketList = new ArrayList<>();

        SDMDatabase sdmDatabase = new SDMDatabase();
        ticketList = sdmDatabase.queryTicketByID(tickets);


        HashMap<String,TroubleTicket> ticketMap = new HashMap<>();

        for(TroubleTicket ticket: ticketList) {
            ticketMap.put(ticket.getOrderid(),ticket);
        }

        System.out.println("Ticket Map of size: " + ticketMap.size());


        System.out.println("checking to get first alarm data");


        // Compare SDM with FM

        // Loop all alarms, and get ticket status from SDM
        // if sdm status is completed, cancel >> but in FM not (7, 8, 102 )

        ArrayList<Alarm> alarmsWrongStatus = new ArrayList<>();
        ArrayList<Alarm> alarmsTTnotFound = new ArrayList<>();


        for(Alarm alarm:alarmList) {
//            System.out.println("Checking alarm: " + alarm);
            String ticketID = alarm.getTicketID();
            String alarmTicketStatus = alarm.getTicketStatus();
            TroubleTicket ticket = ticketMap.get(ticketID);
            if(ticket == null) {
                System.out.println("TT not found in SDM: " + ticketID + ", alarm: " + alarm);
                alarmsTTnotFound.add(alarm);
                continue;
            }
            String ticketStatus = ticket.getProcessstatus();
            if(ticketStatus.equals("completed") && !alarmTicketStatus.equals("7") && !alarmTicketStatus.equals("8") & !alarmTicketStatus.equals("102")){
                System.out.println("Wrong TT: " + alarm);
                alarmsWrongStatus.add(alarm);
            }
        }
        System.out.println("Found " + alarmsTTnotFound.size() + " alarms with no TT found in SDM " + alarmList.size() + " alarms");

        System.out.println("############ Alarms with TT not found in SDM ############");
        System.out.println("############ Use below SQL Condition ############");

        ArrayList<String> testArray = new ArrayList<>();
        for(Alarm alarm:alarmsTTnotFound) {
            testArray.add(alarm.getServerSerial());
        }
//        Set<String> testSet = new HashSet<>();
//        testSet.addAll(testArray);
//        testArray.clear();
//        testArray.addAll(testSet);

        System.out.println("update alerts.status set TicketStatus=7 where ServerSerial in (" + sample.Utlities.arrayToSQL_noquotes(testArray)+")");
        System.out.println("########################################################");

        System.out.println();
        System.out.println("Found " + alarmsWrongStatus.size() + " alarms with wrong status out of " + alarmList.size() + " alarms");
        System.out.println("############ Alarms with wrong Status in FM ############");
        System.out.println("############ Use below SQL Condition ############");

        testArray.clear();
        for(Alarm alarm:alarmsWrongStatus) {
            testArray.add(alarm.getServerSerial());
        }
//        Set<String> testSet = new HashSet<>();
//        testSet.addAll(testArray);
//        testArray.clear();
//        testArray.addAll(testSet);

        System.out.println("update alerts.status set TicketStatus=7 where ServerSerial in (" + sample.Utlities.arrayToSQL_noquotes(testArray)+")");
        System.out.println("########################################################");




    }

    public void getUnclearTTs() {

        // INIT

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime localDateTime = LocalDateTime.now();
        String currentDate = localDateTime.format(formatter);

        ExcelWritter excelWritter = new ExcelWritter();
        File filePath = new File("D:/Work/OSS/Temp_Delete/" + currentDate + "/");
        File filename = new File(filePath + "/Clear_TT.xlsx");
        System.out.println("will export to: " + filename);
        // Get Unclear TTs

        // Get List of Alarms that are not cleared yet, or cleared but didn't send clear notification to SDM
        // So any running TT in this list,, will be considered unclear and ok
        // out of this list means it is already cleared in FM but TT didn' receive clear notification
        ArrayList<Alarm> unClearAlarms = new ArrayList<>();

        FMDatabase fmDatabase = new FMDatabase();
        unClearAlarms = fmDatabase.getUnclearServerSerials();

        HashMap<String,Alarm> unClearAlarmsMap = new HashMap<>();
        for(Alarm alarm: unClearAlarms) {
            if(!unClearAlarmsMap.containsKey(alarm.getServerSerial())){
//                System.out.println("adding \"" + alarm.getServerSerial() + "\" to map");
                unClearAlarmsMap.put(alarm.getServerSerial(),alarm);
            }
        }

        System.out.println("Unclear Map size: " + unClearAlarmsMap.size());

        ArrayList<TroubleTicket> runningTTs = new ArrayList<>();

        SDMDatabase sdmDatabase = new SDMDatabase();
        runningTTs = sdmDatabase.getFMRunngingUnclearTTs();

        ArrayList<TroubleTicket> unClearedTTs = new ArrayList<>();

        for(TroubleTicket ticket:runningTTs) {
            System.out.println("Checking for ticket with serverserial: " + ticket.getServerSerial());
            if(!unClearAlarmsMap.containsKey(ticket.getServerSerial())) {
                unClearedTTs.add(ticket);
            }
        }

        System.out.println("Found " + unClearedTTs.size() + " unclear TTs out of " + runningTTs.size() + " running TTs");
        System.out.println("Save this data as csv file");
        System.out.println("###################");
        System.out.println("Ticket ID,Status,ServerSerial");
        for(TroubleTicket ticket:unClearedTTs) {
//            System.out.println(ticket);
            System.out.println(ticket.getOrderid() + "," + ticket.getProcessstatus() + "," + ticket.getServerSerial());
        }
        System.out.println("Found " + unClearedTTs.size() + " unclear TTs out of " + runningTTs.size() + " running TTs");



        System.out.println("Writing to " + filename);
        excelWritter.writeClearTT(unClearedTTs,filename);

    }

    public void getTTFLE(int numberOfDays) {
        // Check TT assign to FLE
        // Get list of configured assign to rules

        ArrayList<AssignToDecisionRecord> ruleList = new ArrayList<>();
        SDMDatabase sdmDatabase = new SDMDatabase();
        ruleList = sdmDatabase.getAssignToDecisionRules();

        // Create Assign to Decision Map by "AlarmName_Domain"

        HashMap<String,AssignToDecisionRecord> assignToDecisionMap = new HashMap<>();
        for(AssignToDecisionRecord record:ruleList) {
            String key = record.getAlarmName() + "_" + record.getDomain();
            if(!assignToDecisionMap.containsKey(key)) {
                assignToDecisionMap.put(key,record);
            }else {
                System.out.println("ERROR: Record duplicated: " + record);
            }
        }

        // Map Created

        ArrayList<AssignToFLEStatRecord> toFLEList = new ArrayList<>();
        toFLEList = sdmDatabase.getAlarmToFLEbyTimePeriod(numberOfDays);


        ArrayList<AssignToFLEStatRecord> newRequiredRecords = new ArrayList<>();
        ArrayList<AssignToFLEStatRecord> problematicRecords = new ArrayList<>();

        for(AssignToFLEStatRecord record:toFLEList) {
            String alarmName = record.getAlarmName();
            String count = record.getCount();
            String domain = record.getDomain();

            // Check if rule not exist,,,
            if(assignToDecisionMap.containsKey(alarmName + "_" + domain)) {
                // this problematic one as assign to already exist in SDM but assign to FLE
                problematicRecords.add(record);
            }else {
                // this new rule need to be created
                newRequiredRecords.add(record);
            }
        }

        System.out.println("Found " + newRequiredRecords.size() + " new rules need to be created");
        System.out.println("##########################");
        System.out.println("Domain,Alarm Name,Alarm ID,TT Count");
        for(AssignToFLEStatRecord record:newRequiredRecords) {
            System.out.println(record.getDomain() + "," + record.getAlarmName() + "," + record.getAlarmID() + "," + record.getCount());
        }
        System.out.println("##########################");
        System.out.println();

        System.out.println("Found " + problematicRecords.size() + " rules need to check");
        System.out.println("##########################");
        System.out.println("Domain,Alarm Name,Count");
        for(AssignToFLEStatRecord record:problematicRecords) {
            System.out.println(record.getDomain() + "," + record.getAlarmName() + "," + record.getCount());
        }
        System.out.println("##########################");


    }

}
