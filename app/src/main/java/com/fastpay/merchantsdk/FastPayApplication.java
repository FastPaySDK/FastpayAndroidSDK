package com.fastpay.merchantsdk;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;


public class FastPayApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
