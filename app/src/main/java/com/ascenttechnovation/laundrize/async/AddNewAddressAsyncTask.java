package com.ascenttechnovation.laundrize.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ascenttechnovation.laundrize.utils.Constants;

/**
 * Created by ADMIN on 25-07-2015.
 */
public class AddNewAddressAsyncTask extends AsyncTask<String,Void,Boolean> {

    Context context;
    private AddNewAddressCallback callback;
    public interface AddNewAddressCallback{

        public void onStart(boolean status);
        public void onResult(boolean result);

    }

    public AddNewAddressAsyncTask(Context context, AddNewAddressCallback callback) {
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

        Log.d(Constants.LOG_TAG,Constants.AddNewAddressAsyncTask);
        Log.d(Constants.LOG_TAG,"URL to be fetched "+url[0]);

        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        callback.onResult(result);
    }
}
