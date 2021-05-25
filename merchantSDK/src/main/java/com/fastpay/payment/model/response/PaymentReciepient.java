package com.fastpay.payment.model.response;

import java.io.Serializable;

/**
 * Created by Sahidul Islam on 2/22/2021.
 */
public class PaymentReciepient implements Serializable {

    private String name;
    private String mobileNumber;
    private String avatar;

    public PaymentReciepient() {
    }

    public PaymentReciepient(String name, String mobileNumber, String avatar) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getAvatar() {
        return avatar;
    }
}
