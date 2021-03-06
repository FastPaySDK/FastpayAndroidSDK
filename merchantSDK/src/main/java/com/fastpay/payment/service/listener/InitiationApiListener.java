package com.fastpay.payment.service.listener;

import com.fastpay.payment.model.response.InitiationSuccess;

import java.util.ArrayList;

public interface InitiationApiListener {

    void successResponse(InitiationSuccess model);

    void failResponse(ArrayList<String> messages);

    void errorResponse(String error);
}
