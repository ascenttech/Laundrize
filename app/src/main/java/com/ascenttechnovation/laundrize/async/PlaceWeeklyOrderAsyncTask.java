package com.ascenttechnovation.laundrize.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ascenttechnovation.laundrize.utils.Constants;

import org.json.JSONObject;

/**
 * Created by ADMIN on 23-07-2015.
 */
public class PlaceWeeklyOrderAsyncTask extends AsyncTask<JSONObject,Void,Boolean> {

    Context context;
    PlaceWeeklyOrderCallback callback;
    public interface PlaceWeeklyOrderCallback{

        public void onStart(boolean status);
        public void onResult(boolean result);
    }

    public PlaceWeeklyOrderAsyncTask(Context context, PlaceWeeklyOrderCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.onStart(true);
    }

    @Override
    protected Boolean doInBackground(JSONObject... jsonObject) {


        Log.d(Constants.LOG_TAG,Constants.PlaceWeeklyOrderAsyncTask);
        Log.d(Constants.LOG_TAG," JSON to be posted "+ jsonObject);




        return false;

    }


    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        Log.d(Constants.LOG_TAG," Value Returned "+result);
        callback.onResult(result);
    }
}
