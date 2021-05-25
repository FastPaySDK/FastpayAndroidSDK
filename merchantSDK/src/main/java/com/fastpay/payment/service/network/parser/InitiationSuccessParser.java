package com.fastpay.payment.service.network.parser;


import com.fastpay.payment.model.response.InitiationSuccess;

import org.json.JSONException;
import org.json.JSONObject;

public class InitiationSuccessParser {

    public InitiationSuccess getInitiationModel(String response) {

        try {
            if (response != null && !response.isEmpty()) {
                JSONObject jsonObject = new JSONObject(response);

                String storeName = null, storeLogo = null, orderId = null, billAmount = null,
                        currency = null, token = null, qrToken = null;

                if (jsonObject.has("storeName")) {
                    storeName = jsonObject.getString("storeName");
                }

                if (jsonObject.has("storeLogo")) {
                    storeLogo = jsonObject.getString("storeLogo");
                }

                if (jsonObject.has("orderId")) {
                    orderId = jsonObject.getString("orderId");
                }

                if (jsonObject.has("billAmount")) {
                    billAmount = jsonObject.getString("billAmount");
                }

                if (jsonObject.has("currency")) {
                    currency = jsonObject.getString("currency");
                }

                if (jsonObject.has("token")) {
                    token = jsonObject.getString("token");
                }

                if (jsonObject.has("qrToken")) {
                    qrToken = jsonObject.getString("qrToken");
                }

                return new InitiationSuccess(storeName, storeLogo, orderId, billAmount, currency, token, qrToken);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
