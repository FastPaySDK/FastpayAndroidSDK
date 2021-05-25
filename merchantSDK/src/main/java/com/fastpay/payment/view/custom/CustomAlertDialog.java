package com.fastpay.payment.view.custom;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.fastpay.payment.R;
import com.fastpay.payment.service.utill.NavigationUtil;

import java.util.ArrayList;

import static android.content.Context.WINDOW_SERVICE;

//import jp.wasabeef.blurry.Blurry;

public class CustomAlertDialog extends Dialog {

    private TextView dialogTitle, dialogSubtitle, dialogConfirmBtn;
    private ImageView dialogCloseIcon, dialogImage;
    private CardView dialogCardView;

    private ViewGroup rootView;
    private Activity activity;
    private DisplayMetrics displayMetrics;

    private View.OnClickListener confirmationClickListener;
    private View.OnClickListener closeClickListener;

    public CustomAlertDialog(@NonNull Activity activity, ViewGroup rootView) {
        super(activity);
        this.activity = activity;
        this.rootView = rootView;
        doConfig();
    }

    private void doConfig() {
        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        displayMetrics = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);

        setContentView(R.layout.custom_dialog_layout);
        // dialogCardView.setLayoutDirection(ShareData.ENGLISH_LANG.equals(LanguageHelper.getLanguage(activity)) ? View.LAYOUT_DIRECTION_LTR : View.LAYOUT_DIRECTION_RTL);

        dialogCardView = findViewById(R.id.dialogCardView);
        dialogCloseIcon = findViewById(R.id.dialogCloseIcon);
        dialogImage = findViewById(R.id.dialogImage);
        dialogTitle = findViewById(R.id.dialogTitle);
        dialogSubtitle = findViewById(R.id.dialogSubtitle);
        dialogConfirmBtn = findViewById(R.id.dialogConfirmBtn);

        dialogCardView.setMinimumWidth(displayMetrics.widthPixels);

        dialogCloseIcon.setVisibility(View.GONE);
        dialogImage.setVisibility(View.GONE);
        dialogTitle.setVisibility(View.GONE);
        dialogSubtitle.setVisibility(View.GONE);
        dialogConfirmBtn.setVisibility(View.GONE);

        dialogConfirmBtn.setOnClickListener(view -> {
            if (confirmationClickListener != null) {
                confirmationClickListener.onClick(dialogConfirmBtn);
            }
        });

