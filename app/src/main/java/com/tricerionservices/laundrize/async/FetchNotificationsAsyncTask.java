package com.tricerionservices.laundrize.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tricerionservices.laundrize.data.NotificationsData;
import com.tricerionservices.laundrize.utils.Constants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ADMIN on 30-07-2015.
 */
public class FetchNotificationsAsyncTask extends AsyncTask<String,Void,Boolean> {

    public FetchNotificationsCallback callback;
    public String allSlots="";
    private boolean firstElement;
    private String day;

    public interface FetchNotificationsCallback{

        public void onStart(boolean status);
        public void onResult(boolean result);
    }

    public FetchNotificationsAsyncTask(Context context,FetchNotificationsCallback callback) {
        this.callback = callback;
        if(Constants.notificationsData != null){

            Constants.notificationsData.clear();
        }
        else{
            Constants.notificationsData = new ArrayList<NotificationsData>();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.onStart(true);
    }

    @Override
    protected Boolean doInBackground(String... url) {

        Log.d(Constants.LOG_TAG, Constants.FetchNotificationsAsyncTask);
        Log.d(Constants.LOG_TAG, " Url to be fetched " + url[0]);

        try {

            HttpPost httpPost = new HttpPost(url[0]);
            httpPost.addHeader("Authorization:Bearer", Constants.token);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpPost);

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            Log.d(Constants.LOG_TAG, " status code " + statusCode);
            if (statusCode == 200) {

                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);

                Log.d(Constants.LOG_TAG, " JSON RESPONSE " + response);
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray =  jsonObject.getJSONArray("notification");
                for (int i=0;i<jsonArray.length();i++){

                    JSONObject nestedJsonObject = jsonArray.getJSONObject(i);
                    String title = nestedJsonObject.getString("title");
                    String description = nestedJsonObject.getString("desc");
                    String timestamp[] = nestedJsonObject.getString("created_at").split("\\s+");

                    Constants.notificationsData.add(new NotificationsData(title,description,timestamp[0],timestamp[1]));
                }

                return true;
            }
            return false;
        }// end of try

        catch(Exception e){

            e.printStackTrace();
            return false;
        }

    }// end of doInBackground

    @Override
    protected void onPostExecute (Boolean result){
        super.onPostExecute(result);
        Log.d(Constants.LOG_TAG, " Value returned " + result);
        callback.onResult(result);
    }
}