package com.fastpay.payment.service.listener;

import java.util.ArrayList;

public interface SendOtpListener {
    void successResponse(String message);

    void failResponse(ArrayList<String> messages);

    void errorResponse(String error);
}
