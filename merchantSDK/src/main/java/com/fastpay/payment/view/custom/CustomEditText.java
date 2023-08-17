package com.fastpay.payment.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.fastpay.payment.R;


public class CustomEditText extends AppCompatEditText {

    private String mPrefix;
    private Rect mPrefixRect = new Rect();
    private OnPasteListener onPasteListener;

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

    public void setOnPasteListener(OnPasteListener listener) {
        this.onPasteListener = listener;
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
    public boolean onTextContextMenuItem(int id) {
        if (id == android.R.id.paste) {
            CharSequence pasteText = getClipboardText();
            if (onPasteListener != null) {
                onPasteListener.onPaste(pasteText);
                return false;
            }else {
                return super.onTextContextMenuItem(id);
            }
        }else {
            return super.onTextContextMenuItem(id);
        }
    }

    private CharSequence getClipboardText() {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null && clipboard.hasPrimaryClip()) {
            android.content.ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
            return item.getText();
        }
        return null;
    }

    @Override
    public int getCompoundPaddingLeft() {
        return super.getCompoundPaddingLeft() + mPrefixRect.width();
    }

    public interface OnPasteListener {
        void onPaste(CharSequence text);
    }

}
