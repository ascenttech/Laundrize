package com.tricerionservices.laundrize.async;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.tricerionservices.laundrize.utils.Constants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * Created by ADMIN on 08-07-2015.
 */
public class FetchVerificationCodeAsyncTask extends AsyncTask<String,Void,Boolean> {

    Context context;
    FetchVerificationCodeCallback callback;
    public interface FetchVerificationCodeCallback{

        public void onStart(boolean status);
        public void onResult(boolean result);
    }

    public FetchVerificationCodeAsyncTask(Context context, FetchVerificationCodeCallback callback) {
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

        Log.d(Constants.LOG_TAG, Constants.FetchVerificationCodeAsyncTask);
        Log.d(Constants.LOG_TAG, " The url to be fetched " + url[0]);
        try{

            HttpPost httpPost = new HttpPost(url[0]);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpPost);

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            Log.d(Constants.LOG_TAG," Status code is "+statusCode);
            if(statusCode == 200){

                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);

                Log.d(Constants.LOG_TAG," JSON RESPONSE "+ response);
                JSONObject jsonObject = new JSONObject(response);
                String verify_code = jsonObject.getString("verification_code");

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                prefs.edit().putString("vc", verify_code ).commit();

                return true;
            }
            else if(statusCode == 400){

                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);

                Log.d(Constants.LOG_TAG," JSON RESPONSE "+ response);
                JSONObject jsonObject = new JSONObject(response);
                Constants.verificationCodeError = jsonObject.getString("message");
                return false;
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
        Log.d(Constants.LOG_TAG," Returned Value "+result);
        callback.onResult(result);
    }
}
