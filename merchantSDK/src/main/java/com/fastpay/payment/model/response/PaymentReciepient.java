package com.fastpay.payment.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sahidul Islam on 2/22/2021.
 */
public class PaymentReciepient implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("avatar")
    @Expose
    private String avatar;

    public PaymentReciepient() {
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
