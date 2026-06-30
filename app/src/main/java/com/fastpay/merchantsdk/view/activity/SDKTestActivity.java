package com.fastpay.merchantsdk.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;

import com.fastpay.merchantsdk.R;
import com.fastpay.merchantsdk.databinding.ActivitySdkTestLayoutBinding;
import com.fastpay.payment.BuildConfig;
import com.fastpay.payment.model.merchant.FastpayRequest;
import com.fastpay.payment.model.merchant.FastpayResult;
import com.fastpay.payment.model.merchant.FastpaySDK;

import java.util.Random;

public class SDKTestActivity extends BaseActivity {

    private ActivityResultLauncher<Intent> sdkResultLauncher;
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

    private void buildUi() {
        if (layoutBinding.orderIdEditText.getText().toString().isEmpty()) {
            layoutBinding.orderIdEditText.setText(getSaltString());
        }
        if (layoutBinding.paymentAmountEditText.getText().toString().isEmpty()) {
            layoutBinding.paymentAmountEditText.setText("250");
        }

        try {
            if(BuildConfig.DEBUG){
                Log.i("CALLBACK URL", "buildUi: ....................."+getIntent().getData());
            }
            String amount = getIntent().getData().getQueryParameter("amount");
            String orderId = getIntent().getData().getQueryParameter("order_id");
            String status = getIntent().getData().getQueryParameter("status");
            String transaction_id = getIntent().getData().getQueryParameter("transaction_id");
            if (status.contains("success")){
                Toast.makeText(SDKTestActivity.this,"Payment is completed:: transactionID:::"+transaction_id+" and amount:::"+amount,Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(SDKTestActivity.this,"Payment is failed ::: ORDER ID:::"+orderId,Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){

        }

        sdkResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Intent data = result.getData();
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            if (data != null && data.hasExtra(FastpayResult.EXTRA_PAYMENT_RESULT)) {
                                FastpayResult fastpayResult = data.getParcelableExtra(FastpayResult.EXTRA_PAYMENT_RESULT);

                                if(BuildConfig.DEBUG){
                                    Log.e("payment_result", fastpayResult.getTransactionId());
                                }
                                Toast.makeText(SDKTestActivity.this,"Payment Result:: SUCCESS =>"+fastpayResult.getTransactionId(),Toast.LENGTH_LONG).show();
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
        );
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
                FastpayRequest request = new FastpayRequest(this, "749347_861", "ZCqKtkLYHfRPTcV",
                        amount, orderId, FastpaySDK.SANDBOX, "sdk://fastpay-sdk.com/callback", (sdkStatus, message) -> Toast.makeText(SDKTestActivity.this,message,Toast.LENGTH_LONG).show());

/*                FastpayRequest request = new FastpayRequest(this)
                        .orderId("test-order-123")
                        .amount(amount)
                        .storeLogo(R.drawable.ic_fastpay_logo)
                        .environment(FastpaySDK.SANDBOX);*/ // Optional
                request.startPaymentIntent(SDKTestActivity.this,sdkResultLauncher);
                //startActivityForResult(request.getIntent(), FASTPAY_REQUEST_CODE);
            } else {
                Toast.makeText(this, "Enter amount & order id", Toast.LENGTH_LONG).show();
            }
        });
    }
}
