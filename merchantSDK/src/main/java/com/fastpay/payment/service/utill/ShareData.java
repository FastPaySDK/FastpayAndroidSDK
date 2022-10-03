package com.fastpay.payment.service.utill;

import com.fastpay.payment.BuildConfig;

public class ShareData {

    // Constant text
    public static final String DATE = "Date:";
    public static final String CURRENCY_IQD = "IQD";

    public static String COUNTRY_CODE = "+964";
    public static String ENGLISH_LANG = "en";
    public static String ARABIC_LANG = "ar";
    public static String KURDISH_LANG = "ku";

    public static final int PERMISSION_REQUEST_CODE = 100;
    public static final String KEY_FINISHING_TIME = "key_finishing_time";
    public static final int PAYMENT_TERMS_TIME_OUT = 188;

    public static long SESSION_TIME_OUT_VALUE = BuildConfig.DEBUG?10000:(1000 * 60 * 4);


}