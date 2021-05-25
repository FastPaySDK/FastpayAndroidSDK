package com.fastpay.payment.model.request;


import java.io.Serializable;

/**
 * Created by Sahidul Islam on 2/22/2021.
 */

public class PaymentInitiation implements Serializable {

    private String storeId;
    private String storePassword;
    private String billAmount;
    private String orderId;
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
