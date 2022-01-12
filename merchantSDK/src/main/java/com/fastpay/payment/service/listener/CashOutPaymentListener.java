package com.fastpay.payment.service.listener;

import com.fastpay.payment.model.response.CashoutPaymentSummery;

import java.util.ArrayList;

public interface CashOutPaymentListener {
    void successResponse(CashoutPaymentSummery model);

    void failResponse(ArrayList<String> messages);

    void errorResponse(String error);
}
