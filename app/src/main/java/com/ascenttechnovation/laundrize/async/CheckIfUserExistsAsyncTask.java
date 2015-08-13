package com.ascenttechnovation.laundrize.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ascenttechnovation.laundrize.utils.Constants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * Created by ADMIN on 13-08-2015.
 */
public class CheckIfUserExistsAsyncTask extends AsyncTask<String,Void,Boolean> {

    Context context;
    CheckIfUserExistsCallback callback;
    public interface CheckIfUserExistsCallback{

        public void onStart();
        public void onResult(boolean result);
    }

    public CheckIfUserExistsAsyncTask(Context context, CheckIfUserExistsCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.onStart();
    }

    @Override
    protected Boolean doInBackground(String... url) {

        Log.d(Constants.LOG_TAG,Constants.CheckIfUserExistsAsyncTask);
        Log.d(Constants.LOG_TAG," The url to be fetched "+url[0]);
        try{

            HttpGet httpGet = new HttpGet(url[0]);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpGet);

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            Log.d(Constants.LOG_TAG," status code "+statusCode);
            if (statusCode == 200){

                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);

                Log.d(Constants.LOG_TAG," JSON Response "+response);
                JSONObject jsonObject = new JSONObject(response);
                JSONObject nestedJsonObject = jsonObject.getJSONObject("user_details");
                Constants.userId = nestedJsonObject.getString("id");
                Constants.token = nestedJsonObject.getString("remember_token");

                return true;
            }
            else{

                return false;
            }

        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        Log.d(Constants.LOG_TAG," The value returned "+ result);
        callback.onResult(result);
    }
}
