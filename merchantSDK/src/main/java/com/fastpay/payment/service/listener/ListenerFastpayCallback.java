package com.fastpay.payment.service.listener;

import android.os.Parcelable;

import com.fastpay.payment.model.merchant.FastpayRequest;
import com.fastpay.payment.model.merchant.FastpayResult;

/**
 * @Created By Zarraf Ahmed
 * Created 2/29/2024 at 3:01 PM
 */
public interface ListenerFastpayCallback extends Parcelable {
    void sdkCallBack(FastpayRequest.SDKStatus sdkStatus,String message);
}
