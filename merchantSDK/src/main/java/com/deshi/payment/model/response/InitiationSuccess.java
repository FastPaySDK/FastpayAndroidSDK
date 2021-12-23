package com.deshi.payment.model.response;


import java.io.Serializable;

/**
 * Created by Sahidul Islam on 2/22/2021.
 */
public class InitiationSuccess implements Serializable {

    private String storeName;
    private String storeLogo;
    private String orderId;
    private String billAmount;
    private String currency;
    private String token;
    private String qrToken;

    public InitiationSuccess() {
    }

    public InitiationSuccess(String storeName, String storeLogo, String orderId, String billAmount,
                             String currency, String token, String qrToken) {
        this.storeName = storeName;
        this.storeLogo = storeLogo;
        this.orderId = orderId;
        this.billAmount = billAmount;
        this.currency = currency;
        this.token = token;
        this.qrToken = qrToken;
    }

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
