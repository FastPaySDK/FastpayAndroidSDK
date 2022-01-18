package com.deshi.payment.model.merchant;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.deshi.payment.view.activity.PaymentActivity;

/**
 * Created by Sahidul Islam on 2/15/2021.
 */

public class DeshiRequest implements Parcelable {

    public static final String EXTRA_PAYMENT_REQUEST = "PAYMENT_REQUEST";
    public static final String EXTRA_PAYMENT_MESSAGE = "PAYMENT_MESSAGE";
    private Context mContext;

    private String mStoreId;
    private String mStorePassword;
    private String mAmount;
    private String mOrderId;
    private String mEnvironment;
    private String mCurrency = "BDT"; // Default is BDT
    private int mStoreLogo;  // Optional

    public enum Environment {
        Sandbox,
        Production
    }

    public DeshiRequest(Context context) {
        mContext = context;
    }

    public DeshiRequest(Context mContext, String mStoreId, String mStorePassword, String mAmount, String mOrderId, String environment) {
        this.mContext = mContext;
        this.mStoreId = mStoreId;
        this.mStorePassword = mStorePassword;
        this.mAmount = mAmount;
        this.mOrderId = mOrderId;
        this.mEnvironment = environment;
    }

    public DeshiRequest storeId(String storeId) {
        mStoreId = storeId;
        return this;
    }

    public DeshiRequest storePassword(String storePassword) {
        mStorePassword = storePassword;
        return this;
    }

    public DeshiRequest amount(String amount) {
        mAmount = amount;
        return this;
    }

    public DeshiRequest orderId(String orderId) {
        mOrderId = orderId;
        return this;
    }

    public DeshiRequest currency(String currency) {
        if (!currency.isEmpty()) mCurrency = currency;
        return this;
    }

    public DeshiRequest storeLogo(int storeLogo) {
        mStoreLogo = storeLogo;
        return this;
    }

    public DeshiRequest environment(String environment) {
        mEnvironment = environment;
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

    public Intent getIntent() {
        return new Intent(mContext, PaymentActivity.class).putExtra(EXTRA_PAYMENT_REQUEST, this);
    }

    private DeshiRequest(Parcel in) {
        mStoreId = in.readString();
        mStorePassword = in.readString();
        mAmount = in.readString();
        mOrderId = in.readString();
        mCurrency = in.readString();
        mStoreLogo = in.readInt();
        mEnvironment = in.readString();
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
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DeshiRequest> CREATOR = new Creator<DeshiRequest>() {
        @Override
        public DeshiRequest createFromParcel(Parcel in) {
            return new DeshiRequest(in);
        }

        @Override
        public DeshiRequest[] newArray(int size) {
            return new DeshiRequest[size];
        }
    };
}
