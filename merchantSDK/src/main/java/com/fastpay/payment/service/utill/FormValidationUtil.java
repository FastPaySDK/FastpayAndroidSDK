package com.fastpay.payment.service.utill;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import com.fastpay.payment.R;
import com.fastpay.payment.model.common.PolicyDataModel;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormValidationUtil {

    private static FormValidationUtil formValidationUtil = new FormValidationUtil();

    private FormValidationUtil() {
    }

    public static FormValidationUtil getInstance() {
        return formValidationUtil;
    }

    public ArrayList<PolicyDataModel> checkPasswordPolicy(Context context, String password) {
        ArrayList<PolicyDataModel> policyDataModels = new ArrayList<>();

        // Length checking
        if (password.length() < 8)
            policyDataModels.add(new PolicyDataModel(context.getString(R.string.password_policy_length), false));
        else
            policyDataModels.add(new PolicyDataModel(context.getString(R.string.password_policy_length), true));

        // Uppercase
        if (!password.matches(".*[A-Z].*"))
            policyDataModels.add(new PolicyDataModel(context.getString(R.string.password_policy_caps_character), false));
        else
            policyDataModels.add(new PolicyDataModel(context.getString(R.string.password_policy_caps_character), true));

        // Lowercase
        if (!password.matches(".*[a-z].*"))
            policyDataModels.add(new PolicyDataModel(context.getString(R.string.password_policy_lower_character), false));
        else
            policyDataModels.add(new PolicyDataModel(context.getString(R.string.password_policy_lower_character), true));

        // Digit
        if (!password.matches(".*\\d.*"))
            policyDataModels.add(new PolicyDataModel(context.getString(R.string.password_policy_number_character), false));
        else
            policyDataModels.add(new PolicyDataModel(context.getString(R.string.password_policy_number_character), true));

        // Check special character
        if (!password.matches(".*[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?].*"))
            policyDataModels.add(new PolicyDataModel(context.getString(R.string.password_policy_special_character), false));
        else
            policyDataModels.add(new PolicyDataModel(context.getString(R.string.password_policy_special_character), true));

        return policyDataModels;
    }

    public boolean isPasswordPolicyMatched(ArrayList<PolicyDataModel> policyDataModel) {
        boolean isValid = true;
        for (PolicyDataModel model : policyDataModel) {
            if (!model.isValid()) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    public boolean isPinPolicyMatched(String pin) {
        return pin.length() == 4;
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
