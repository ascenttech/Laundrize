package com.ascenttechnovation.laundrize.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ascenttechnovation.laundrize.utils.Constants;

/**
 * Created by ADMIN on 27-07-2015.
 */
public class TrackOrdersAsyncTask extends AsyncTask<String,Void,Boolean> {

    Context context;
    TrackOrdersCallback callback;
    public interface TrackOrdersCallback{

        public void onStart(boolean status);
        public void onResult(boolean result);

    }

    public TrackOrdersAsyncTask(Context context, TrackOrdersCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.onStart(true);
    }

    @Override
    protected Boolean doInBackground(String... url) {

        Log.d(Constants.LOG_TAG, Constants.TrackOrdersAsyncTask);
        Log.d(Constants.LOG_TAG," URL to be fetched "+ url[0]);









        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        Log.d(Constants.LOG_TAG," Value Returned "+result);
        callback.onResult(result);
    }
}
