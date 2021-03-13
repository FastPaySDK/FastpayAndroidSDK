package com.fastpay.payment.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sahidul Islam on 2/22/2021.
 */

public class PaymentSummery implements Serializable {

    @SerializedName("recipient")
    @Expose
    private PaymentReciepient recipient;
    @SerializedName("invoice_id")
    @Expose
    private String invoiceId;

    public PaymentSummery() {
    }

    public PaymentReciepient getRecipient() {
        return recipient;
    }

    public String getInvoiceId() {
        return invoiceId;
    }
}
