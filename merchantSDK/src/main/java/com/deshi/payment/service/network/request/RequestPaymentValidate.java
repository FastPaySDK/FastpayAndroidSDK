package com.deshi.payment.service.network.request;

import android.content.Context;

import com.deshi.payment.R;
import com.deshi.payment.service.listener.PaymentValidationApiListener;
import com.deshi.payment.service.network.parser.PaymentValidateParser;

import com.deshi.payment.model.merchant.DeshiSDK;
import com.deshi.payment.model.response.BaseResponseModel;
import com.deshi.payment.service.network.http.BaseHttp;
import com.deshi.payment.service.network.http.HttpParams;
import com.deshi.payment.service.network.parser.BaseResponseParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;


public class RequestPaymentValidate extends BaseHttp {

    private WeakReference<Context> mContext;
    private BaseResponseModel responseModel;
    private PaymentValidationApiListener listener;

    public RequestPaymentValidate(Context context, String environment) {
        super(context, environment.equals(DeshiSDK.PRODUCTION) ? HttpParams.PRODUCTION_URL : HttpParams.SANDBOX_URL
                + HttpParams.API_VERSION + HttpParams.API_VALIDATE);
        mContext = new WeakReference<>(context);
    }

    public void setResponseListener(PaymentValidationApiListener responseListener) {
        this.listener = responseListener;
    }

    public void buildParams(String storeId, String storePass, String orderId) {
        JSONObject json = new JSONObject();
        try {
            json.put(HttpParams.PARAM_STORE_ID, storeId);
            json.put(HttpParams.PARAM_STORE_PASS, storePass);
            json.put(HttpParams.PARAM_ORDER_ID, orderId);
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
            if (responseModel.getCode()== 200) {
                listener.successResponse(new PaymentValidateParser().getValidationModel(responseModel.getData().toString()));
            } else {
                listener.failResponse(responseModel.getErrors());
            }
        } else {
            listener.errorResponse(mContext.get().getString(R.string.fp_app_common_api_error));
        }
    }
}
