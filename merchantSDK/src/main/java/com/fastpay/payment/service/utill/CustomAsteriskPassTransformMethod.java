package com.fastpay.payment.service.utill;

/**
 * @Created By Zarraf Ahmed
 * Created 8/10/2023 at 11:51 AM
 */
import android.text.method.PasswordTransformationMethod;
import android.view.View;

public class CustomAsteriskPassTransformMethod extends PasswordTransformationMethod {

   @Override
   public CharSequence getTransformation(CharSequence source, View view) {
      return new PasswordCharSequence(source);
   }

   private class PasswordCharSequence implements CharSequence {
      private CharSequence mSource;

      public PasswordCharSequence(CharSequence source) {
         mSource = source;
      }

      public char charAt(int index) {
         return '●';
      }

      public int length() {
         return mSource.length();
      }

      public CharSequence subSequence(int start, int end) {
         return mSource.subSequence(start, end);
      }
   }


}
