package com.afomic.tradeapp.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by afomic on 2/7/18.
 */

public class DateUtil {
    private static final String DATE_PATTERN_WITHIN_A_DAY="hh:mm";
    private static final String DATE_PATTERN_WITHIN_A_WEEK="EEE";
    private static final String DATE_PATTERN_WITHIN_A_MONTH="MMM d";
    private static final String DATE_PATTERN_WITHIN_A_YEAR="dd/mm/yyyy";
    private static long A_DAY=1000*3600*24;
    private static long A_WEEK=A_DAY*7;
    private static long A_MONTH=A_WEEK*4;
    private static long A_YEAR=A_MONTH*12;



    public static String formatDate(long timeStamp){
        long timeDifference=System.currentTimeMillis()-timeStamp;
        Date date=new Date(timeStamp);
        String datePattern;
        if(timeDifference<A_DAY){
            datePattern=DATE_PATTERN_WITHIN_A_DAY;
        }else if(timeDifference<A_WEEK){
            datePattern=DATE_PATTERN_WITHIN_A_WEEK;
        }else if(timeDifference>A_YEAR){
            datePattern=DATE_PATTERN_WITHIN_A_YEAR;
        }else {
            datePattern=DATE_PATTERN_WITHIN_A_MONTH;
        }
        SimpleDateFormat dateFormat=new SimpleDateFormat(datePattern, Locale.ENGLISH);
        return dateFormat.format(date);

    }
}
