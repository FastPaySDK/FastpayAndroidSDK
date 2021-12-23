package com.deshi.payment.service.listener;

import com.deshi.payment.model.response.PaymentSummery;

import java.util.ArrayList;

public interface PayWithCredentialApiListener {

    void successResponse(PaymentSummery model);

    void failResponse(ArrayList<String> messages);

    void errorResponse(String error);
}
