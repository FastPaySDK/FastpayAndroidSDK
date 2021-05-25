package com.fastpay.payment.service.utill;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormValidationUtil {

    private static FormValidationUtil formValidationUtil = new FormValidationUtil();

    private FormValidationUtil() {
    }

    public static FormValidationUtil getInstance() {
        return formValidationUtil;
    }

    public boolean isValidEmailAddress(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public boolean isValidMobileNumber(String number) {
        if (!TextUtils.isEmpty(number) && number.length() == 12) return true;
        else return false;
    }

    public boolean isNumeric(String target) {
        boolean number = false;
        try {
            Pattern p = Pattern.compile("[0-9]+");
            Matcher m = p.matcher(target);
            while (m.find()) {
                number = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return number;
    }

    public long getNumberFromString(String target) {
        long number = 0;
        try {
            Pattern p = Pattern.compile("[0-9]+");
            Matcher m = p.matcher(target);
            while (m.find()) {
                number = Long.parseLong(m.group());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return number;
    }

}
