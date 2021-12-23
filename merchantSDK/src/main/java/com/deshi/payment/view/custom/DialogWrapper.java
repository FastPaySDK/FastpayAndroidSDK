package com.deshi.payment.view.custom;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;




//import jp.wasabeef.blurry.Blurry;

import static android.content.Context.WINDOW_SERVICE;

import com.deshi.payment.R;

/**
 * Created by Sahidul Islam on 19-Jun-20.
 */

public class DialogWrapper extends Dialog {

    private ViewGroup rootView;
    private View dialogView;
    private Activity activity;
    private DisplayMetrics displayMetrics;

    public DialogWrapper(@NonNull Activity activity, ViewGroup rootView) {
        super(activity);
        this.activity = activity;
        this.rootView = rootView;
    }

    private void doConfig() {

    }

    public void setDialogView(View view) {
        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        displayMetrics = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);

        dialogView = view;
        dialogView.findViewById(R.id.dialogCardView).setMinimumWidth(displayMetrics.widthPixels);
        setContentView(view);
    }

    @Override
    public void show() {
        if (rootView != null) {
            setCancelable(false);
            setCanceledOnTouchOutside(false);
/*            Blurry.with(getContext())
                    .radius(10)
                    .sampling(2)
                    .color(getContext().getResources().getColor(R.color.colorBlur))
                    .async()
                    .animate(300)
                    .onto(rootView);*/

/*            YoYo.with(Techniques.ZoomIn)
                    .pivot(displayMetrics.widthPixels / 2, displayMetrics.heightPixels / 2)
                    .duration(400)
                    .repeat(0)
                    .playOn(dialogView);*/
        }
        super.show();
    }

    @Override
    public void dismiss() {
        if (rootView != null) {
/*            YoYo.with(Techniques.ZoomOut)
                    .pivot(displayMetrics.widthPixels / 2, displayMetrics.heightPixels / 2)
                    .duration(200)
                    .repeat(0)
                    .playOn(dialogView);*/

            new Thread(() -> {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                activity.runOnUiThread(() -> {
                  //  Blurry.delete(rootView);
                    super.dismiss();
                });
            }).start();
        }
    }
}
