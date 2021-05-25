package com.fastpay.payment.service.network.parser;


import com.fastpay.payment.model.response.PaymentReciepient;
import com.fastpay.payment.model.response.PaymentSummery;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentSummeryParser {

    public PaymentSummery getSummeryModel(String response) {

        try {
            if (response != null && !response.isEmpty()) {
                JSONObject jsonObject = new JSONObject(response);

                String invoiceId = null;
                PaymentReciepient reciepient = new PaymentReciepient();

                if (jsonObject.has("recipient")) {
                    JSONObject reciepientObject = jsonObject.getJSONObject("recipient");

                    String name = null, mobileNumber = null, avatar = null;

                    if (reciepientObject.has("name")) {
                        name = reciepientObject.getString("name");
                    }

                    if (reciepientObject.has("mobile_number")) {
                        mobileNumber = reciepientObject.getString("mobile_number");
                    }

                    if (reciepientObject.has("avatar")) {
                        avatar = reciepientObject.getString("avatar");
                    }

                    reciepient = new PaymentReciepient(name, mobileNumber, avatar);
                }

                if (jsonObject.has("invoice_id")) {
                    invoiceId = jsonObject.getString("invoice_id");
                }

                return new PaymentSummery(reciepient, invoiceId);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
