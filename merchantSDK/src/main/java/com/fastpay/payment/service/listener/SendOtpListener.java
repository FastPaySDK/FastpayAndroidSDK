package com.fastpay.payment.service.listener;

import com.fastpay.payment.model.response.CashoutPaymentSummery;

import java.util.ArrayList;

/**
 * @Created By Zarraf Ahmed
 * Created 8/10/2023 at 5:10 PM
 */
public interface SendOtpListener {
    void successResponse(String message);

    void failResponse(ArrayList<String> messages);

    void errorResponse(String error);
}
