package com.fastpay.payment.service.network.request;

import android.content.Context;

import com.fastpay.payment.R;
import com.fastpay.payment.model.merchant.FastpayRequest;
import com.fastpay.payment.model.merchant.FastpaySDK;
import com.fastpay.payment.model.response.BaseResponseModel;
import com.fastpay.payment.service.listener.InitiationApiListener;
import com.fastpay.payment.service.listener.SendOtpListener;
import com.fastpay.payment.service.network.http.BaseHttp;
import com.fastpay.payment.service.network.http.HttpParams;
import com.fastpay.payment.service.network.parser.BaseResponseParser;
import com.fastpay.payment.service.network.parser.InitiationSuccessParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Created By Zarraf Ahmed
 * Created 8/10/2023 at 4:44 PM
 */
public class SendOtpRequestModel extends BaseHttp {

    private WeakReference<Context> mContext;
    private BaseResponseModel responseModel;
    private SendOtpListener listener;


    public SendOtpRequestModel(Context context, String environment) {
        super(context, environment.equals(FastpaySDK.PRODUCTION) ? HttpParams.PRODUCTION_URL : HttpParams.SANDBOX_URL
                + HttpParams.API_VERSION + HttpParams.SEND_OTP);
        mContext = new WeakReference<>(context);
    }

    public SendOtpRequestModel(Context context, String environment,int type) {
        super(context, environment.equals(FastpaySDK.PRODUCTION) ? HttpParams.PRODUCTION_URL : HttpParams.SANDBOX_URL
                + HttpParams.API_VERSION + HttpParams.VERIFY_OTP);
        mContext = new WeakReference<>(context);
    }


    public void setResponseListener(SendOtpListener responseListener) {
        this.listener = responseListener;
    }

    public void buildParams(String orderId, String amount, String mobileNumber, String password) {
        JSONObject json = new JSONObject();
        try {
            json.put(HttpParams.PARAM_ORDER_ID_2, orderId);
            json.put(HttpParams.PARAM_AMOUNT, amount);
            json.put(HttpParams.PARAM_MOBILE_NUMBER_2, mobileNumber);
            json.put(HttpParams.PARAM_PASSWORD, password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        post(json.toString());
    }

    public void buildParams(String orderId, String amount, String mobileNumber, String password,String otp) {
        JSONObject json = new JSONObject();
        try {
            json.put(HttpParams.PARAM_ORDER_ID_2, orderId);
            json.put(HttpParams.PARAM_AMOUNT, amount);
            json.put(HttpParams.PARAM_MOBILE_NUMBER_2, mobileNumber);
            json.put(HttpParams.PARAM_PASSWORD, password);
            json.put(HttpParams.PARAM_OTP, otp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        post(json.toString());
    }

    @Override
    public void onBackgroundTask(String response) {
        responseModel = new BaseResponseParser().getResponseModel(response);
    }

    @Override
    public void onTaskComplete() {
        if (responseModel != null) {
            if (responseModel.getCode() == 200 ) {
                listener.successResponse(responseModel.getMessage());
            } else {
                ArrayList<String> errorList = new ArrayList<>();
                errorList.add(responseModel.getMessage());
                listener.failResponse(errorList);
            }
        } else {
            listener.errorResponse(mContext.get().getString(R.string.fp_app_common_api_error));
        }
    }
}
