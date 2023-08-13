package com.fastpay.payment.service.network.parser;


import com.fastpay.payment.model.response.BaseResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BaseResponseParser {

    public BaseResponseModel getResponseModel(String response) {

        try {
            if (response != null && !response.isEmpty()) {
                JSONObject jsonObject = new JSONObject(response);

                int code = 0;
                String message = null;
                ArrayList<String> errors = new ArrayList<>();
                Object data = null;

                if (jsonObject.has("code")) {
                    code = jsonObject.getInt("code");
                }

                if (jsonObject.has("messages")) {
                    String listMessages = jsonObject.getString("messages");
                    listMessages = listMessages.replace("[","");
                    listMessages = listMessages.replace("]","");
                    listMessages = listMessages.replace("\"","");
                    message = listMessages;
                }

                if (jsonObject.has("errors")) {
                    JSONArray errorArray = jsonObject.getJSONArray("errors");
                    for (int i = 0; i < errorArray.length(); i++) {
                        errors.add(errorArray.getString(i));
                    }
                }

                if (jsonObject.has("data")) {
                    data = jsonObject.get("data");
                }

                return new BaseResponseModel(code, message, errors, data);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
