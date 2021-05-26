package com.fastpay.payment.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.fastpay.payment.R;
import com.fastpay.payment.model.merchant.FastpayRequest;
import com.fastpay.payment.model.merchant.FastpayResult;
import com.fastpay.payment.model.response.InitiationSuccess;
import com.fastpay.payment.model.response.PaymentSummery;
import com.fastpay.payment.model.response.PaymentValidation;
import com.fastpay.payment.service.listener.InitiationApiListener;
import com.fastpay.payment.service.listener.PayWithCredentialApiListener;
import com.fastpay.payment.service.listener.PaymentValidationApiListener;
import com.fastpay.payment.service.network.request.RequestAuthPayment;
import com.fastpay.payment.service.network.request.RequestPaymentInitiate;
import com.fastpay.payment.service.network.request.RequestPaymentValidate;
import com.fastpay.payment.service.utill.ConfigurationUtil;
import com.fastpay.payment.service.utill.DownloadImage;
import com.fastpay.payment.service.utill.FormValidationUtil;
import com.fastpay.payment.service.utill.GifDecoderView;
import com.fastpay.payment.service.utill.NavigationUtil;
import com.fastpay.payment.service.utill.QRCodeHelper;
import com.fastpay.payment.service.utill.ShareData;
import com.fastpay.payment.view.custom.CustomAlertDialog;
import com.fastpay.payment.view.custom.CustomProgressDialog;
import com.fastpay.payment.view.custom.MobileNumberFormat;
import com.google.zxing.client.android.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PaymentActivity extends BaseActivity {

    private ConstraintLayout mainRootView, mobileNumberLayout, errorLayout;
    private ConstraintLayout paymentInitLayout, paymentOptionLayout, qrOptionLayout;
    private ConstraintLayout successLayout, payViaLayout, paymentHeaderLayout;
    private ScrollView paymentLayout;
    private TextView merchantNameTextView, orderIdTextView, paymentAmountTextView;
    private TextView mobileNumberCode, paymentBtn, termsTextView;
    private TextView initialTextView, loginTitleTextView, generateQrTextView;
    private TextView successTextView, backAppTextView, errorTextView, retryTextView;
    private EditText mobileNumberEditText, passwordEditText;
    private ImageView merchantLogoImageView, qrPaymentBtnImageView;
    private ImageView mobileNumberEditTextEndImageView;
    private ImageView qrCodeImageView;
    private ImageView passwordEditTextEndImageView;
    private CheckBox confirmCheckBox;

    private GifDecoderView customTickView;

    private FastpayRequest requestExtra;
    private InitiationSuccess initiationModel;

    private TransitionDrawable mobileNumberBackground1, mobileNumberBackground2;
    private TransitionDrawable successIconBackground, paymentButtonBackground;

    private Handler handler;
    private Runnable runnable;

    private String initialText;
    private int dotCount = 0, animDot = 3;

    private int dotAnimDelay = 700;
    private int successAnimDelay = 1 * 1000;
    private int successDelay = 3 * 1000;
    private int qrPaymentDelay = 10 * 1000;

    private int initialError = 1;
    private int paymentError = 2;
    private int animIdPos = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment_layout);
        initView();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(FastpayRequest.EXTRA_PAYMENT_REQUEST)) {
                requestExtra = bundle.getParcelable(FastpayRequest.EXTRA_PAYMENT_REQUEST);
            }
        }

        initiatePayment();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void finish() {
        super.finish();

        if (handler != null)
            handler.removeCallbacks(runnable);

        NavigationUtil.exitPageSide(this);
    }

