package com.fastpay.payment.service.network.parser;


import com.fastpay.payment.model.response.PaymentValidation;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentValidateParser {

    public PaymentValidation getValidationModel(String response) {

        try {
            if (response != null && !response.isEmpty()) {
                JSONObject jsonObject = new JSONObject(response);

                String transactionId = null, merchantOrderId = null, receivedAmount = null, currency = null,
                        status = null, customerName = null, mobileNumber = null, paymentAt = null;

                if (jsonObject.has("gw_transaction_id")) {
                    transactionId = jsonObject.getString("gw_transaction_id");
                }

                if (jsonObject.has("merchant_order_id")) {
                    merchantOrderId = jsonObject.getString("merchant_order_id");
                }

                if (jsonObject.has("received_amount")) {
                    receivedAmount = jsonObject.getString("received_amount");
                }

                if (jsonObject.has("currency")) {
                    currency = jsonObject.getString("currency");
                }

                if (jsonObject.has("status")) {
                    status = jsonObject.getString("status");
                }

                if (jsonObject.has("customer_name")) {
                    customerName = jsonObject.getString("customer_name");
                }

                if (jsonObject.has("customer_mobile_number")) {
                    mobileNumber = jsonObject.getString("customer_mobile_number");
                }

                if (jsonObject.has("at")) {
                    paymentAt = jsonObject.getString("at");
                }

                return new PaymentValidation(transactionId, merchantOrderId, receivedAmount, currency, status, customerName, mobileNumber, paymentAt);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
