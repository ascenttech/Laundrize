package com.tricerionservices.laundrize.async;

import android.os.AsyncTask;
import android.util.Log;

import com.tricerionservices.laundrize.utils.Constants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * Created by ADMIN on 30-07-2015.
 */
public class FetchCurrentServerTimeAsyncTask extends AsyncTask<String,Void,Boolean> {

    public FetchCurrentServerTimeCallBack callback;
    public interface FetchCurrentServerTimeCallBack{

        public void onStart(boolean status);
        public void onResult(boolean result);
    }

    public FetchCurrentServerTimeAsyncTask(FetchCurrentServerTimeCallBack callback) {
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.onStart(true);
    }

    @Override
    protected Boolean doInBackground(String... url) {

        Log.d(Constants.LOG_TAG,Constants.FetchCurrentServerTimeAsyncTask);
        Log.d(Constants.LOG_TAG," Url to be fetched "+url[0]);

        try{

            HttpGet httpGet = new HttpGet(url[0]);
            httpGet.addHeader("Authorization:Bearer", Constants.token);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpGet);

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            Log.d(Constants.LOG_TAG, " status code " + statusCode);
            if(statusCode == 200){

                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);

                Log.d(Constants.LOG_TAG," JSON RESPONSE "+ response);
                JSONObject jsonObject = new JSONObject(response);

                Constants.currentDate = jsonObject.getString("current_date");
                Constants.currentTime = jsonObject.getString("current_time");

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
        Log.d(Constants.LOG_TAG," Value returned "+result);
        if(result){
            Constants.currentServerTimeFetched = true;
        }
        callback.onResult(result);
    }
}
