# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-ignorewarnings
-repackageclasses
-keepattributes Signature
-keepattributes EnclosingMethod
-keepattributes InnerClasses

#-dontwarn com.fastpay.payment.**
#-keep class com.fastpay.** { *; }

-dontobfuscate
-optimizations !code/allocation/variable

### Androidx
-dontwarn androidx.**
-keep class androidx.** { *; }
-keep interface androidx.** { *; }
-keep class androidx.appcompat.widget.** { *; }

### Webview
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

### Androidx, Material Design
-keep class com.google.android.material.** { *; }
-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**

## Databinding or library depends on databinding
-dontwarn android.databinding.**
-keep class android.databinding.** { *; }

### Retrofit 2
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

### OkHttp3
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-dontwarn javax.annotation.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-dontwarn org.codehaus.mojo.animal_sniffer.*
-dontwarn okhttp3.internal.platform.ConscryptPlatform
-dontwarn org.conscrypt.ConscryptHostnameVerifier

### Crash report
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

### Picasso
-dontwarn com.squareup.okhttp.**

### Stetho, Stetho Realm plugin
-keep class com.facebook.stetho.** {
  *;
}
-dontwarn com.facebook.stetho.**

### Daimajia library
-keep class com.daimajia.easing.** { *; }
-keep interface com.daimajia.easing.** { *; }
-keep class com.daimajia.androidanimations.** { *; }
-keep interface com.daimajia.androidanimations.** { *; }

# And if you use AsyncExecutor:
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}




