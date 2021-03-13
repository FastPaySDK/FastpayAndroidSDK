package com.fastpay.payment.service.controller;

import android.app.Activity;
import android.util.Log;

import com.fastpay.payment.model.response.BaseResponseModel;


public class BaseController {

    protected boolean checkBasicApiResponse(Activity activity, BaseResponseModel model) {
        if (model.getCode() == 200) {
            return true;
        } else if (model.getCode() == 400) {
            return false;
        }
        return false;
    }
}
