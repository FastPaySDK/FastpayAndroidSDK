package com.fastpay.payment.view.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    public static OnSessionFinishedListener sessionFinishedListener;

    public interface OnSessionFinishedListener{
        void onSessionFinished();
    }

    public void setSessionFinishedListener(OnSessionFinishedListener listener){
        sessionFinishedListener = listener;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
