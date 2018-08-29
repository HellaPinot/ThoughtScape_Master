package com.hellapinot.thomassmith.thoughtscape_master;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {

    private static final Date EPOCH = new GregorianCalendar(2018, 06,19).getTime();
    private static String strDateFormat = "dd-MMM-yyyy";
    private static SimpleDateFormat dFormat = new SimpleDateFormat(strDateFormat, Locale.ENGLISH);
    private static final String TAG = "DateUtil";



    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }


    public static String getDate(int addDaysVal){
        return dFormat.format(addDays(getEPOCH(),addDaysVal));
    }


    public static int DaysSinceEpoch(){
        Calendar sDate = toCalendar(EPOCH.getTime());
        Calendar eDate = toCalendar(System.currentTimeMillis());

        // Get the represented date in milliseconds
        long milis1 = sDate.getTimeInMillis();
        long milis2 = eDate.getTimeInMillis();

        // Calculate difference in milliseconds
        long diff = Math.abs(milis2 - milis1);

        return (int)(diff / (24 * 60 * 60 * 1000));
    }

    public static int daysFromToday(String aDate){
        Date toDate = null;
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        try {
            toDate = formatter1.parse(aDate);
        } catch(ParseException e){
            Log.d(TAG, "daysFromToday: invalid date parsed");
        }
        Calendar sDate = toCalendar(new Date().getTime());
        Calendar eDate = toCalendar(toDate.getTime());

        // Get the represented date in milliseconds
        long milis1 = sDate.getTimeInMillis();
        long milis2 = eDate.getTimeInMillis();

        // Calculate difference in milliseconds
        long diff = Math.abs(milis2 - milis1);
        return (int)(diff / (24 * 60 * 60 * 1000));
    }

    

    public static int daysBetween(String fDate, String tDate){
        Date toDate = null;
        Date fromDate = null;
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        try {
            toDate = formatter1.parse(tDate);
            fromDate = formatter1.parse(fDate);
        } catch(ParseException e){
            Log.d(TAG, "daysFromToday: invalid date parsed");
        }
        Calendar sDate = toCalendar(fromDate.getTime());
        Calendar eDate = toCalendar(toDate.getTime());

        // Get the represented date in milliseconds
        long milis1 = sDate.getTimeInMillis();
        long milis2 = eDate.getTimeInMillis();

        // Calculate difference in milliseconds
        long diff = Math.abs(milis2 - milis1);
        return (int)(diff / (24 * 60 * 60 * 1000));
    }



    private static Calendar toCalendar(long timestamp)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }



    public static Date getEPOCH() {
        return EPOCH;
    }



}
