package com.fastpay.payment.view.activity;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.fastpay.payment.R;
import com.fastpay.payment.service.utill.NavigationUtil;

/**
 * Created by Sahidul Islam on 2/15/2021.
 */

public class TermsConditionActivity extends BaseActivity {

    private ImageView backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_condition_layout);

        buildUi();
        initListener();
    }

    @Override
    public void onBackPressed() {
        NavigationUtil.exitPageSide(this);
        finish();
    }

    private void buildUi() {
        backButton = findViewById(R.id.goBackBtn);
    }

    private void initListener() {
        setSessionFinishedListener(this::onBackPressed);
        backButton.setOnClickListener(view -> onBackPressed());
    }
}
