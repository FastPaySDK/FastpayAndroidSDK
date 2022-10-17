package com.fastpay.payment.service.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.fastpay.payment.service.utill.ShareData;

/**
 * Created by Sahidul Islam on 16/10/22.
 */
public class UserSessionReceiver extends BroadcastReceiver {

    private static OnSessionReceiverListener sessionReceiverListener;

    public interface OnSessionReceiverListener{
        void onSessionFinished();
    }

    public void setSessionReceiverListener(OnSessionReceiverListener listener){
        sessionReceiverListener = listener;
    }

    @Override
    public final void onReceive(Context context, Intent intent) {

        if (intent != null) {
            if (intent.getAction().equals(ShareData.INTENT_USER_SESSION_FINISHED)) {
               if(sessionReceiverListener != null) sessionReceiverListener.onSessionFinished();
            }
        }
    }
}
