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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ADMIN on 08-07-2015.
 */
public class SignInUserAsyncTask extends AsyncTask<String,Void,Boolean> {

    Context context;
    SignInUserCallback callback;
    HttpEntity httpEntity;
    String responseString;
    public interface SignInUserCallback{

        public void onStart(boolean status);
        public void onResult(boolean result);
    }

    public SignInUserAsyncTask(Context context, SignInUserCallback callback) {
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

        Log.d(Constants.LOG_TAG,Constants.SignInUserAsyncTask);
        Log.d(Constants.LOG_TAG," The url to be fetched "+ url[0]);

        try {
            HttpPost httpPost = new HttpPost(url[0]);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpPost);

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            Log.d(Constants.LOG_TAG," status code "+statusCode);
            if(statusCode == 200){

                httpEntity = httpResponse.getEntity();
                responseString = EntityUtils.toString(httpEntity);

                Log.d(Constants.LOG_TAG," JSON RESPONSE " + responseString);
                JSONObject jsonObject = new JSONObject(responseString);
                Constants.userId = jsonObject.getString("userid");
                Constants.token = jsonObject.getString("token");
                Constants.phoneNumber = url[1];
                Constants.password = url[2];
                Log.d(Constants.LOG_TAG," Password is "+Constants.password+" number "+Constants.phoneNumber);

                return true;
            }
            else{
                return false;
            }

        }
        catch (Exception e){

            e.printStackTrace();
            return false;
        }

    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        Log.d(Constants.LOG_TAG," Value Returned "+result);
        callback.onResult(result);
    }
}
