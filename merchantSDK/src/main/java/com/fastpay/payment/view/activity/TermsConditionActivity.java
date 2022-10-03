package com.fastpay.payment.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.fastpay.payment.R;
import com.fastpay.payment.model.merchant.FastpayRequest;
import com.fastpay.payment.service.utill.NavigationUtil;
import com.fastpay.payment.service.utill.ShareData;
import com.fastpay.payment.service.utill.StoreInformationUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Sahidul Islam on 2/15/2021.
 */

public class TermsConditionActivity extends BaseActivity {

    private ImageView backButton;
    private long remainIngTime = 0;
    private static Timer timer = null;
    private long startingTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_condition_layout);
        buildUi();
        initListener();
    }

    @Override
    public void onBackPressed() {
        finish();
        NavigationUtil.exitPageSide(this);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userSessionStart();
    }

    private void buildUi() {
        backButton = findViewById(R.id.goBackBtn);
    }

    private void initListener() {
        backButton.setOnClickListener(view -> onBackPressed());
    }

    private void userSessionStart() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        long finishingTime = StoreInformationUtil.getLongData(TermsConditionActivity.this,ShareData.KEY_FINISHING_TIME,-1L);
        if (finishingTime == -1L){
            finishingTime = System.currentTimeMillis()+ShareData.SESSION_TIME_OUT_VALUE;
            StoreInformationUtil.saveLongData(TermsConditionActivity.this,ShareData.KEY_FINISHING_TIME,finishingTime);
        }
        long timerDuration = finishingTime-System.currentTimeMillis();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.putExtra(FastpayRequest.EXTRA_PAYMENT_MESSAGE, getString(R.string.fp_payment_message_request_timeout));
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        },  (timerDuration) );
    }
}
