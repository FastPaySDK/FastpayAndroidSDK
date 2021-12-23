package com.deshi.payment.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.deshi.payment.R;


public class CustomTextView extends AppCompatTextView {

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public CustomTextView(Context context) {
        super(context);
        init(null);
    }


    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.custom);
            String fontType = a.getString(R.styleable.custom_textStyle);

            try {
                if (!TextUtils.isEmpty(fontType)) {
                    setTypeface(CustomViewUtil.getTypeFace(getContext(), fontType));
                } else {
                    Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/GothamNormal.ttf");
                    setTypeface(myTypeface);
                }
            } catch (Exception e) { e.printStackTrace(); }
            a.recycle();
        }
    }
}
