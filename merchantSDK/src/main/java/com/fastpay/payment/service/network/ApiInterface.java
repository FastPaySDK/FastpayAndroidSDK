package com.fastpay.payment.service.network;


import com.fastpay.payment.model.request.PaymentInitiation;
import com.fastpay.payment.model.request.PaymentRequest;
import com.fastpay.payment.model.request.ValidatePayment;
import com.fastpay.payment.model.response.BaseResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("public/sdk/payment/initiation")
    Call<BaseResponseModel> callInitiationApi(@Body PaymentInitiation requestModel);

    @POST("public/sdk/payment/pay")
    Call<BaseResponseModel> callPaymentApi(@Body PaymentRequest requestModel);

    @POST("public/sdk/payment/validate")
    Call<BaseResponseModel> callValidatePaymentApi(@Body ValidatePayment requestModel);
}
