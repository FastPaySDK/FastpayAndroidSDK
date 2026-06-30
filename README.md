# FastPayMerchantSDK Integration & Usage Guide

This guide provides step-by-step instructions on how to integrate the **FastPayMerchantSDK** into your Android application and use it to process payments.

## Latest Version
`1.0.2`


---

## 1. Repository Setup

The FastPayMerchantSDK is hosted on GitHub Packages. To authenticate and download the dependency securely, load the credentials dynamically from a properties file (e.g., `github.properties` in the project root) or from environment variables in your `settings.gradle.kts`.

### Configuration in `settings.gradle.kts` (Kotlin DSL)

Add the following configuration to your `settings.gradle.kts`:

```kotlin
// Load GitHub credentials from local properties file
val githubProperties = java.util.Properties().apply {
    val file = file("github.properties")
    if (file.exists()) {
        file.inputStream().use { load(it) }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        
        // Add FastPayMerchantSDK Maven Repository
        maven {
            url = uri("https://maven.pkg.github.com/FastPaySDK/FastpayAndroidSDK")
            credentials {
                username = githubProperties.getProperty("gpr.usr") ?: System.getenv("GPR_USER")
                password = githubProperties.getProperty("gpr.key") ?: System.getenv("GPR_API_KEY")
            }
        }
    }
}
```

### GitHub Properties Setup (`github.properties`)
Create a file named `github.properties` in your root project directory:
```properties
gpr.usr=YOUR_GITHUB_USERNAME
gpr.key=YOUR_GITHUB_PERSONAL_ACCESS_TOKEN
```

---

## 2. Declare the Dependency

Add the dependency to your app-level `build.gradle.kts` (typically located at `app/build.gradle.kts`):

```kotlin
dependencies {
    // ... other dependencies
    
    // FastPay Merchant SDK
    implementation("com.fastpay:merchant-sdk:1.0.1")
}
```

---

## 3. Android Manifest Configuration

The SDK requires internet access to process payments and deep-linking/intent filters to redirect users back to the app after completing the transaction in the browser.

### Configure Redirect Callback Deep Link
Add an `<intent-filter>` to the activity that starts the payment (e.g., `SDKTestActivity`) to handle callback redirection:

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    
    <!-- Required permissions -->
    <uses-permission android:name="android.permission.INTERNET" />
    
    <application ...>
        <activity
            android:name=".SDKTestActivity"
            android:exported="true">
            
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>

            <!-- FastPay Redirect Deep Link Intent Filter -->
            <intent-filter>
                <!-- Configure custom scheme & host matching your callback URL -->
                <data android:scheme="sdk" android:host="test-merchant.com" android:pathPrefix="/callback"/>
                
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />
            </intent-filter>
            
        </activity>
    </application>
</manifest>
```

---

## 4. Implementation & Usage

To trigger payments and retrieve the transaction result, follow these integration steps in your Activity.

### Step 1: Register Activity Result Launcher
Use the modern `registerForActivityResult` API to capture outcomes returned directly by the SDK intent. Declare the launcher variable and register it within your Activity or Fragment initialization code:

```kotlin
import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.fastpay.payment.model.merchant.FastpayRequest
import com.fastpay.payment.model.merchant.FastpayResult

// 1. Declare the launcher property in your Activity or Fragment
private lateinit var sdkResultLauncher: ActivityResultLauncher<Intent>

// 2. Register the launcher (e.g., inside onCreate() or onViewCreated())
sdkResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
    val data = result.data
    when (result.resultCode) {
        Activity.RESULT_OK -> {
            // Extract payment results
            val paymentResult = data?.getParcelableExtra<FastpayResult>(FastpayResult.EXTRA_PAYMENT_RESULT)
            paymentResult?.let {
                val transactionId = it.transactionId
                // Handle payment success (e.g. notify backend or update UI)
            }
        }
        Activity.RESULT_CANCELED -> {
            // Extract failure/cancellation message
            val message = data?.getStringExtra(FastpayRequest.EXTRA_PAYMENT_MESSAGE)
            // Handle cancel/failure (e.g. show Toast to the user)
        }
    }
}
```

### Step 2: Initialize & Start Payment Request
Instantiate a `FastpayRequest` and invoke `startPaymentIntent()` passing your launcher:

```kotlin
import com.fastpay.payment.model.merchant.FastpayRequest
import com.fastpay.payment.model.merchant.FastpaySDK

private fun makePayment(amount: String, orderId: String) {
    val merchantId = "749347_861" // Replace with your Merchant ID
    val secureKey = "ZCqKtkLYHfRPTcV" // Replace with your Secure Key/Password
    val callbackUrl = "sdk://test-merchant.com/callback" // Deep link declared in AndroidManifest.xml

    val request = FastpayRequest(
        activity = this,
        merchantId = merchantId,
        merchantPassword = secureKey,
        amount = amount,
        orderId = orderId,
        sdkMode = FastpaySDK.SANDBOX, // Use FastpaySDK.LIVE for Production
        callbackUrl = callbackUrl
    ) { code, message ->
        // Triggered immediately if preliminary validation (e.g. invalid parameters) fails
        Toast.makeText(this, "Validation Error: $message", Toast.LENGTH_LONG).show()
    }

    // Launch the FastPay payment UI
    request.startPaymentIntent(this, sdkResultLauncher)
}
```

### Step 3: Handle Redirect Callback Parameters
When the user successfully completes the payment flow in an external web interface, the system redirects back to the activity via the configured Intent filter. 

You should extract the transaction parameters in `onCreate()` or `onResume()` / `onNewIntent()`:

```kotlin
override fun onResume() {
    super.onResume()
    
    intent.data?.let { uri ->
        val amount = uri.getQueryParameter("amount")
        val orderId = uri.getQueryParameter("order_id")
        val status = uri.getQueryParameter("status")
        val transactionId = uri.getQueryParameter("transaction_id")

        if (status?.contains("success", ignoreCase = true) == true) {
            Toast.makeText(this, "DeepLink: Success! TxID: $transactionId, Amount: $amount", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "DeepLink: Failed/Canceled for Order: $orderId", Toast.LENGTH_LONG).show()
        }
        
        // Clear intent data to prevent duplicate handling on configuration changes
        intent.data = null
    }
}
```

---

## 5. Parameter Guide

| Parameter Name | Data Type | Description |
| :--- | :--- | :--- |
| `activity` | `Activity` | Context reference of the calling Activity. |
| `merchantId` | `String` | Unique identity registered with FastPay. |
| `merchantPassword` | `String` | Secure API Key / Secret provided by FastPay. |
| `amount` | `String` | The amount to charge (must be greater than 0). |
| `orderId` | `String` | Unique transaction/invoice identifier generated by your app. |
| `sdkMode` | `FastpaySDK` | `FastpaySDK.SANDBOX` for testing, `FastpaySDK.LIVE` for production. |
| `callbackUrl` | `String` | Redirection URL schema registered under AndroidManifest.xml. |
