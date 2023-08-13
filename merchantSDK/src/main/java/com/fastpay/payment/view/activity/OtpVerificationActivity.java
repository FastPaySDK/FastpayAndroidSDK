package com.fastpay.payment.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fastpay.payment.R;
import com.fastpay.payment.model.merchant.FastpayRequest;
import com.fastpay.payment.model.response.CashoutPaymentSummery;
import com.fastpay.payment.service.listener.CashOutPaymentListener;
import com.fastpay.payment.service.network.request.RequestOtpPayment;
import com.fastpay.payment.service.network.request.SendOtpRequestModel;
import com.fastpay.payment.service.listener.SendOtpListener;
import com.fastpay.payment.service.network.http.HttpParams;
import com.fastpay.payment.service.utill.ConfigurationUtil;
import com.fastpay.payment.service.utill.CustomAsteriskPassTransformMethod;
import com.fastpay.payment.service.utill.NavigationUtil;
import com.fastpay.payment.service.utill.ShareData;
import com.fastpay.payment.view.custom.CustomAlertDialog;
import com.fastpay.payment.view.custom.CustomEditText;
import com.fastpay.payment.view.custom.CustomProgressDialog;
import com.fastpay.payment.view.custom.CustomTextView;

import java.util.ArrayList;

public class OtpVerificationActivity extends BaseActivity implements View.OnKeyListener {

