package com.fastpay.payment;

import com.fastpay.payment.service.listener.ListenerFastpayCallback;

/**
 * @Created By Zarraf Ahmed
 * Created 2/29/2024 at 5:25 PM
 */
public class SdkSingleton {

   private static SdkSingleton single_instance = null;

   private ListenerFastpayCallback listenerFastpayCallback;

   public static synchronized SdkSingleton getInstance()
   {
      if (single_instance == null)
         single_instance = new SdkSingleton();

      return single_instance;
   }

   public ListenerFastpayCallback getListenerFastpayCallback() {
      return listenerFastpayCallback;
   }

   public void setListenerFastpayCallback(ListenerFastpayCallback listenerFastpayCallback) {
      this.listenerFastpayCallback = listenerFastpayCallback;
   }
}
