package com.fastpay.payment.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sahidul Islam on 2/22/2021.
 */

public class PaymentValidation implements Serializable {

    @SerializedName("gw_transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("merchant_order_id")
    @Expose
    private String merchantOrderId;
    @SerializedName("received_amount")
    @Expose
    private String receivedAmount;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("customer_mobile_number")
    @Expose
    private String customerMobileNumber;
    @SerializedName("at")
    @Expose
    private String paymentAt;


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
