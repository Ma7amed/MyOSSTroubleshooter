package sample;

import java.util.ArrayList;

/**
 * Created by m80028770 on 11/6/2017.
 */
public class Utlities {

    public static String arrayToSQL(String[] array) {

        if (array.length==0) {
            return "";
        }

        String result = "'" + array[0] + "'";

        for(int i=1;i<array.length;i++) {
            result += ",'" + array[i] + "'";
        }

        return result;
    }


    public static String arrayToSQL(ArrayList<String> array) {

        if (array.size()==0) {
            return "";
        }

        String result = "'" + array.get(0) + "'";

        for(int i=1;i<array.size();i++) {
            result += ",'" + array.get(i) + "'";
        }

        return result;
    }

    public static String arrayToSQL_noquotes(ArrayList<String> array) {

        if (array.size()==0) {
            return "";
        }

        String result =  array.get(0) ;

        for(int i=1;i<array.size();i++) {
            result += "," + array.get(i);
        }

        return result;
    }

    public static ArrayList<String> arrayToSQL_Advance(ArrayList<String> array) {


        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();

        int start=1;
        int end;

        if(array.size()<=1000) {
            end = array.size();
        }else {
            end =1000;
        }

        // 1st
        for(int i=start-1;i<=end-1;i++) {
            temp.add(array.get(i));
        }
        result.add(arrayToSQL(temp));

        System.out.println("Start: " + (start-1) + ", End: " + (end-1));

        while(end +1000<=array.size()){
            start +=1000;
            end +=1000;

            // next
            temp.clear();
            for(int i=start-1;i<=end-1;i++) {
                temp.add(array.get(i));
            }
            result.add(arrayToSQL(temp));

            System.out.println("Start: " + (start-1) + ", End: " + (end-1));
        }

        if (array.size()%1000>0 && array.size()>1000) {
            start +=1000;
            end += array.size()%1000;

            // last
            temp.clear();
            for(int i=start-1;i<=end-1;i++) {
                temp.add(array.get(i));
            }
            result.add(arrayToSQL(temp));
            System.out.println("Start: " + (start-1) + ", End: " + (end-1));
        }
//
//
//        if (array.size()==0) {
//            return null;
//        }
//
//        String result = "'" + array.get(0) + "'";
//
//        for(int i=1;i<array.size();i++) {
//            result += ",'" + array.get(i) + "'";
//        }

        System.out.println("Result size: " + result.size());

        return result;
    }

}
