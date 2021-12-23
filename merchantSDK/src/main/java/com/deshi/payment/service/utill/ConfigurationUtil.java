package com.deshi.payment.service.utill;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;


import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.CLIPBOARD_SERVICE;
import static android.content.Context.INPUT_METHOD_SERVICE;

public class ConfigurationUtil {


    public static Double convertDpToPixel(double dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static Double convertPixelsToDp(double px, Context context) {
        return px / (context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        }
    }

    public static void softHideKeyBoard(Activity activity) {
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static boolean isInternetAvailable(Activity context) {
        boolean isConnected = false;

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                NetworkCapabilities capabilities = manager.getNetworkCapabilities(manager.getActiveNetwork());
                isConnected = capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
            } else {
                NetworkInfo[] netInfo = manager.getAllNetworkInfo();
                for (NetworkInfo info : netInfo) {
                    if (info.getTypeName().equalsIgnoreCase("WIFI") || info.getTypeName().equalsIgnoreCase("MOBILE"))
                        if (info.isConnected())
                            isConnected = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isConnected;
    }

    public static String getFormatedAmount(long amount) {
        return NumberFormat.getNumberInstance(Locale.US).format(amount);
    }

    public static String getFormatedAmount(Double amount) {
        return NumberFormat.getNumberInstance(Locale.US).format(amount);
    }

    public static String getFormatedAmount(String amount) {
        return amount;
    }

    public static String getFormattedNumber(String mobileNumber) {
        return mobileNumber.substring(0, 4) + " " + mobileNumber.substring(4, 7) + " " + mobileNumber.substring(7, 10) + " " + mobileNumber.substring(10, mobileNumber.length());
    }

    public static String getFormattedMobileNumber(String mobileNumber) {
        if (mobileNumber.length() == 14) {
            mobileNumber = mobileNumber.substring(4);
            return mobileNumber.substring(0, 3) + " " + mobileNumber.substring(3, 6) + " " + mobileNumber.substring(6, 10);
        } else if (mobileNumber.length() == 13) {
            mobileNumber = mobileNumber.substring(4);
            return mobileNumber.substring(0, 3) + " " + mobileNumber.substring(3, 6) + " " + mobileNumber.substring(6, 9);
        } else {
            return mobileNumber;
        }
    }

    public static void browseUrl(Activity activity, String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(browserIntent);
    }

    public static void ShareViaIntent(Activity activity, String dataLabel, String url) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, dataLabel);
        i.putExtra(Intent.EXTRA_TEXT, url);
        activity.startActivity(Intent.createChooser(i, "Share URL"));
    }

    public static void copyToClipBoard(Activity activity, String dataLabel, String data) {
        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(dataLabel, data);
        clipboard.setPrimaryClip(clip);
    }

    public ObjectAnimator imageRotation(View view, Float startDeg, Float endDeg, int duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", startDeg, endDeg);
        animator.setDuration(duration);
        return animator;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static Drawable roundRectDrawable(int color, int cornerRadius) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(color);
        gd.setCornerRadius(cornerRadius);
        return gd;
    }
}
