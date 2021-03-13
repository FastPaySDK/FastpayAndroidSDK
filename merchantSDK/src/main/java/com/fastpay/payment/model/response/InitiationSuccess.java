package com.fastpay.payment.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sahidul Islam on 2/22/2021.
 */

public class InitiationSuccess implements Serializable {

    @SerializedName("storeName")
    @Expose
    private String storeName;
    @SerializedName("storeLogo")
    @Expose
    private String storeLogo;
    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("billAmount")
    @Expose
    private String billAmount;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("qrToken")
    @Expose
    private String qrToken;


    public String getStoreName() {
        return storeName;
    }

    public String getStoreLogo() {
        return storeLogo;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getToken() {
        return token;
    }

    public String getQrToken() {
        return qrToken;
    }
}
