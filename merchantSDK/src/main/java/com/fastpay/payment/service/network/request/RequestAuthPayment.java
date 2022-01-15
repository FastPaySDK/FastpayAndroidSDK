package com.fastpay.payment.service.network.request;

import android.content.Context;

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


public class RequestAuthPayment extends BaseHttp {

    private WeakReference<Context> mContext;
    private BaseResponseModel responseModel;
    private PayWithCredentialApiListener listener;

    public RequestAuthPayment(Context context, String environment) {
        super(context, environment.equals(FastpaySDK.PRODUCTION) ? HttpParams.PRODUCTION_URL + HttpParams.API_VERSION + HttpParams.API_PAYMENT : HttpParams.SANDBOX_URL + HttpParams.API_VERSION + HttpParams.API_PAYMENT);
        mContext = new WeakReference<>(context);
    }

    public void setResponseListener(PayWithCredentialApiListener responseListener) {
        this.listener = responseListener;
    }

    public void buildParams(String orderId, String token, String mobileNumber, String password) {
        JSONObject json = new JSONObject();
        try {
            json.put(HttpParams.PARAM_ORDER_ID, orderId);
            json.put(HttpParams.PARAM_TOKEN, token);
            json.put(HttpParams.PARAM_MOBILE_NUMBER, mobileNumber);
            json.put(HttpParams.PARAM_PASSWORD, password);
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
                listener.successResponse(new PaymentSummeryParser().getSummeryModel(responseModel.getData().toString()));
            } else {
                listener.failResponse(responseModel.getErrors());
            }
        } else {
            listener.errorResponse(mContext.get().getString(R.string.fp_app_common_api_error));
        }
    }
}