/*    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    showPaymentQr();
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(PaymentActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        CustomAlertDialog dialog = new CustomAlertDialog(this, mainRootView);
                        dialog.showFailResponse(getString(R.string.app_common_permission_acceptence), getString(R.string.app_common_permission_acceptence_fail_message));
                        dialog.setOnConfirmationBtnClickListener(view -> {
                            dialog.dismiss();
                            ActivityCompat.requestPermissions(PaymentActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                        });
                    } else {
                        CustomAlertDialog dialog = new CustomAlertDialog(this, mainRootView);
                        dialog.showPermissionError(getString(R.string.app_common_permission_acceptence), getString(R.string.app_common_permission_acceptence_fail_message));
                    }
                }
                break;
        }
    }*/

    private void initView() {
        mainRootView = findViewById(R.id.mainRootView);
        merchantNameTextView = findViewById(R.id.merchantName);
        merchantLogoImageView = findViewById(R.id.merchantLogo);
        orderIdTextView = findViewById(R.id.orderId);
        paymentAmountTextView = findViewById(R.id.paymentAmount);
        paymentBtn = findViewById(R.id.paymentBtn);
        qrPaymentBtnImageView = findViewById(R.id.qrPaymentBtn);
        mobileNumberCode = findViewById(R.id.mobileNumberCode);
        mobileNumberEditText = findViewById(R.id.mobileNumberEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        qrOptionLayout = findViewById(R.id.qrOptionLayout);
        mobileNumberEditTextEndImageView = findViewById(R.id.mobileNumberEditTextEndImageView);
        passwordEditTextEndImageView = findViewById(R.id.passwordEditTextEndImageView);
        termsTextView = findViewById(R.id.termsTextView);
        confirmCheckBox = findViewById(R.id.confirmCheckBox);
        mobileNumberLayout = findViewById(R.id.mobileNumberLayout);
        paymentInitLayout = findViewById(R.id.paymentInitLayout);
        paymentOptionLayout = findViewById(R.id.paymentOptionLayout);
        successLayout = findViewById(R.id.successLayout);
        paymentLayout = findViewById(R.id.paymentLayout);
        initialTextView = findViewById(R.id.initialText);
        loginTitleTextView = findViewById(R.id.loginTitleView);
        generateQrTextView = findViewById(R.id.generateQrTitleView);
        customTickView = findViewById(R.id.customTickView);
        qrCodeImageView = findViewById(R.id.qrCodeView);
        successTextView = findViewById(R.id.successTextView);
        backAppTextView = findViewById(R.id.backAppTextView);
        payViaLayout = findViewById(R.id.payViaLayout);
        paymentHeaderLayout = findViewById(R.id.paymentHeaderLayout);
        errorLayout = findViewById(R.id.errorLayout);
        errorTextView = findViewById(R.id.errorTextView);
        retryTextView = findViewById(R.id.retryTextView);
    }

    private void buildUi() {
        merchantNameTextView.setText(TextUtils.isEmpty(initiationModel.getStoreName()) ? getString(R.string.app_name) : initiationModel.getStoreName());

        if (requestExtra != null && requestExtra.getAppLogo() > 0) {
            merchantLogoImageView.setImageDrawable(ContextCompat.getDrawable(this, requestExtra.getAppLogo()));
        } else {
            if (!TextUtils.isEmpty(initiationModel.getStoreLogo())) {
                new DownloadImage(merchantLogoImageView).execute(initiationModel.getStoreLogo());
            } else {
                Drawable appIcon = getPackageManager().getApplicationIcon(getApplicationInfo());
                merchantLogoImageView.setImageDrawable(appIcon);
            }
        }

        if (!TextUtils.isEmpty(initiationModel.getOrderId()))
            orderIdTextView.setText(getString(R.string.payment_page_order_id, initiationModel.getOrderId()));

        if (!TextUtils.isEmpty(initiationModel.getBillAmount()) && !TextUtils.isEmpty(initiationModel.getCurrency()))
            paymentAmountTextView.setText(ConfigurationUtil.getFormatedAmount(Double.parseDouble(initiationModel.getBillAmount()) + " " + initiationModel.getCurrency()));

        paymentButtonBackground = new TransitionDrawable(new Drawable[]{getResources().getDrawable(R.drawable.drawable_inactive_btn_background), getResources().getDrawable(R.drawable.drawable_active_btn_background)});

        paymentBtn.setEnabled(false);
        paymentBtn.setActivated(false);
        paymentBtn.setTextColor(ContextCompat.getColor(this, R.color.colorWhiteIdentical));
        paymentBtn.setBackground(paymentButtonBackground);

        // Mobile number hints field
        mobileNumberCode.setText(ShareData.COUNTRY_CODE + "  -  ");

        // Mobile number view
        mobileNumberBackground1 = new TransitionDrawable(new Drawable[]{getResources().getDrawable(R.drawable.drawable_edittext_form_background_white), getResources().getDrawable(R.drawable.drawable_edittext_form_invalid_background_white)});
        mobileNumberBackground2 = new TransitionDrawable(new Drawable[]{getResources().getDrawable(R.drawable.drawable_edittext_form_invalid_background_white), getResources().getDrawable(R.drawable.drawable_edittext_form_valid_background_white)});
        successIconBackground = new TransitionDrawable(new Drawable[]{getResources().getDrawable(R.drawable.ic_empty), getResources().getDrawable(R.drawable.ic_success_check_icon)});

        mobileNumberEditText.setHint(getString(R.string.payment_page_mobile_number_hint));
        mobileNumberEditText.setHintTextColor(getResources().getColor(R.color.colorBaseGrayBackground));
        mobileNumberEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});
        mobileNumberEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        mobileNumberEditTextEndImageView.setImageDrawable(successIconBackground);
        mobileNumberEditTextEndImageView.setVisibility(View.VISIBLE);
        mobileNumberEditText.setTransformationMethod(new PasswordTransformationMethod() {
            @Override
            public CharSequence getTransformation(CharSequence source, View view) {
                return source;
            }
        });

        mobileNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || TextUtils.isEmpty(s.toString())) {
                    mobileNumberLayout.setBackground(mobileNumberBackground1);
                    if (animIdPos == R.drawable.drawable_edittext_form_invalid_background_white) {
                        mobileNumberBackground1.reverseTransition(300);
                    }
                    animIdPos = R.drawable.drawable_edittext_form_background_white;

                    mobileNumberEditText.requestFocus();
                } else if (!FormValidationUtil.getInstance().isValidMobileNumber(s.toString())) {
                    if (animIdPos == 0 || animIdPos == R.drawable.drawable_edittext_form_background_white) {
                        mobileNumberLayout.setBackground(mobileNumberBackground1);
                        mobileNumberBackground1.startTransition(300);
                    } else if (animIdPos == R.drawable.drawable_edittext_form_valid_background_white) {
                        mobileNumberLayout.setBackground(mobileNumberBackground2);
                        mobileNumberBackground2.reverseTransition(300);
                        successIconBackground.reverseTransition(100);
                    }
                    animIdPos = R.drawable.drawable_edittext_form_invalid_background_white;

                } else if (FormValidationUtil.getInstance().isValidMobileNumber(s.toString())) {
                    mobileNumberLayout.setBackground(mobileNumberBackground2);
                    if (animIdPos == 0 || animIdPos == R.drawable.drawable_edittext_form_background_white || animIdPos == R.drawable.drawable_edittext_form_invalid_background_white) {
                        mobileNumberBackground2.startTransition(300);
                        successIconBackground.startTransition(100);
                    }
                    animIdPos = R.drawable.drawable_edittext_form_valid_background_white;
                }
                checkReadyForSubmission();
            }
        });
        mobileNumberEditText.addTextChangedListener(new MobileNumberFormat(mobileNumberEditText));

        // Password view
        passwordEditText.setHint(getString(R.string.payment_page_enter_your_password));
        passwordEditText.setText("");
        passwordEditText.setHintTextColor(getResources().getColor(R.color.colorBaseGrayBackground));
        passwordEditText.setTextColor(getResources().getColor(R.color.colorBaseTextColor));
        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
        passwordEditTextEndImageView.setImageResource(R.drawable.ic_invisible_icon);
        passwordEditTextEndImageView.setVisibility(View.VISIBLE);
        passwordEditTextEndImageView.setOnClickListener(view -> {
            if (passwordEditText.getTransformationMethod() == null) {
                passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
                passwordEditTextEndImageView.setImageResource(R.drawable.ic_invisible_icon);
            } else {
                passwordEditText.setTransformationMethod(null);
                passwordEditTextEndImageView.setImageResource(R.drawable.ic_visible_icon);
            }

            passwordEditText.setSelection(passwordEditText.length());
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkReadyForSubmission();
            }
        });

        paymentInitLayout.setVisibility(View.GONE);
        paymentHeaderLayout.setVisibility(View.VISIBLE);
        payViaLayout.setVisibility(View.VISIBLE);
        paymentLayout.setVisibility(View.VISIBLE);
        paymentOptionLayout.setVisibility(View.VISIBLE);

        initListener();
        if (BuildConfig.DEBUG) {
            mobileNumberEditText.setText("1521331666");
            passwordEditText.setText("Password@1");
        }
    }

    private void initListener() {
        confirmCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkReadyForSubmission();
        });

        termsTextView.setOnClickListener(view -> {
            Intent intent = new Intent(PaymentActivity.this, TermsConditionActivity.class);
            startActivity(intent);
            NavigationUtil.enterPageSide(PaymentActivity.this);
        });

        qrPaymentBtnImageView.setOnClickListener(view -> {
            showPaymentQr();

/*            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            } else {
                showPaymentQr();
            }*/
        });

        generateQrTextView.setOnClickListener(view -> {
            showPaymentQr();
/*            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            } else {
                showPaymentQr();
            }*/
        });

        loginTitleTextView.setOnClickListener(view -> {
            qrOptionLayout.setVisibility(View.GONE);
            paymentOptionLayout.setVisibility(View.VISIBLE);
        });

        paymentBtn.setOnClickListener(view -> {
            payWithCredential();
        });
    }

    private void checkReadyForSubmission() {
        if (FormValidationUtil.getInstance().isValidMobileNumber(mobileNumberEditText.getText().toString().trim())
                && !TextUtils.isEmpty(passwordEditText.getText().toString().trim())
                && confirmCheckBox.isChecked()
        ) {
            if (!paymentBtn.isActivated()) {
                paymentBtn.setEnabled(true);
                paymentBtn.setActivated(true);
                paymentButtonBackground.startTransition(300);
            }
        } else {
            if (paymentBtn.isActivated()) {
                paymentBtn.setEnabled(false);
                paymentBtn.setActivated(false);
                paymentButtonBackground.reverseTransition(300);
            }
        }
    }

    private void initiatePayment() {
        if (requestExtra == null || requestExtra.getStoreId().isEmpty() || requestExtra.getStorePassword().isEmpty()) {
            Intent intent = new Intent();
            intent.putExtra(FastpayRequest.EXTRA_PAYMENT_MESSAGE, getString(R.string.payment_message_initial_failed));
            setResult(Activity.RESULT_CANCELED, intent);
            finish();
        }

        if (requestExtra.getOrderId().isEmpty()) {
            Intent intent = new Intent();
            intent.putExtra(FastpayRequest.EXTRA_PAYMENT_MESSAGE, getString(R.string.payment_message_orderid_empty));
            setResult(Activity.RESULT_CANCELED, intent);
            finish();
        }

        if (requestExtra.getAmount().isEmpty()) {
            Intent intent = new Intent();
            intent.putExtra(FastpayRequest.EXTRA_PAYMENT_MESSAGE, getString(R.string.payment_message_order_amount_empty));
            setResult(Activity.RESULT_CANCELED, intent);
            finish();
        }

        if (ConfigurationUtil.isInternetAvailable(this)) {
            showInitialAnim();
            RequestPaymentInitiate paymentInitiate = new RequestPaymentInitiate(this, requestExtra.getEnvironment());
            paymentInitiate.buildParams(requestExtra);
            paymentInitiate.setResponseListener(new InitiationApiListener() {
                @Override
                public void successResponse(InitiationSuccess model) {
                    if (model != null && !TextUtils.isEmpty(model.getToken())) {
                        initiationModel = model;
                        buildUi();
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra(FastpayRequest.EXTRA_PAYMENT_MESSAGE, getString(R.string.payment_message_token_empty));
                        setResult(Activity.RESULT_CANCELED, intent);
                        finish();
                    }
                }

                @Override
                public void failResponse(ArrayList<String> messages) {
                    Intent intent = new Intent();
                    intent.putExtra(FastpayRequest.EXTRA_PAYMENT_MESSAGE, TextUtils.join("\n\n", messages));
                    setResult(Activity.RESULT_CANCELED, intent);
                    finish();
                }

                @Override
                public void errorResponse(String error) {
                    Intent intent = new Intent();
                    intent.putExtra(FastpayRequest.EXTRA_PAYMENT_MESSAGE, error);
                    setResult(Activity.RESULT_CANCELED, intent);
                    finish();
                }
            });
            paymentInitiate.execute();
        } else {
            new CustomAlertDialog(this, mainRootView).showInternetError(true);
        }
    }

    private void payWithCredential() {
        if (ConfigurationUtil.isInternetAvailable(this)) {
            CustomProgressDialog.show(this);

            String mobileNumber = ShareData.COUNTRY_CODE + mobileNumberEditText.getText().toString().replaceAll("\\s+", "");
            String password = passwordEditText.getText().toString().trim();
            String orderId = initiationModel.getOrderId();
            String token = initiationModel.getToken();

            RequestAuthPayment authPayment = new RequestAuthPayment(this, requestExtra.getEnvironment());
            authPayment.buildParams(orderId, token, mobileNumber, password);
            authPayment.setResponseListener(new PayWithCredentialApiListener() {
                @Override
                public void successResponse(PaymentSummery model) {
                    // CustomProgressDialog.dismiss();
                    validatePayment();
                }

                @Override
                public void failResponse(ArrayList<String> messages) {
                    showError(TextUtils.join("\n\n", messages), paymentError);
                    CustomProgressDialog.dismiss();
                }

                @Override
                public void errorResponse(String message) {
                    showError(message, paymentError);
                    CustomProgressDialog.dismiss();
                }
            });
            authPayment.execute();
        } else {
            new CustomAlertDialog(this, mainRootView).showInternetError(true);
        }
    }

    private void validatePayment() {
        if (ConfigurationUtil.isInternetAvailable(this)) {
            if (!CustomProgressDialog.isShowing())
                CustomProgressDialog.show(this);

            RequestPaymentValidate paymentValidate = new RequestPaymentValidate(this, requestExtra.getEnvironment());
            paymentValidate.buildParams(requestExtra.getStoreId(), requestExtra.getStorePassword(), requestExtra.getOrderId());
            paymentValidate.setResponseListener(new PaymentValidationApiListener() {
                @Override
                public void successResponse(PaymentValidation model) {
                    CustomProgressDialog.dismiss();
                    showSuccessResult(model);
                }

                @Override
                public void failResponse(ArrayList<String> messages) {
                    showError(TextUtils.join("\n\n", messages), paymentError);
                    CustomProgressDialog.dismiss();
                }

                @Override
                public void errorResponse(String message) {
                    showError(message, paymentError);
                    CustomProgressDialog.dismiss();
                }
            });
            paymentValidate.execute();
        } else {
            new CustomAlertDialog(this, mainRootView).showInternetError(true);
        }
    }

    private void checkQrPayment() {
        if (ConfigurationUtil.isInternetAvailable(this)) {

            RequestPaymentValidate paymentValidate = new RequestPaymentValidate(this, requestExtra.getEnvironment());
            paymentValidate.buildParams(requestExtra.getStoreId(), requestExtra.getStorePassword(), requestExtra.getOrderId());
            paymentValidate.setResponseListener(new PaymentValidationApiListener() {
                @Override
                public void successResponse(PaymentValidation model) {
                    handler.removeCallbacksAndMessages(null);
                    showSuccessResult(model);
                }

                @Override
                public void failResponse(ArrayList<String> messages) {
                }

                @Override
                public void errorResponse(String message) {
                }
            });
            paymentValidate.execute();
        } else {
            new CustomAlertDialog(this, mainRootView).showInternetError(true);
        }
    }

    private void showError(String message, int errorState) {
        errorTextView.setText(message);

        if (errorState == paymentError) {
            paymentHeaderLayout.setVisibility(View.GONE);
            payViaLayout.setVisibility(View.GONE);
            paymentOptionLayout.setVisibility(View.GONE);
        }

        errorLayout.setVisibility(View.VISIBLE);

        retryTextView.setOnClickListener(view -> {
            if (errorState == initialError) {
                errorLayout.setVisibility(View.GONE);
                initiatePayment();
            } else if (errorState == paymentError) {
                errorLayout.setVisibility(View.GONE);
                paymentHeaderLayout.setVisibility(View.VISIBLE);
                payViaLayout.setVisibility(View.VISIBLE);
                paymentOptionLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private FastpayResult getPaymentResult(PaymentValidation model) {
        return new FastpayResult(model.getStatus(), model.getTransactionId(),
                model.getMerchantOrderId(), model.getReceivedAmount(), model.getCurrency(),
                model.getCustomerName(), model.getCustomerMobileNumber(), model.getPaymentAt());
    }

    private void showPaymentQr() {
        if (!TextUtils.isEmpty(initiationModel.getQrToken())) {
            paymentOptionLayout.setVisibility(View.GONE);
            qrOptionLayout.setVisibility(View.VISIBLE);

            QRCodeHelper qrCodeHelper = new QRCodeHelper(this, qrCodeImageView, null);
            qrCodeHelper.generateQRWithOutImage(initiationModel.getQrToken());

            handler = new Handler(Looper.getMainLooper());
            runnable = () -> {
                checkQrPayment();
                handler.postDelayed(runnable, qrPaymentDelay);
            };
        } else {
            Toast.makeText(this, getString(R.string.payment_page_qr_token_empty), Toast.LENGTH_SHORT).show();
        }
    }

    private void showInitialAnim() {
        if (paymentInitLayout.getVisibility() == View.GONE)
            paymentInitLayout.setVisibility(View.VISIBLE);

        handler = new Handler(Looper.getMainLooper());
        runnable = () -> {
            if (dotCount == animDot) {
                dotCount = 0;
                runOnUiThread(() -> {
                    initialText = getString(R.string.initial_page_initializing);
                    initialTextView.setText(initialText);
                });
            } else {
                dotCount++;

                initialText = getString(R.string.initial_page_initializing);
                for (int i = 0; i < dotCount; i++) {
                    initialText = initialText.concat(" .");
                }

                runOnUiThread(() -> {
                    initialTextView.setText(initialText);
                });
            }
            handler.postDelayed(runnable, dotAnimDelay);
        };
        handler.post(runnable);
    }

    private void showSuccessResult(PaymentValidation model) {
        paymentLayout.setVisibility(View.GONE);
        successLayout.setVisibility(View.VISIBLE);

        try {
            InputStream stream = getAssets().open("success.gif");
            customTickView.playGif(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            successTextView.setVisibility(View.VISIBLE);
            backAppTextView.setVisibility(View.VISIBLE);

            handler.postDelayed(() -> {
                Intent intent = new Intent();
                intent.putExtra(FastpayResult.EXTRA_PAYMENT_RESULT, getPaymentResult(model));
                setResult(Activity.RESULT_OK, intent);
                finish();
            }, successDelay);
        }, successAnimDelay);
    }
}
