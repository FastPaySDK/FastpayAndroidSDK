package com.fastpay.payment.service.network.parser;

import com.fastpay.payment.model.response.CashoutPaymentSummery;
import com.fastpay.payment.model.response.CashoutRecipient;
import com.fastpay.payment.model.response.CashoutSummary;
import com.fastpay.payment.model.response.PaymentValidation;

import org.json.JSONException;
import org.json.JSONObject;

public class CashoutPaymentParser {

    public CashoutPaymentSummery getModel(String response) {
        try {
            if (response != null && !response.isEmpty()) {
                JSONObject jsonObject = new JSONObject(response);
                CashoutPaymentSummery cashoutPaymentSummery = new CashoutPaymentSummery();
                if (jsonObject.has("summary")) {
                    CashoutSummary summary = new CashoutSummary();
                    JSONObject jsonSummery = new JSONObject(jsonObject.getString("summary"));
                    if (jsonSummery.has("transaction_type"))
                        summary.setTransactionType(jsonSummery.getString("transaction_type"));

                    if (jsonSummery.has("invoice_id"))
                        summary.setInvoiceId(jsonSummery.getString("invoice_id"));

                    if (jsonSummery.has("order_id"))
                        summary.setOrderId(jsonSummery.getString("order_id"));

                    if (jsonSummery.has("amount"))
                        summary.setAmount(jsonSummery.getString("amount"));

                    if (jsonSummery.has("recipient")) {
                        CashoutRecipient recipient = new CashoutRecipient();
                        JSONObject recipientJSON = new JSONObject(jsonSummery.getString("recipient"));
                        if (recipientJSON.has("name"))
                            recipient.setName(recipientJSON.getString("name"));
                        if (recipientJSON.has("mobile_number"))
                            recipient.setMobileNumber(recipientJSON.getString("mobile_number"));
                        if (recipientJSON.has("avatar"))
                            recipient.setAvatar(recipientJSON.getString("avatar"));
                        summary.setRecipient(recipient);
                    }
                    cashoutPaymentSummery.setSummary(summary);
                }
                return cashoutPaymentSummery;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
