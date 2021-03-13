package com.fastpay.payment.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sahidul Islam on 2/22/2021.
 */

public class PaymentInitiation implements Serializable {

    @SerializedName("storeId")
    @Expose
    private String storeId;
    @SerializedName("storePassword")
    @Expose
    private String storePassword;
    @SerializedName("billAmount")
    @Expose
    private String billAmount;
    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("currency")
    @Expose
    private String currency;

    public PaymentInitiation() {
    }

    public PaymentInitiation(String storeId, String storePassword, String billAmount, String orderId, String currency) {
        this.storeId = storeId;
        this.storePassword = storePassword;
        this.billAmount = billAmount;
        this.orderId = orderId;
        this.currency = currency;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStorePassword() {
        return storePassword;
    }

    public void setStorePassword(String storePassword) {
        this.storePassword = storePassword;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
