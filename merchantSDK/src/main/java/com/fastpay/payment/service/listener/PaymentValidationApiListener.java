package com.fastpay.payment.service.listener;

import com.fastpay.payment.model.response.PaymentValidation;

import java.util.ArrayList;

public interface PaymentValidationApiListener {

    void successResponse(PaymentValidation model);

    void failResponse(ArrayList<String> messages);

    void errorResponse(String error);
}
