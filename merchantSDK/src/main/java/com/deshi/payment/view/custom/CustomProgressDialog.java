package com.deshi.payment.view.custom;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import com.deshi.payment.R;


public class CustomProgressDialog {

    private static Dialog customProgressDialog;

    public static void show(Activity activity) {
        customProgressDialog = new Dialog(activity);
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(android.R.color.transparent)));
        customProgressDialog.setCancelable(false);

        View progressDialogView = activity.getLayoutInflater().inflate(R.layout.custom_dialog_progress_dialog, null);
        customProgressDialog.setContentView(progressDialogView);
        customProgressDialog.show();
    }

    public static void dismiss() {
        try {
            if (customProgressDialog.isShowing())
                customProgressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isShowing() {
        return customProgressDialog.isShowing();
    }

    public static void setCancelable(boolean status) {
        customProgressDialog.setCancelable(status);
    }
}
