package com.deshi.payment.service.network.request;

import android.content.Context;

import com.deshi.payment.R;
import com.deshi.payment.model.response.BaseResponseModel;
import com.deshi.payment.service.network.parser.BaseResponseParser;

import com.deshi.payment.model.merchant.FastpayRequest;
import com.deshi.payment.model.merchant.FastpaySDK;
import com.deshi.payment.service.listener.InitiationApiListener;
import com.deshi.payment.service.network.http.BaseHttp;
import com.deshi.payment.service.network.http.HttpParams;
import com.deshi.payment.service.network.parser.InitiationSuccessParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;


public class RequestPaymentInitiate extends BaseHttp {

    private WeakReference<Context> mContext;
    private BaseResponseModel responseModel;
    private InitiationApiListener listener;

    public RequestPaymentInitiate(Context context, String environment) {
        super(context, environment.equals(FastpaySDK.PRODUCTION) ? HttpParams.PRODUCTION_URL : HttpParams.SANDBOX_URL
                + HttpParams.API_VERSION + HttpParams.API_INITIATE);
        mContext = new WeakReference<>(context);
    }

    public void setResponseListener(InitiationApiListener responseListener) {
        this.listener = responseListener;
    }

    public void buildParams(FastpayRequest requestModel) {
        JSONObject json = new JSONObject();
        try {
            json.put(HttpParams.PARAM_STORE_ID, requestModel.getStoreId());
            json.put(HttpParams.PARAM_STORE_PASS, requestModel.getStorePassword());
            json.put(HttpParams.PARAM_BILL_AMOUNT, requestModel.getAmount());
            json.put(HttpParams.PARAM_ORDER_ID, requestModel.getOrderId());
            json.put(HttpParams.PARAM_CURRENCY, requestModel.getCurrency());
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
                listener.successResponse(new InitiationSuccessParser().getInitiationModel(responseModel.getData().toString()));
            } else {
                listener.failResponse(responseModel.getErrors());
            }
        } else {
            listener.errorResponse(mContext.get().getString(R.string.fp_app_common_api_error));
        }
    }
}
