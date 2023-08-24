package com.fastpay.payment.service.network.request;

import android.content.Context;

import com.fastpay.payment.model.merchant.FastpaySDK;
import com.fastpay.payment.model.response.BaseResponseModel;
import com.fastpay.payment.service.listener.CashOutPaymentListener;
import com.fastpay.payment.service.network.http.BaseHttp;
import com.fastpay.payment.service.network.http.HttpParams;
import com.fastpay.payment.service.network.parser.BaseResponseParser;
import com.fastpay.payment.service.network.parser.CashoutPaymentParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;

public class RequestOtpPayment extends BaseHttp {

    private WeakReference<Context> mContext;
    private BaseResponseModel responseModel;
    private CashOutPaymentListener listener;

    public RequestOtpPayment(Context context, String environment) {
        super(context, (environment.equals(FastpaySDK.PRODUCTION) ? HttpParams.PRODUCTION_URL : HttpParams.SANDBOX_URL)
                + HttpParams.API_VERSION + HttpParams.VERIFY_OTP);
        mContext = new WeakReference<>(context);
    }

    public void setResponseListener(CashOutPaymentListener responseListener) {
        this.listener = responseListener;
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
            if (responseModel.getCode() == 200) {
                listener.successResponse(new CashoutPaymentParser().getModel(responseModel.getData().toString()));
            } else {
                ArrayList<String> errorList = new ArrayList();
                if (responseModel.getMessage() != null){
                    String newString = responseModel.getMessage();
                    if (newString.contains("["))
                        newString = newString.replace("[","");
                    if (newString.contains("]"))
                        newString = newString.replace("]","");
                    if (newString.contains(","))
                        errorList.addAll(Arrays.asList(newString.split(",")));
                    else
                        errorList.add(newString);
                    listener.failResponse(errorList);
                }else{
                    errorList.add("Error Occurred");
                    listener.failResponse(errorList);
                }
            }
        } else {
            listener.errorResponse("Error Occurred");
        }
    }
}
