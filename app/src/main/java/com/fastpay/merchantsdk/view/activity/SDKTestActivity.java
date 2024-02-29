package com.fastpay.merchantsdk.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcel;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.fastpay.merchantsdk.R;
import com.fastpay.merchantsdk.databinding.ActivitySdkTestLayoutBinding;
import com.fastpay.payment.model.merchant.FastpayRequest;
import com.fastpay.payment.model.merchant.FastpayResult;
import com.fastpay.payment.model.merchant.FastpaySDK;
import com.fastpay.payment.service.listener.ListenerFastpayCallback;

import java.util.Random;

public class SDKTestActivity extends BaseActivity {

    ActivitySdkTestLayoutBinding layoutBinding;

    private static final int FASTPAY_REQUEST_CODE = 101;

    @Override
    public void initView() {
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_sdk_test_layout);
        buildUi();
        initListener();
    }

    @Override
    public View getRootView() {
        return layoutBinding.getRoot();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FASTPAY_REQUEST_CODE) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    if (data != null && data.hasExtra(FastpayResult.EXTRA_PAYMENT_RESULT)) {
                        FastpayResult result = data.getParcelableExtra(FastpayResult.EXTRA_PAYMENT_RESULT);
                        Log.e("payment_result", result.getTransactionId());
                        Toast.makeText(SDKTestActivity.this,"Payment Result:: SUCCESS =>"+result.getTransactionId(),Toast.LENGTH_LONG).show();
                    }
                    break;
                case Activity.RESULT_CANCELED:
                    if (data != null && data.hasExtra(FastpayRequest.EXTRA_PAYMENT_MESSAGE)) {
                        String message = data.getStringExtra(FastpayRequest.EXTRA_PAYMENT_MESSAGE);
                        Toast.makeText(SDKTestActivity.this,"Payment Result:: CANCELTED/FAILED =>"+message,Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    }

    private void buildUi() {
        layoutBinding.orderIdEditText.setText(getSaltString());
        layoutBinding.paymentAmountEditText.setText("250");

/*        InputStream stream = null;
        try {
            stream = getAssets().open("success.gif");
        } catch (IOException e) {
            e.printStackTrace();
        }

        layoutBinding.customView.playGif(stream);*/
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    private void initListener() {
        layoutBinding.payBtn.setOnClickListener(view -> {
            // layoutBinding.customView.tickAnimation();

            String orderId = layoutBinding.orderIdEditText.getText().toString().trim();
            String amount = layoutBinding.paymentAmountEditText.getText().toString().trim();

            if (!orderId.isEmpty() && !amount.isEmpty() && Double.parseDouble(amount) > 0) {
                /*FastpayRequest request = new FastpayRequest(this, "754912_901", "953751sS1@#",
                        amount, orderId, FastpaySDK.PRODUCTION);*/
                FastpayRequest request = new FastpayRequest(this, "831463_230", "D@\\!n2o2$0p3e71aEku",
                        amount, orderId, FastpaySDK.PRODUCTION, new ListenerFastpayCallback() {
                    @Override
                    public void sdkCallBack(FastpayRequest.SDKStatus sdkStatus, String message) {
                        Toast.makeText(SDKTestActivity.this,message,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(@NonNull Parcel dest, int flags) {

                    }
                });

/*                FastpayRequest request = new FastpayRequest(this)
                        .orderId("test-order-123")
                        .amount(amount)
                        .storeLogo(R.drawable.ic_fastpay_logo)
                        .environment(FastpaySDK.SANDBOX);*/ // Optional
                request.startPaymentIntent(SDKTestActivity.this,FASTPAY_REQUEST_CODE);
                //startActivityForResult(request.getIntent(), FASTPAY_REQUEST_CODE);
            } else {
                Toast.makeText(this, "Enter amount & order id", Toast.LENGTH_LONG).show();
            }
        });
    }
}
