package com.fastpay.payment.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sahidul Islam on 2/22/2021.
 */
public class PaymentSuccess implements Serializable {

    @SerializedName("summary")
    @Expose
    private PaymentSummery paymentSummery;

    public PaymentSuccess() {
    }

    public PaymentSummery getPaymentSummery() {
        return paymentSummery;
    }
}
