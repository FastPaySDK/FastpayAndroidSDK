package com.fastpay.payment.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sahidul Islam on 2/22/2021.
 */

public class ValidatePayment implements Serializable {

    @SerializedName("storeId")
    @Expose
    private String storeId;
    @SerializedName("storePassword")
    @Expose
    private String storePassword;
    @SerializedName("orderId")
    @Expose
    private String orderId;

    public ValidatePayment() {
    }

    public ValidatePayment(String storeId, String storePassword, String orderId) {
        this.storeId = storeId;
        this.storePassword = storePassword;
        this.orderId = orderId;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
