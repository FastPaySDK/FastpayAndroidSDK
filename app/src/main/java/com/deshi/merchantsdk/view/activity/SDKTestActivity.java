package com.deshi.merchantsdk.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;


import com.deshi.merchantsdk.R;
import com.deshi.merchantsdk.databinding.ActivitySdkTestLayoutBinding;
import com.deshi.payment.model.merchant.DeshiRequest;
import com.deshi.payment.model.merchant.DeshiResult;
import com.deshi.payment.model.merchant.DeshiSDK;


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
                    if (data != null && data.hasExtra(DeshiResult.EXTRA_PAYMENT_RESULT)) {
                        DeshiResult result = data.getParcelableExtra(DeshiResult.EXTRA_PAYMENT_RESULT);
                        Log.e("payment_result", result.getTransactionId());
                    }
                    break;
                case Activity.RESULT_CANCELED:
                    if (data != null && data.hasExtra(DeshiRequest.EXTRA_PAYMENT_MESSAGE)) {
                        String message = data.getStringExtra(DeshiRequest.EXTRA_PAYMENT_MESSAGE);
                        Log.e("payment_result", "Canceled : " + message);
                    }
                    break;
            }
        }
    }

    private void buildUi() {
        layoutBinding.orderIdEditText.setText("ORD51100");
        layoutBinding.paymentAmountEditText.setText("250");

/*        InputStream stream = null;
        try {
            stream = getAssets().open("success.gif");
        } catch (IOException e) {
            e.printStackTrace();
        }

        layoutBinding.customView.playGif(stream);*/
    }

    private void initListener() {
        layoutBinding.payBtn.setOnClickListener(view -> {
            // layoutBinding.customView.tickAnimation();

            String orderId = layoutBinding.orderIdEditText.getText().toString().trim();
            String amount = layoutBinding.paymentAmountEditText.getText().toString().trim();

            if (!orderId.isEmpty() && !amount.isEmpty() && Double.parseDouble(amount) > 0) {
                DeshiRequest request = new DeshiRequest(
                        this,
                        "10000085_727",
                        "Password100@",
                        amount,
                        orderId,
                        DeshiSDK.SANDBOX
                );

/*                FastpayRequest request = new FastpayRequest(this)
                        .orderId("test-order-123")
                        .amount(amount)
                        .storeLogo(R.drawable.ic_fastpay_logo)
                        .environment(FastpaySDK.SANDBOX);*/ // Optional

                startActivityForResult(request.getIntent(), FASTPAY_REQUEST_CODE);
            } else {
                Toast.makeText(this, "Enter amount & order id", Toast.LENGTH_LONG).show();
            }
        });
    }
}