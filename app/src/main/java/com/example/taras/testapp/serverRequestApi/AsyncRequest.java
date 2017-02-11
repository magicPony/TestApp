package com.example.taras.testapp.serverRequestApi;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static com.example.taras.testapp.CastUtils.inputStreamToString;

/**
 * Created by Taras on 09/02/2017.
 */

public class AsyncRequest extends AsyncTask<String, Void, String> {

    private IOnPostExecute mOnPostExecute;

    public AsyncRequest(IOnPostExecute onPostExecute) {
        mOnPostExecute = onPostExecute;
    }

    @Override
    protected String doInBackground(String[] strings) {
        for (String s : strings) {
            try {
                return getResponse(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(String response) {
        mOnPostExecute.onSuccess(response);
    }

    private String getResponse(String requestUrl) throws IOException {
        String result = "";
        int resCode;

        URL url = new URL(requestUrl);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setAllowUserInteraction(false);
        httpConn.setInstanceFollowRedirects(true);
        httpConn.setRequestMethod("GET");
        httpConn.connect();
        resCode = httpConn.getResponseCode();

        if (resCode == HttpsURLConnection.HTTP_OK) {
            InputStream inputStream = httpConn.getInputStream();
            result = inputStreamToString(inputStream);
        }

        return result;
    }
}
