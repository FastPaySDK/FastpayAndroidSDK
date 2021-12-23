package com.deshi.payment.view.custom;

import android.content.Context;
import android.graphics.Typeface;

public class CustomViewUtil {

    public static Typeface getTypeFace(Context context, String fontType) {
        Typeface typeface = null;
        if(fontType.equalsIgnoreCase("0")){
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/GothamNormal.ttf");
        } else if(fontType.equalsIgnoreCase("1")){
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/GothamBold.ttf");
        } else if(fontType.equalsIgnoreCase("2")) {
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/GothamLight.ttf");
        } else if(fontType.equalsIgnoreCase("3")) {
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/GothamMedium.ttf");
        }
        return typeface;
    }
}
