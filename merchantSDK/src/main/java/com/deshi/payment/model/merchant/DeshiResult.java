package com.deshi.payment.model.merchant;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sahidul Islam on 2/15/2021.
 */

public class DeshiResult implements Parcelable {

    public static final String EXTRA_PAYMENT_RESULT = "PAYMENT_RESULT";

    private String transactionStatus;
    private String transactionId;
    private String orderId;
    private String paymentAmount;
    private String paymentCurrency;
    private String payeeName;
    private String payeeMobileNumber;
    private String paymentTime;

    public DeshiResult() {
    }

    public DeshiResult(String transactionStatus, String transactionId, String orderId, String paymentAmount,
                       String paymentCurrency, String payeeName, String payeeMobileNumber, String paymentTime) {
        this.transactionStatus = transactionStatus;
        this.transactionId = transactionId;
        this.orderId = orderId;
        this.paymentAmount = paymentAmount;
        this.paymentCurrency = paymentCurrency;
        this.payeeName = payeeName;
        this.payeeMobileNumber = payeeMobileNumber;
        this.paymentTime = paymentTime;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public String getPaymentCurrency() {
        return paymentCurrency;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public String getPayeeMobileNumber() {
        return payeeMobileNumber;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    protected DeshiResult(Parcel in) {
        transactionStatus = in.readString();
        transactionId = in.readString();
        orderId = in.readString();
        paymentAmount = in.readString();
        paymentCurrency = in.readString();
        payeeName = in.readString();
        payeeMobileNumber = in.readString();
        paymentTime = in.readString();
    }

    public static final Creator<DeshiResult> CREATOR = new Creator<DeshiResult>() {
        @Override
        public DeshiResult createFromParcel(Parcel in) {
            return new DeshiResult(in);
        }

        @Override
        public DeshiResult[] newArray(int size) {
            return new DeshiResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(transactionStatus);
        dest.writeString(transactionId);
        dest.writeString(orderId);
        dest.writeString(paymentAmount);
        dest.writeString(paymentCurrency);
        dest.writeString(payeeName);
        dest.writeString(payeeMobileNumber);
        dest.writeString(paymentTime);
    }

    @Override
    public String toString() {
        return "FastpayResult{" +
                "transactionStatus='" + transactionStatus + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", paymentAmount='" + paymentAmount + '\'' +
                ", paymentCurrency='" + paymentCurrency + '\'' +
                ", payeeName='" + payeeName + '\'' +
                ", payeeMobileNumber='" + payeeMobileNumber + '\'' +
                ", paymentTime='" + paymentTime + '\'' +
                '}';
    }
}
