package com.fastpay.payment.view.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

/**
 * Created by Sahidul Islam on 2/25/2021.
 */

public class CustomTickView extends View {

    private Paint mPaint = new Paint();
    private Path mPath = new Path();

    public ValueAnimator mTickAnimator;

    float density = 0.0f;
    float maxPoint = 0.0f, midPoint = 0.0f;

    public CustomTickView(Context context) {
        super(context);
        init();
    }

    public CustomTickView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTickView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        density = getContext().getResources().getDisplayMetrics().density;
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(18);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.WHITE);
        canvas.drawPath(mPath, mPaint);
    }

    public void startAnimation() {
        midPoint = (density - 1) * density;
        maxPoint = (float) ((midPoint * .667) + midPoint);

        mPath.moveTo(getWidth() / 4, getHeight() / 2);
        mTickAnimator = ValueAnimator.ofFloat(0f, maxPoint);
        mTickAnimator.setInterpolator(new LinearInterpolator());
        mTickAnimator.setDuration(1000);
        mTickAnimator.addUpdateListener(animation -> {
            float progress = (float) animation.getAnimatedValue();
            if (progress < midPoint) {
                mPath.rLineTo(progress, progress);
            } else {
                mPath.rLineTo(progress, -progress);
            }
            postInvalidate();
        });
        mTickAnimator.start();
    }
}
