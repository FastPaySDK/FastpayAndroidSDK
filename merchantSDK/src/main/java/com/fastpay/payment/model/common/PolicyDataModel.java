package com.fastpay.payment.model.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PolicyDataModel implements Serializable {

    @SerializedName("policyTitle")
    @Expose
    private String policyTitle;
    @SerializedName("isValid")
    @Expose
    private boolean isValid;

    public PolicyDataModel(String policyTitle, boolean isValid) {
        this.policyTitle = policyTitle;
        this.isValid = isValid;
    }

    public String getPolicyTitle() {
        return policyTitle;
    }

    public void setPolicyTitle(String policyTitle) {
        this.policyTitle = policyTitle;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
