package com.fastpay.payment.service.utill;

import android.content.Context;
import android.content.SharedPreferences;

public class StoreInformationUtil {

    private static String PREF_NAME = "fast_pay_sdk_pref";

    private static SharedPreferences getPref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public static boolean saveLongData(Context context, String key, Long data) {
        SharedPreferences.Editor editor = getPref(context).edit();
        editor.putLong(key, data);
        return editor.commit();
    }


    public static Long getLongData(Context context, String key,Long defaultValue) {
        SharedPreferences sharedPreferences = getPref(context);
        return sharedPreferences.getLong(key, defaultValue);
    }

    public static void clearKey(Context context,String key){
        SharedPreferences.Editor editor = getPref(context).edit();
        editor.remove(key);
        editor.apply();
    }
}