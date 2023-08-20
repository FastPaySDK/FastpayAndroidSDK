package com.fastpay.payment.service.network.request;

import android.content.Context;
import android.util.Log;

import com.fastpay.payment.R;
import com.fastpay.payment.model.merchant.FastpaySDK;
import com.fastpay.payment.model.response.BaseResponseModel;
import com.fastpay.payment.service.listener.SendOtpListener;
import com.fastpay.payment.service.network.http.BaseHttp;
import com.fastpay.payment.service.network.http.HttpParams;
import com.fastpay.payment.service.network.parser.BaseResponseParser;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

public class RequestSentOtp extends BaseHttp {
    private WeakReference<Context> mContext;
    private BaseResponseModel responseModel;
    private SendOtpListener listener;

    public RequestSentOtp(Context context, String environment) {
        super(context, environment.equals(FastpaySDK.PRODUCTION) ? HttpParams.PRODUCTION_URL : HttpParams.SANDBOX_URL
                + HttpParams.API_VERSION_2 + HttpParams.API_SENT_OTP);
        mContext = new WeakReference<>(context);
    }

    public void setResponseListener(SendOtpListener responseListener) {
        this.listener = responseListener;
    }

    public void buildParams(String orderId, String token, String mobileNumber, String password) {
        JSONObject json = new JSONObject();
        try {
            json.put(HttpParams.PARAM_ORDER_ID_2, orderId);
            json.put(HttpParams.PARAM_MOBILE_NUMBER_2, mobileNumber);
            json.put(HttpParams.PARAM_PASSWORD, password);
            json.put(HttpParams.PARAM_TOKEN, token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(Objects.equals(environment, FastpaySDK.SANDBOX)){
            Log.e("RequestBodySentOtp", json.toString());
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

