package com.fastpay.payment.model.request;

public class CashoutPaymentRequest {

    private String orderId;
    private String amount;
    private String mobileNumber;
    private String password;

    public CashoutPaymentRequest(String orderId, String amount, String mobileNumber, String password) {
        this.orderId = orderId;
        this.amount = amount;
        this.mobileNumber = mobileNumber;
        this.password = password;
    }

    public CashoutPaymentRequest(){}

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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
