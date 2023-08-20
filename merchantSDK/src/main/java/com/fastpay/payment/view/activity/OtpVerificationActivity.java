package com.fastpay.payment.view.activity;

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

import androidx.constraintlayout.widget.ConstraintLayout;

import com.fastpay.payment.R;
import com.fastpay.payment.model.merchant.FastpayRequest;
import com.fastpay.payment.service.utill.ConfigurationUtil;
import com.fastpay.payment.service.utill.CustomAsteriskPassTransformMethod;
import com.fastpay.payment.service.utill.NavigationUtil;
import com.fastpay.payment.view.custom.CustomEditText;
import com.fastpay.payment.view.custom.CustomTextView;

public class OtpVerificationActivity extends BaseActivity implements View.OnKeyListener {

    public static final String EXTRA_OTP = "otp";
    public static final int OTP_VERIFICATION_REQUEST_CODE = 100;

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


        llPin1 = findViewById(R.id.pinField1);
        llPin2 = findViewById(R.id.pinField2);
        llPin3 = findViewById(R.id.pinField3);
        llPin4 = findViewById(R.id.pinField4);
        llPin5 = findViewById(R.id.pinField5);
        llPin6 = findViewById(R.id.pinField6);
        goBackBtn = findViewById(R.id.goBackBtn);
        goBackText = findViewById(R.id.goBackText);


        tvMessage = findViewById(R.id.subTitle);
        mainRootView = findViewById(R.id.clMain);

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
        setResult(RESULT_CANCELED,null);
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
                        returnResult();
                    }
                }
            }
        });

        pin1.setOnKeyListener((view, i, keyEvent) -> {
            if(pin2.getText()!=null || pin2.getText().length() > 0){
                if(keyEvent.getKeyCode() != KeyEvent.KEYCODE_DEL) {
                    pin2.requestFocus();
                }
            }
            return false;
        });

        pin1.setOnPasteListener(text -> {
            pasteOtpText(text);
        });
        pin2.setOnPasteListener(text -> {
            pasteOtpText(text);
        });
        pin3.setOnPasteListener(text -> {
            pasteOtpText(text);
        });
        pin4.setOnPasteListener(text -> {
            pasteOtpText(text);
        });
        pin5.setOnPasteListener(text -> {
            pasteOtpText(text);
        });
        pin6.setOnPasteListener(text -> {
            pasteOtpText(text);
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
                        returnResult();
                    }
                }

            }
        });

        pin2.setOnKeyListener((view, i, keyEvent) -> {
            if(pin2.getText()==null || pin2.getText().length() == 0){
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    pin1.requestFocus();
                    pin1.setSelection(pin1.getText().length());
                }
            }else {
                if(keyEvent.getKeyCode() != KeyEvent.KEYCODE_DEL) {
                    pin3.requestFocus();
                }
            }
            return false;
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
                        returnResult();
                    }
                }
            }
        });

        pin3.setOnKeyListener((view, i, keyEvent) -> {
            if(pin3.getText()==null || pin3.getText().length() == 0){
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    pin2.requestFocus();
                    pin2.setSelection(pin2.getText().length());
                }
            }else {
                if(keyEvent.getKeyCode() != KeyEvent.KEYCODE_DEL) {
                    pin4.requestFocus();
                }
            }
            return false;
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
                        returnResult();
                    }
                }
            }
        });

        pin4.setOnKeyListener((view, i, keyEvent) -> {
            if(pin4.getText()==null || pin4.getText().length() == 0){
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    pin3.requestFocus();
                    pin3.setSelection(pin3.getText().length());
                }
            }else {
                if(keyEvent.getKeyCode() != KeyEvent.KEYCODE_DEL) {
                    pin5.requestFocus();
                }
            }
            return false;
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
                        returnResult();
                    }
                }
            }
        });

        pin5.setOnKeyListener((view, i, keyEvent) -> {
            if(pin5.getText()==null || pin5.getText().length() == 0){
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    pin4.requestFocus();
                    pin4.setSelection(pin4.getText().length());
                }
            }else {
                if(keyEvent.getKeyCode() != KeyEvent.KEYCODE_DEL) {
                    pin6.requestFocus();
                }
            }
            return false;
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
                    llPin5.requestFocus();
                } else {
                    ((TransitionDrawable) llPin6.getBackground()).startTransition(300);

                    if (getOtpFullText().length() == 6) {
                        returnResult();
                    }
                }
            }
        });

        pin6.setOnKeyListener((view, i, keyEvent) -> {
            if(pin6.getText()==null || pin6.getText().length() == 0){
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    pin5.requestFocus();
                    pin5.setSelection(pin5.getText().length());
                }
            }
            return false;
        });

    }

    private void pasteOtpText(CharSequence text) {
        if (text.length() >= 1) {
            pin1.setText(String.valueOf(text.charAt(0)));
        }
        if (text.length() >= 2) {
            pin2.setText(String.valueOf(text.charAt(1)));
        }
        if (text.length() >= 3) {
            pin3.setText(String.valueOf(text.charAt(2)));
        }
        if (text.length() >= 4) {
            pin4.setText(String.valueOf(text.charAt(3)));
        }
        if (text.length() >= 5) {
            pin5.setText(String.valueOf(text.charAt(4)));
        }
        if (text.length() >= 6) {
            pin6.setText(String.valueOf(text.charAt(5)));
        }
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

    private void returnResult() {
        Intent data = new Intent();
        data.putExtra(EXTRA_OTP, getOtpFullText());
        setResult(RESULT_OK,data);
        finish();
    }
}
