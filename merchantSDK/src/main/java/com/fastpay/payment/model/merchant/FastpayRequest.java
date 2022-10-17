package com.fastpay.payment.model.merchant;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.fastpay.payment.view.activity.PaymentActivity;

/**
 * Created by Sahidul Islam on 2/15/2021.
 */

public class FastpayRequest implements Parcelable {

    public static final String EXTRA_PAYMENT_REQUEST = "PAYMENT_REQUEST";
    public static final String EXTRA_PAYMENT_MESSAGE = "PAYMENT_MESSAGE";

    private Context mContext;
    private String mStoreId;
    private String mStorePassword;
    private String mAmount;
    private String mOrderId;
    private String mEnvironment;
    private String mCurrency = "IQD"; // Default is IQD
    private int mStoreLogo;  // Optional
    private long mSessionTimeout = 0L;  // Optional

    public enum Environment {
        Sandbox,
        Production
    }

    public FastpayRequest(Context context) {
        mContext = context;
    }

    public FastpayRequest(Context mContext, String mStoreId, String mStorePassword, String mAmount, String mOrderId, String environment) {
        this.mContext = mContext;
        this.mStoreId = mStoreId;
        this.mStorePassword = mStorePassword;
        this.mAmount = mAmount;
        this.mOrderId = mOrderId;
        this.mEnvironment = environment;
    }

    public FastpayRequest storeId(String storeId) {
        mStoreId = storeId;
        return this;
    }

    public FastpayRequest storePassword(String storePassword) {
        mStorePassword = storePassword;
        return this;
    }

    public FastpayRequest amount(String amount) {
        mAmount = amount;
        return this;
    }

    public FastpayRequest orderId(String orderId) {
        mOrderId = orderId;
        return this;
    }

    public FastpayRequest currency(String currency) {
        if (!currency.isEmpty()) mCurrency = currency;
        return this;
    }

    public FastpayRequest storeLogo(int storeLogo) {
        mStoreLogo = storeLogo;
        return this;
    }

    public FastpayRequest environment(String environment) {
        mEnvironment = environment;
        return this;
    }

    public FastpayRequest sessionTimeout(long timeInSecond) {
        mSessionTimeout = timeInSecond;
        return this;
    }

    public String getStoreId() {
        return mStoreId;
    }

    public String getStorePassword() {
        return mStorePassword;
    }

    public String getAmount() {
        return mAmount;
    }

    public String getOrderId() {
        return mOrderId;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public int getAppLogo() {
        return mStoreLogo;
    }

    public String getEnvironment() {
        return mEnvironment;
    }

    public long getSessionTimeout() {
        return mSessionTimeout;
    }

    public Intent getIntent() {
        return new Intent(mContext, PaymentActivity.class).putExtra(EXTRA_PAYMENT_REQUEST, this);
    }

    private FastpayRequest(Parcel in) {
        mStoreId = in.readString();
        mStorePassword = in.readString();
        mAmount = in.readString();
        mOrderId = in.readString();
        mCurrency = in.readString();
        mStoreLogo = in.readInt();
        mEnvironment = in.readString();
        mSessionTimeout = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mStoreId);
        dest.writeString(mStorePassword);
        dest.writeString(mAmount);
        dest.writeString(mOrderId);
        dest.writeString(mCurrency);
        dest.writeInt(mStoreLogo);
        dest.writeString(mEnvironment);
        dest.writeLong(mSessionTimeout);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FastpayRequest> CREATOR = new Creator<FastpayRequest>() {
        @Override
        public FastpayRequest createFromParcel(Parcel in) {
            return new FastpayRequest(in);
        }

        @Override
        public FastpayRequest[] newArray(int size) {
            return new FastpayRequest[size];
        }
    };
}
