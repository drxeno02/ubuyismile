package com.app.amazon.framework;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.app.amazon.framework.interfaces.OnAWSRequestListener;
import com.app.framework.utilities.FrameworkUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by leonard on 10/20/2017.
 */

public class AmazonRequestManager extends AsyncTask<String, String, String> {

    private static OnAWSRequestListener mAWSRequestListener;

    /**
     * Method is used to set callback for when AWS request is made and response is received
     *
     * @param listener Callback for when AWS request is made and response is received
     */
    public static void onAWSRequestListener(OnAWSRequestListener listener) {
        mAWSRequestListener = listener;
    }

    @NonNull
    @Override
    protected String doInBackground(String... urls) {
        String resResult = "";
        if (!FrameworkUtils.checkIfNull(urls)) {
            try {
                URL url = new URL(urls[0]);
                BufferedReader buffer = new BufferedReader(new InputStreamReader(url.openStream()));
                resResult = buffer.readLine();
                buffer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resResult;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if (!FrameworkUtils.checkIfNull(mAWSRequestListener)) {
            mAWSRequestListener.onAWSSuccess(result);
        }
    }

}
