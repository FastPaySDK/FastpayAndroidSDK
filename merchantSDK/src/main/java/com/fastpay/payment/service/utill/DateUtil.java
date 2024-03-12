package com.fastpay.payment.service.utill;

import android.util.Log;

import com.fastpay.payment.BuildConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Sahidul Islam on 27-Jul-20.
 */

public class DateUtil {


    public static String getDateFormat(String date) {
        String[] date_formats = {
                "yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd'T'HH:mm:ssZ",
                "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd HH:mm:ss",
                "MM/dd/yyyy HH:mm:ss", "MM/dd/yyyy'T'HH:mm:ss.SSS'Z'",
                "MM/dd/yyyy'T'HH:mm:ss.SSSZ", "MM/dd/yyyy'T'HH:mm:ss.SSS",
                "MM/dd/yyyy'T'HH:mm:ssZ", "MM/dd/yyyy'T'HH:mm:ss",
                "yyyy:MM:dd HH:mm:ss", "yyyyMMdd",};

        for (String format : date_formats) {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
            try {
                sdf.parse(date);
                return format;
            } catch (ParseException e) {
                if(BuildConfig.DEBUG){
                    Log.e("Date", "Error in Parsing Time : " + e.getMessage());
                }
            }
        }
        return "";
    }

    public static String getTargetDayDate(String currentDate, boolean isPrevious) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date newDate = null;
        try {
            newDate = dateFormat.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(newDate);

        int amount = isPrevious ? -1 : +1;
        calendar.add(Calendar.DATE, amount);
        return dateFormat.format(calendar.getTime());
    }

    public static String getTargetMonthDate(String currentDate, boolean isPrevious) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date newDate = null;
        try {
            newDate = dateFormat.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(newDate);

        int amount = isPrevious ? -1 : +1;
        calendar.add(Calendar.MONTH, amount);
        return dateFormat.format(calendar.getTime());
    }

    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }

    public static String getFormatedDate(String date) {
        Date newDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss a", Locale.ENGLISH);
            newDate = sdf.parse(date);
        } catch (ParseException e) {
            if(BuildConfig.DEBUG){
                Log.e("Date", "Error in Parsing Time : " + e.getMessage());
            }
        }

        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(newDate);

        return newDateFormat.format(calendar.getTime());
    }

    public static String getFormatedTime(String date) {
        Date newDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss a", Locale.ENGLISH);
            newDate = sdf.parse(date);
        } catch (ParseException e) {
            if(BuildConfig.DEBUG){
                Log.e("Date", "Error in Parsing Time : " + e.getMessage());
            }
        }

        SimpleDateFormat newDateFormat = new SimpleDateFormat("hh:mm:ss a", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(newDate);

        return newDateFormat.format(calendar.getTime());
    }

    public static String getFormatedNotificationDate(String date) {
        Date newDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            newDate = sdf.parse(date);
        } catch (ParseException e) {
            if(BuildConfig.DEBUG){
                Log.e("Date", "Error in Parsing Time : " + e.getMessage());
            }
        }

        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(newDate);

        return newDateFormat.format(calendar.getTime());
    }
}
