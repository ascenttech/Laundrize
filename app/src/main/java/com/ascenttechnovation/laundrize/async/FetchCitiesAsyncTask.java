package com.ascenttechnovation.laundrize.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ascenttechnovation.laundrize.data.GeneralAddressRelatedData;
import com.ascenttechnovation.laundrize.utils.Constants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ADMIN on 11-08-2015.
 */
public class FetchCitiesAsyncTask extends AsyncTask<String,Void,Boolean> {

    Context context;
    FetchCitiesCallback callback;
    String areaName,zipCode,cityName,type;

    public interface FetchCitiesCallback{

        public void onStart(boolean status);
        public void onResult(boolean result);
    }

    public FetchCitiesAsyncTask(Context context, FetchCitiesCallback callback) {
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

        Log.d(Constants.LOG_TAG,Constants.FetchCitiesAsyncTask);
        Log.d(Constants.LOG_TAG," The url to  be fetched "+ url[0]);
        try{

            HttpGet httpGet = new HttpGet(url[0]);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpGet);

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            Log.d(Constants.LOG_TAG," status code "+ statusCode);
            if(statusCode == 200){

                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);

                Log.d(Constants.LOG_TAG," JSON RESPONSE "+ response);
                JSONArray jsonArray = new JSONArray(response);
                for(int i = 0;i<jsonArray.length();i++){

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String id = jsonObject.getString("id");
                    cityName = jsonObject.getString("city");

                }

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
