package com.deshi.payment.service.utill;

import android.app.Activity;

import com.deshi.payment.R;


public class NavigationUtil {

    public static void enterPageBottomToUp(Activity activity) {
        activity.overridePendingTransition(R.anim.start_slide_up_1, R.anim.exit_slide_up_1);
    }

    public static void exitPageBottomToUp(Activity activity) {
        activity.overridePendingTransition(R.anim.start_slide_up_2, R.anim.exit_slide_up_2);
    }

    public static void enterPageSide(Activity activity) {
        activity.overridePendingTransition(R.anim.start_slide_side_1, R.anim.exit_slide_side_1);
    }

    public static void exitPageSide(Activity activity) {
        activity.overridePendingTransition(R.anim.start_slide_side_2, R.anim.exit_slide_side_2);
    }
}
