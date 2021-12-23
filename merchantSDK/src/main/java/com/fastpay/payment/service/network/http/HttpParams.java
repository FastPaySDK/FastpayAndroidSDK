package com.fastpay.payment.service.network.http;

/**
 * Created by Sahidul Islam on 17-May-21.
 */
public class HttpParams {

    public static final String SANDBOX_URL = "https://staging-apigw-sdk.fast-pay.iq/";
    public static final String PRODUCTION_URL = "https://apigw-sdk.fast-pay.iq/";
    public static final String API_VERSION = "api/v1/";

    public static final String API_INITIATE = "public/sdk/payment/initiation";
    public static final String API_PAYMENT = "public/sdk/payment/pay";
    public static final String API_VALIDATE = "public/sdk/payment/validate";

    public static final String PARAM_STORE_ID = "storeId";
    public static final String PARAM_STORE_PASS = "storePassword";
    public static final String PARAM_BILL_AMOUNT = "billAmount";
    public static final String PARAM_ORDER_ID = "orderId";
    public static final String PARAM_CURRENCY = "currency";
    public static final String PARAM_TOKEN = "token";
    public static final String PARAM_MOBILE_NUMBER = "mobileNumber";
    public static final String PARAM_PASSWORD= "password";
}
