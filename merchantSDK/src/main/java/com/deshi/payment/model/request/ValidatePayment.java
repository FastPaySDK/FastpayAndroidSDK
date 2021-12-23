package com.deshi.payment.model.request;

import java.io.Serializable;

/**
 * Created by Sahidul Islam on 2/22/2021.
 */

public class ValidatePayment implements Serializable {

    private String storeId;
    private String storePassword;
    private String orderId;

    public ValidatePayment() {
    }

    public ValidatePayment(String storeId, String storePassword, String orderId) {
        this.storeId = storeId;
        this.storePassword = storePassword;
        this.orderId = orderId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStorePassword() {
        return storePassword;
    }

    public void setStorePassword(String storePassword) {
        this.storePassword = storePassword;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
