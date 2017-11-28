package sample;

import sample.DTO.Alarm;
import sample.DTO.AssignToDecisionRecord;
import sample.DTO.AssignToFLEStatRecord;
import sample.DTO.TroubleTicket;
import sample.model.DAO.FMDatabase;
import sample.model.DAO.SDMDatabase;
import sample.model.TroubleShooter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by m80028770 on 11/6/2017.
 */
public class Launcher {

    public static void main2(String[] args) {

//        TroubleShooter troubleShooter = new TroubleShooter();
//        troubleShooter.checkFMAlarmWithWrongStatus();




    }

    public static void main(String[] args) {
        TroubleShooter troubleShooter = new TroubleShooter();
//        troubleShooter.checkFMAlarmWithWrongStatus();
        troubleShooter.getUnclearTTs();
//            troubleShooter.getTTFLE(1);
//

    }

}
