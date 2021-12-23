package com.deshi.payment.model.response;

import java.io.Serializable;

/**
 * Created by Sahidul Islam on 2/22/2021.
 */

public class PaymentSummery implements Serializable {

    private PaymentReciepient recipient;
    private String invoiceId;

    public PaymentSummery() {
    }

    public PaymentSummery(PaymentReciepient recipient, String invoiceId) {
        this.recipient = recipient;
        this.invoiceId = invoiceId;
    }

    public PaymentReciepient getRecipient() {
        return recipient;
    }

    public String getInvoiceId() {
        return invoiceId;
    }
}
