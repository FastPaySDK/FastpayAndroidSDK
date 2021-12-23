package com.deshi.payment.model.response;


import java.io.Serializable;

/**
 * Created by Sahidul Islam on 2/22/2021.
 */
public class PaymentSuccess implements Serializable {

    private PaymentSummery paymentSummery;

    public PaymentSuccess() {
    }

    public PaymentSummery getPaymentSummery() {
        return paymentSummery;
    }
}
