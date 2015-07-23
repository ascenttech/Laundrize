package com.ascenttechnovation.laundrize.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ascenttechnovation.laundrize.utils.Constants;

/**
 * Created by ADMIN on 22-07-2015.
 */
public class FetchAddressAsyncTask extends AsyncTask<String,Void,Boolean> {

    Context context;
    FetchAddressCallback callback;

    public interface FetchAddressCallback{

        public void onStart(boolean status);
        public void onResult(boolean result);

    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.onStart(true);
    }

    @Override
    protected Boolean doInBackground(String... url) {
        Log.d(Constants.LOG_TAG,Constants.FetchAddressAsyncTask);
        Log.d(Constants.LOG_TAG," Url to be fetched "+url[0]);

        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        callback.onResult(result);
    }
}