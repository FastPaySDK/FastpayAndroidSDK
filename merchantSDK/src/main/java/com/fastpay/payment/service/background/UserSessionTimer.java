package com.fastpay.payment.service.background;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.fastpay.payment.service.utill.ShareData;
import com.fastpay.payment.service.utill.StoreInformationUtil;

import java.util.concurrent.TimeUnit;

/**
 * Created by Sahidul Islam on 16/10/22.
 */
public class UserSessionTimer extends Service {

    private CountDownTimer countDownTimer;

    @Override
    public void onCreate() {
        // super.onCreate();
        countDownTimer = createTimer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // return super.onStartCommand(intent, flags, startId);
        countDownTimer.start();
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        // super.onDestroy();
        countDownTimer.cancel();
    }

    private CountDownTimer createTimer() {
        long appTimerCountInMillis = StoreInformationUtil.getLongData(getApplicationContext(), ShareData.KEY_FINISHING_TIME, ShareData.USER_SESSION_TIMER_TARGET);
        long timerIntervalMillis = TimeUnit.SECONDS.toMillis(ShareData.USER_SESSION_TIMER_INTERVAL);
        long timerCountInMillis = TimeUnit.SECONDS.toMillis(appTimerCountInMillis);

        return new CountDownTimer(timerCountInMillis, timerIntervalMillis) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("onTimerTick: ", String.valueOf(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent();
                intent.setAction(ShareData.INTENT_USER_SESSION_FINISHED);
                sendBroadcast(intent);

                stopSelf();
            }
        };
    }
}
