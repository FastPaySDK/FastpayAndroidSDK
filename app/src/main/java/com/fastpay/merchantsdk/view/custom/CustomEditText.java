package com.fastpay.merchantsdk.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.fastpay.merchantsdk.R;


public class CustomEditText extends AppCompatEditText {

    private String mPrefix;
    private Rect mPrefixRect = new Rect();

    public CustomEditText(Context context) {
        super(context);
        init(null);
    }

    public CustomEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
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
            } catch (Exception e) {
                e.printStackTrace();
            }
            a.recycle();
        }
    }

    public void setPrefix(String prefix) {
        this.mPrefix = prefix;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!TextUtils.isEmpty(mPrefix)) {
            getPaint().getTextBounds(mPrefix, 0, mPrefix.length(), mPrefixRect);
            mPrefixRect.right += getPaint().measureText(" ");
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!TextUtils.isEmpty(mPrefix)) {
            canvas.drawText(mPrefix, super.getCompoundPaddingLeft(), getBaseline(), getPaint());
        }
    }

    @Override
    public int getCompoundPaddingLeft() {
        return super.getCompoundPaddingLeft() + mPrefixRect.width();
    }

}
