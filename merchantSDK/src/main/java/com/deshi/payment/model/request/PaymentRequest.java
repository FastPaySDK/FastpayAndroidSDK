package com.deshi.payment.model.request;


import java.io.Serializable;

/**
 * Created by Sahidul Islam on 2/22/2021.
 */
public class PaymentRequest implements Serializable {

    private String orderId;
    private String token;
    private String mobileNumber;
    private String password;

    public PaymentRequest() {
    }

    public PaymentRequest(String orderId, String token, String mobileNumber, String password) {
        this.orderId = orderId;
        this.token = token;
        this.mobileNumber = mobileNumber;
        this.password = password;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