        dialogCloseIcon.setOnClickListener(view -> {
            if (closeClickListener != null) {
                closeClickListener.onClick(dialogCloseIcon);
            }
        });

    }

    public void setImage(int imageId) {
        dialogImage.setImageResource(imageId);
        dialogImage.setVisibility(View.VISIBLE);
    }

    public void setCardBackground(int colorId) {
        dialogCardView.setCardBackgroundColor(activity.getResources().getColor(colorId));
    }

    public void setPositiveButtonStyle(int btnBackground, String btnText, int textColor) {
        dialogConfirmBtn.setText(btnText);
        dialogConfirmBtn.setTextColor(activity.getResources().getColor(textColor));
        dialogConfirmBtn.setBackgroundResource(btnBackground);
        dialogConfirmBtn.setVisibility(View.VISIBLE);
    }

    public void dialogCloseIconVisibility(boolean isVisible) {
        dialogCloseIcon.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public void setTitle(String title, int textColor) {
        dialogTitle.setText(title);
        dialogTitle.setTextColor(activity.getResources().getColor(textColor));
        dialogTitle.setVisibility(View.VISIBLE);
    }

    public void setSubTitle(@Nullable String subTitle, int textColor) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add(subTitle);
        setSubTitle(strings, textColor);
    }

    public void setSubTitle(@Nullable ArrayList<String> subTitles, int textColor) {
        String finalSubtitle = new String();
        if (subTitles != null && subTitles.size() == 0) {
            dialogSubtitle.setVisibility(View.GONE);
        } else {
            if (subTitles.size() == 1) {
                finalSubtitle = subTitles.get(0);

                dialogTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                dialogTitle.setGravity(Gravity.CENTER);

                dialogSubtitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                dialogSubtitle.setGravity(Gravity.CENTER);
            } else if (subTitles.size() > 1) {
                boolean isFirst = true;
                for (String subTitle : subTitles) {
                    finalSubtitle += ((!isFirst ? "\n\n" : "") + "-" + subTitle);
                    isFirst = false;
                }
                dialogTitle.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                dialogTitle.setGravity(Gravity.START);

                dialogSubtitle.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                dialogSubtitle.setGravity(Gravity.START);
            }
            dialogSubtitle.setText(finalSubtitle);
            dialogSubtitle.setTextColor(activity.getResources().getColor(textColor));
            dialogSubtitle.setVisibility(View.VISIBLE);
        }
    }

    public void setOnConfirmationBtnClickListener(View.OnClickListener clickListener) {
        this.confirmationClickListener = clickListener;
    }

    public void setOnCloseBtnClickListener(View.OnClickListener clickListener) {
        this.closeClickListener = clickListener;
    }

    @Override
    public void show() {
        if (rootView != null) {
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
                    .playOn(dialogCardView);*/
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
                    .playOn(dialogCardView);*/

            new Thread(() -> {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                activity.runOnUiThread(() -> {
                    // Blurry.delete(rootView);
                    super.dismiss();
                });
            }).start();
        }
    }

    public void showFailResponse(String title, ArrayList<String> message) {
        setTitle(title, R.color.colorDialogValErrorTitle);
        setSubTitle(message, R.color.colorDialogValErrorSubTitle);
        setCardBackground(R.color.colorDialogValErrorBackground);
        setImage(R.drawable.ic_validation_error_drawable);
        setPositiveButtonStyle(R.drawable.custom_dialog_btn_background, activity.getString(R.string.app_common_try_again), R.color.colorDialogValErrorBackground);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setOnConfirmationBtnClickListener(view -> {
            dismiss();
        });
        show();
    }

    public void showFailResponse(String title, String message) {
        setTitle(title, R.color.colorDialogValErrorTitle);
        setSubTitle(message, R.color.colorDialogValErrorSubTitle);
        setCardBackground(R.color.colorDialogValErrorBackground);
        setImage(R.drawable.ic_validation_error_drawable);
        setPositiveButtonStyle(R.drawable.custom_dialog_btn_background, activity.getString(R.string.app_common_try_again), R.color.colorDialogValErrorBackground);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setOnConfirmationBtnClickListener(view -> {
            dismiss();
        });
        show();
    }

    public void showFailResponse(String title, String message, boolean isCustomDismiss) {
        setTitle(title, R.color.colorDialogValErrorTitle);
        setSubTitle(message, R.color.colorDialogValErrorSubTitle);
        setCardBackground(R.color.colorDialogValErrorBackground);
        setImage(R.drawable.ic_validation_error_drawable);
        setPositiveButtonStyle(R.drawable.custom_dialog_btn_background, activity.getString(R.string.app_common_try_again), R.color.colorDialogValErrorBackground);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        if (!isCustomDismiss) {
            setOnConfirmationBtnClickListener(view -> {
                dismiss();
            });
        }
        show();
    }

    public void showInternetError(boolean isFinish) {
        setTitle(activity.getString(R.string.app_common_api_error), R.color.colorDialogValErrorTitle);
        setSubTitle(activity.getString(R.string.app_common_internet_not_connected), R.color.colorDialogValErrorSubTitle);
        setCardBackground(R.color.colorDialogValErrorBackground);
        setImage(R.drawable.ic_validation_error_drawable);
        setPositiveButtonStyle(R.drawable.custom_dialog_btn_background, activity.getString(R.string.app_common_settings), R.color.colorDialogValErrorBackground);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setOnConfirmationBtnClickListener(view -> {
            activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            if (isFinish) {
                activity.finish();
                NavigationUtil.exitPageSide(activity);
            }
            dismiss();
        });
        show();
    }

    public void showPermissionError(String title, String message) {
        setTitle(title, R.color.colorDialogValErrorTitle);
        setSubTitle(message, R.color.colorDialogValErrorSubTitle);
        setCardBackground(R.color.colorDialogValErrorBackground);
        setImage(R.drawable.ic_validation_error_drawable);
        setPositiveButtonStyle(R.drawable.custom_dialog_btn_background, activity.getString(R.string.app_common_settings), R.color.colorDialogValErrorBackground);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setOnConfirmationBtnClickListener(view -> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
            intent.setData(uri);
            activity.startActivity(intent);
            dismiss();
        });
        show();
    }

    public void showSuccess(String title, ArrayList<String> messages) {
        setTitle(title, R.color.colorSuccessTextColor);
        setSubTitle(messages, R.color.colorSecondTextColor);
        setImage(R.drawable.ic_validation_success_drawable);
        setCardBackground(R.color.colorPrimary);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setPositiveButtonStyle(R.drawable.custom_dialog_bottom_dim_white_background, activity.getString(R.string.app_common_done), R.color.colorPrimary);
        show();
    }
}