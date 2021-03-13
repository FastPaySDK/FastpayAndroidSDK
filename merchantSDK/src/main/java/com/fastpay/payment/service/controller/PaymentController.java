package com.fastpay.payment.service.controller;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.fastpay.payment.R;
import com.fastpay.payment.model.request.PaymentInitiation;
import com.fastpay.payment.model.request.PaymentRequest;
import com.fastpay.payment.model.request.ValidatePayment;
import com.fastpay.payment.model.response.BaseResponseModel;
import com.fastpay.payment.model.response.InitiationSuccess;
import com.fastpay.payment.model.response.PaymentSummery;
import com.fastpay.payment.model.response.PaymentValidation;
import com.fastpay.payment.service.listener.InitiationApiListener;
import com.fastpay.payment.service.listener.PayWithCredentialApiListener;
import com.fastpay.payment.service.listener.PaymentValidationApiListener;
import com.fastpay.payment.service.network.ApiClient;
import com.fastpay.payment.service.network.ApiInterface;
import com.fastpay.payment.service.utill.ConfigurationUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by Sahidul Islam on 2/22/2021.
 */

public class PaymentController extends BaseController {

    private Activity context;

    public PaymentController(Activity context) {
        this.context = context;
    }

    public void initiatePaymentApi(PaymentInitiation model, InitiationApiListener listener) {
        if (ConfigurationUtil.isInternetAvailable(context)) {
            ApiInterface apiInterface = ApiClient.getClient(context).create(ApiInterface.class);
            apiInterface.callInitiationApi(model).enqueue(new Callback<BaseResponseModel>() {
                @Override
                public void onResponse(@NonNull Call<BaseResponseModel> call, @NonNull Response<BaseResponseModel> response) {
                    if (response.code() == 200) {
                        BaseResponseModel baseResponseModel = response.body();
                        if (listener != null && baseResponseModel != null) {
                            InitiationSuccess model = new Gson().fromJson(new Gson().toJson(baseResponseModel.getData()), InitiationSuccess.class);
                            listener.successResponse(model);
                        } else {
                            listener.errorResponse(context.getString(R.string.app_common_api_error));
                        }
                    } else if (response.code() == 400) {
                        Converter<ResponseBody, BaseResponseModel> converter = ApiClient.getClient(context).responseBodyConverter(BaseResponseModel.class, new Annotation[0]);
                        try {
                            BaseResponseModel baseResponseModel = converter.convert(response.errorBody());
                            listener.failResponse(baseResponseModel.getErrors());
                        } catch (IOException e) {
                            e.printStackTrace();
                            listener.errorResponse(e.getLocalizedMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<BaseResponseModel> call, @NonNull Throwable throwable) {
                    if (listener != null) {
                        listener.errorResponse(throwable.getMessage());
                    }
                }
            });
        }
    }

    public void payWithCredentialApi(PaymentRequest model, PayWithCredentialApiListener listener) {
        if (ConfigurationUtil.isInternetAvailable(context)) {
            ApiInterface apiInterface = ApiClient.getClient(context).create(ApiInterface.class);
            apiInterface.callPaymentApi(model).enqueue(new Callback<BaseResponseModel>() {
                @Override
                public void onResponse(@NonNull Call<BaseResponseModel> call, @NonNull Response<BaseResponseModel> response) {
                    if (response.code() == 200) {
                        BaseResponseModel baseResponseModel = response.body();
                        if (listener != null && baseResponseModel != null) {
                            PaymentSummery model = new Gson().fromJson(new Gson().toJson(baseResponseModel.getData()), PaymentSummery.class);
                            listener.successResponse(model);
                        } else {
                            listener.errorResponse(context.getString(R.string.app_common_api_error));
                        }
                    } else if (response.code() == 400) {
                        Converter<ResponseBody, BaseResponseModel> converter = ApiClient.getClient(context).responseBodyConverter(BaseResponseModel.class, new Annotation[0]);
                        try {
                            BaseResponseModel baseResponseModel = converter.convert(response.errorBody());
                            listener.failResponse(baseResponseModel.getErrors());
                        } catch (IOException e) {
                            e.printStackTrace();
                            listener.errorResponse(e.getLocalizedMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<BaseResponseModel> call, @NonNull Throwable throwable) {
                    if (listener != null) {
                        listener.errorResponse(throwable.getMessage());
                    }
                }
            });
        }
    }

    public void paymentValidationApi(ValidatePayment model, PaymentValidationApiListener listener) {
        if (ConfigurationUtil.isInternetAvailable(context)) {
            ApiInterface apiInterface = ApiClient.getClient(context).create(ApiInterface.class);
            apiInterface.callValidatePaymentApi(model).enqueue(new Callback<BaseResponseModel>() {
                @Override
                public void onResponse(@NonNull Call<BaseResponseModel> call, @NonNull Response<BaseResponseModel> response) {
                    if (response.code() == 200) {
                        BaseResponseModel baseResponseModel = response.body();
                        if (listener != null && baseResponseModel != null) {
                            PaymentValidation model = new Gson().fromJson(new Gson().toJson(baseResponseModel.getData()), PaymentValidation.class);
                            listener.successResponse(model);
                        } else {
                            listener.errorResponse(context.getString(R.string.app_common_api_error));
                        }
                    } else if (response.code() == 400) {
                        Converter<ResponseBody, BaseResponseModel> converter = ApiClient.getClient(context).responseBodyConverter(BaseResponseModel.class, new Annotation[0]);
                        try {
                            BaseResponseModel baseResponseModel = converter.convert(response.errorBody());
                            listener.failResponse(baseResponseModel.getErrors());
                        } catch (IOException e) {
                            e.printStackTrace();
                            listener.errorResponse(e.getLocalizedMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<BaseResponseModel> call, @NonNull Throwable throwable) {
                    if (listener != null) {
                        listener.errorResponse(throwable.getMessage());
                    }
                }
            });
        }
    }
}
