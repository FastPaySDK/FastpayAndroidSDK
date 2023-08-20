package com.fastpay.payment.service.network.request;

import android.content.Context;
import android.util.Log;

import com.fastpay.payment.R;
import com.fastpay.payment.model.merchant.FastpaySDK;
import com.fastpay.payment.model.response.BaseResponseModel;
import com.fastpay.payment.service.listener.PayWithCredentialApiListener;
import com.fastpay.payment.service.network.http.BaseHttp;
import com.fastpay.payment.service.network.http.HttpParams;
import com.fastpay.payment.service.network.parser.BaseResponseParser;
import com.fastpay.payment.service.network.parser.PaymentSummeryParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Objects;


public class RequestAuthPayment extends BaseHttp {

    private WeakReference<Context> mContext;
    private BaseResponseModel responseModel;
    private PayWithCredentialApiListener listener;

    public RequestAuthPayment(Context context, String environment) {
        super(context, environment.equals(FastpaySDK.PRODUCTION) ? HttpParams.PRODUCTION_URL + HttpParams.API_VERSION_2 + HttpParams.API_PAYMENT_WITH_OTP_VERIFICATION : HttpParams.SANDBOX_URL + HttpParams.API_VERSION_2 + HttpParams.API_PAYMENT_WITH_OTP_VERIFICATION);
        mContext = new WeakReference<>(context);
        this.environment = environment;
    }

    public void setResponseListener(PayWithCredentialApiListener responseListener) {
        this.listener = responseListener;
    }

    public void buildParams(String orderId, String token, String mobileNumber, String password, String otp) {
        JSONObject json = new JSONObject();
        try {
            json.put(HttpParams.PARAM_ORDER_ID_2, orderId);
            json.put(HttpParams.PARAM_TOKEN, token);
            json.put(HttpParams.PARAM_OTP, otp);
            json.put(HttpParams.PARAM_MOBILE_NUMBER_2, mobileNumber);
            json.put(HttpParams.PARAM_PASSWORD, password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(Objects.equals(environment, FastpaySDK.SANDBOX)){
            Log.e("RequestBodyAuthPayment", json.toString());
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
                listener.successResponse(new PaymentSummeryParser().getSummeryModel(responseModel.getData().toString()));
            } else {
                listener.failResponse(responseModel.getErrors());
            }
        } else {
            listener.errorResponse(mContext.get().getString(R.string.fp_app_common_api_error));
        }
    }
}
