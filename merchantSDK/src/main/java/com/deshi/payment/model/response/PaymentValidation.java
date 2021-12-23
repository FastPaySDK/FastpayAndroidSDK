package com.deshi.payment.model.response;

import java.io.Serializable;

/**
 * Created by Sahidul Islam on 2/22/2021.
 */

public class PaymentValidation implements Serializable {


    private String transactionId;
    private String merchantOrderId;
    private String receivedAmount;
    private String currency;
    private String status;
    private String customerName;
    private String customerMobileNumber;
    private String paymentAt;

    public PaymentValidation(String transactionId, String merchantOrderId, String receivedAmount,
                             String currency, String status, String customerName, String customerMobileNumber, String paymentAt) {
        this.transactionId = transactionId;
        this.merchantOrderId = merchantOrderId;
        this.receivedAmount = receivedAmount;
        this.currency = currency;
        this.status = status;
        this.customerName = customerName;
        this.customerMobileNumber = customerMobileNumber;
        this.paymentAt = paymentAt;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public String getReceivedAmount() {
        return receivedAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getStatus() {
        return status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerMobileNumber() {
        return customerMobileNumber;
    }

    public String getPaymentAt() {
        return paymentAt;
    }
}
