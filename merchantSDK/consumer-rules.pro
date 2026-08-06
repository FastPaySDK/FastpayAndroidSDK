# ProGuard rules for Fastpay Android SDK consumers
# This ensures that the SDK classes and members are kept during the client app's minification process.

-keep class com.fastpay.** { *; }
-dontwarn com.fastpay.**
