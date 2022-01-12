package com.fastpay.payment.service.network.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;

import com.google.zxing.client.android.BuildConfig;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class BaseHttp extends AsyncTask<Void, Void, String> {

    private Context mContext;
    private String requestUrl, requestMethod;
    private String requestParams;

    private static final String REQUEST_METHOD_GET = "GET";
    private static final String REQUEST_METHOD_POST = "POST";

    private static final int READ_TIMEOUT = 1200000;
    private static final int CONNECT_TIMEOUT = 1200000;

    public BaseHttp(Context context, String requestUrl) {
        this.mContext = context;
        this.requestUrl = requestUrl;
        this.requestMethod = REQUEST_METHOD_POST;
        if (BuildConfig.DEBUG)
            Log.v("BaseHttp: ", requestUrl);
    }

    public void post(String requestParams) {
        this.requestMethod = REQUEST_METHOD_POST;
        this.requestParams = requestParams;
        if (BuildConfig.DEBUG)
            Log.v("BaseHttp: ", "Params: " + requestParams);
    }

    @Override
    protected String doInBackground(Void... max) {
        if (isNetworkAvailable(mContext)) {
            String response = sendRequest(requestUrl, requestMethod, requestParams);
            onBackgroundTask(response);
            return response;
        } else {
            // return null means no response
            onBackgroundTask(null);
            return null;
        }
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        if (BuildConfig.DEBUG)
            Log.v("BaseHttp: ", "Response: " + response);
        onTaskComplete();
    }

    public abstract void onBackgroundTask(String response);

    public abstract void onTaskComplete();

    private String sendRequest(String requestUrl, String requestMethod, String requestParams) {
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(requestMethod);
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setReadTimeout(READ_TIMEOUT);
            httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT);

            if (requestMethod.equals(REQUEST_METHOD_GET)) {
                httpURLConnection.setDoOutput(false);
            } else {
                httpURLConnection.setDoOutput(true);
            }

            if (requestParams != null) {
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(requestParams);

                writer.flush();
                writer.close();
                outputStream.close();
            }

            return getResponseInString(httpURLConnection);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    private String getResponseInString(HttpURLConnection httpURLConnection) throws IOException {
        if (BuildConfig.DEBUG)
            Log.v("BaseHttp: ", "Status: " + httpURLConnection.getResponseCode());

        StringBuilder result = new StringBuilder();
        try {
            InputStream inputStream;
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            } else {
                inputStream = new BufferedInputStream(httpURLConnection.getErrorStream());
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            httpURLConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager != null && connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