    private CustomEditText pin1,pin2,pin3,pin4,pin5,pin6;
    private CustomTextView tvMessage,goBackText;
    private LinearLayout llPin1,llPin2,llPin3,llPin4,llPin5,llPin6;
    private String mobileNumber,password,orderId,amount,message;
    private FastpayRequest requestExtra;
    private ConstraintLayout mainRootView;
    private ImageView goBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            if (bundle.containsKey(HttpParams.PARAM_MOBILE_NUMBER_2))
                mobileNumber = getIntent().getStringExtra(HttpParams.PARAM_MOBILE_NUMBER_2);
            if (bundle.containsKey(HttpParams.PARAM_ORDER_ID_2))
                orderId = getIntent().getStringExtra(HttpParams.PARAM_ORDER_ID_2);
            if (bundle.containsKey(HttpParams.PARAM_PASSWORD))
                password = getIntent().getStringExtra(HttpParams.PARAM_PASSWORD);
            if (bundle.containsKey(HttpParams.PARAM_AMOUNT))
                amount = getIntent().getStringExtra(HttpParams.PARAM_AMOUNT);
            if (bundle.containsKey(ShareData.KEY_OTP_MESSAGE))
                message = getIntent().getStringExtra(ShareData.KEY_OTP_MESSAGE);
            if (bundle.containsKey(FastpayRequest.EXTRA_PAYMENT_REQUEST)) {
                requestExtra = bundle.getParcelable(FastpayRequest.EXTRA_PAYMENT_REQUEST);
            }
        }

        llPin1 = (LinearLayout) findViewById(R.id.pinField1);
        llPin2 = (LinearLayout) findViewById(R.id.pinField2);
        llPin3 = (LinearLayout) findViewById(R.id.pinField3);
        llPin4 = (LinearLayout) findViewById(R.id.pinField4);
        llPin5 = (LinearLayout) findViewById(R.id.pinField5);
        llPin6 = (LinearLayout) findViewById(R.id.pinField6);
        goBackBtn = findViewById(R.id.goBackBtn);
        goBackText = findViewById(R.id.goBackText);


        tvMessage = findViewById(R.id.subTitle);
        mainRootView = (ConstraintLayout) findViewById(R.id.clMain);

        pin1 = findViewById(R.id.tvPin1);
        pin2 = findViewById(R.id.tvPin2);
        pin3 = findViewById(R.id.tvPin3);
        pin4 = findViewById(R.id.tvPin4);
        pin5 = findViewById(R.id.tvPin5);
        pin6 = findViewById(R.id.tvPin6);

        buildUi();

        initListener();
    }

    @Override
    public void onBackPressed() {
        NavigationUtil.exitPageSide(this);
        finish();
    }

    private void initListener() {
        setSessionFinishedListener(this::onBackPressed);
    }

    private void buildUi() {
        setOtpTextViewConfigure(llPin1,pin1);
        setOtpTextViewConfigure(llPin2,pin2);
        setOtpTextViewConfigure(llPin3,pin3);
        setOtpTextViewConfigure(llPin4,pin4);
        setOtpTextViewConfigure(llPin5,pin5);
        setOtpTextViewConfigure(llPin6,pin6);

        tvMessage.setText(message);

        goBackBtn.setOnClickListener(v->{
            finish();
        });
        goBackText.setOnClickListener(v->{
            finish();
        });

        pin1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || TextUtils.isEmpty(s.toString())) {
                    llPin1.setBackground(new TransitionDrawable(new Drawable[]{getResources().getDrawable(R.drawable.drawable_pin_invalid), getResources().getDrawable(R.drawable.drawable_pin_valid)}));
                } else {
                    ((TransitionDrawable) llPin1.getBackground()).startTransition(300);
                    pin2.requestFocus();

                    if (getOtpFullText().length() == 6) {
                        callApiToVerifyOtp();
                    }
                }
            }
        });

        pin2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || TextUtils.isEmpty(s.toString())) {
                    ((TransitionDrawable) llPin2.getBackground()).reverseTransition(300);
                } else {
                    ((TransitionDrawable) llPin2.getBackground()).startTransition(300);
                    pin3.requestFocus();
                    if (getOtpFullText().length() == 6) {
                        callApiToVerifyOtp();
                    }
                }

            }
        });

        pin3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || TextUtils.isEmpty(s.toString())) {
                    ((TransitionDrawable) llPin3.getBackground()).reverseTransition(300);
                } else {
                    ((TransitionDrawable) llPin3.getBackground()).startTransition(300);
                    pin4.requestFocus();
                    if (getOtpFullText().length() == 6) {
                        callApiToVerifyOtp();
                    }
                }
            }
        });

        pin4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || TextUtils.isEmpty(s.toString())) {
                    ((TransitionDrawable) llPin4.getBackground()).reverseTransition(300);
                } else {
                    ((TransitionDrawable) llPin4.getBackground()).startTransition(300);
                    pin5.requestFocus();
                    if (getOtpFullText().length() == 6) {
                        callApiToVerifyOtp();
                    }
                }
            }
        });

        pin5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || TextUtils.isEmpty(s.toString())) {
                    ((TransitionDrawable) llPin5.getBackground()).reverseTransition(300);
                } else {
                    ((TransitionDrawable) llPin5.getBackground()).startTransition(300);
                    pin6.requestFocus();
                    if (getOtpFullText().length() == 6) {
                        callApiToVerifyOtp();
                    }
                }
            }
        });

        pin6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || TextUtils.isEmpty(s.toString())) {
                    ((TransitionDrawable) llPin6.getBackground()).reverseTransition(300);
                } else {
                    ((TransitionDrawable) llPin6.getBackground()).startTransition(300);

                    if (getOtpFullText().length() == 6) {
                        callApiToVerifyOtp();
                    }
                }
            }
        });

    }

    private void setOtpTextViewConfigure(LinearLayout pinField,EditText editText) {

        InputFilter[] otpFilters = new InputFilter[1];
        otpFilters[0] = new InputFilter.LengthFilter(1);

        TransitionDrawable otpDrawable = new TransitionDrawable(new Drawable[]{getResources().getDrawable(R.drawable.drawable_pin_invalid), getResources().getDrawable(R.drawable.drawable_pin_valid)});
        pinField.setGravity(Gravity.CENTER_VERTICAL);
        pinField.setBackground(otpDrawable);
        editText.setFilters(otpFilters);
        editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        editText.setGravity(Gravity.CENTER);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setTextSize(getResources().getDimension(R.dimen.text_14sp));
        editText.setTransformationMethod(new CustomAsteriskPassTransformMethod());
        editText.setPadding(0, ConfigurationUtil.convertDpToPixel(10, this).intValue(), 0, ConfigurationUtil.convertDpToPixel(10, this).intValue());
    }

    public String getOtpFullText() {
        return pin1.getText().toString() +
                pin2.getText().toString() +
                pin3.getText().toString() +
                pin4.getText().toString() +
                pin5.getText().toString() +
                pin6.getText().toString();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (TextUtils.isEmpty(((CustomEditText) v).getText().toString())) {
                    if (v == pin6)
                        pin5.requestFocus();
                    else if (v == pin5)
                        pin4.requestFocus();
                    else if (v == pin4)
                        pin3.requestFocus();
                    else if (v == pin3)
                        pin2.requestFocus();
                    else if (v == pin2)
                        pin1.requestFocus();
                } else {
                    ((CustomEditText) v).setText("");
                }
                return true;
            }
        }
        return false;
    }

    public void clearPinData() {
        pin1.setText("");
        pin2.setText("");
        pin3.setText("");
        pin4.setText("");
        pin5.setText("");
        pin6.setText("");

        pin1.setBackground(new TransitionDrawable(new Drawable[]{getResources().getDrawable(R.drawable.drawable_pin_invalid), getResources().getDrawable(R.drawable.drawable_pin_valid)}));
        pin2.setBackground(new TransitionDrawable(new Drawable[]{getResources().getDrawable(R.drawable.drawable_pin_invalid), getResources().getDrawable(R.drawable.drawable_pin_valid)}));
        pin3.setBackground(new TransitionDrawable(new Drawable[]{getResources().getDrawable(R.drawable.drawable_pin_invalid), getResources().getDrawable(R.drawable.drawable_pin_valid)}));
        pin4.setBackground(new TransitionDrawable(new Drawable[]{getResources().getDrawable(R.drawable.drawable_pin_invalid), getResources().getDrawable(R.drawable.drawable_pin_valid)}));
        pin5.setBackground(new TransitionDrawable(new Drawable[]{getResources().getDrawable(R.drawable.drawable_pin_invalid), getResources().getDrawable(R.drawable.drawable_pin_valid)}));
        pin6.setBackground(new TransitionDrawable(new Drawable[]{getResources().getDrawable(R.drawable.drawable_pin_invalid), getResources().getDrawable(R.drawable.drawable_pin_valid)}));

        pin1.requestFocus();
    }

    private void callApiToVerifyOtp() {
        if (ConfigurationUtil.isInternetAvailable(this)) {
            CustomProgressDialog.show(this);

            RequestOtpPayment requestModel = new RequestOtpPayment(this, requestExtra.getEnvironment());
            requestModel.buildParams(orderId, amount, mobileNumber, password,getOtpFullText());

            requestModel.setResponseListener(new CashOutPaymentListener() {
                @Override
                public void successResponse(CashoutPaymentSummery data) {
                    CustomProgressDialog.dismiss();
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("PAYMENT_SUMMERY",data);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }

                @Override
                public void failResponse(ArrayList<String> messages) {
                    CustomProgressDialog.dismiss();
                    clearPinData();
                }

                @Override
                public void errorResponse(String error) {
                    CustomProgressDialog.dismiss();
                    clearPinData();
                }
            });
            requestModel.execute();
        } else {
            new CustomAlertDialog(this, mainRootView).showInternetError(false);
        }
    }
}