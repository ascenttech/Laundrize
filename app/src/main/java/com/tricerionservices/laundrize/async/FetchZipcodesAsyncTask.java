package com.tricerionservices.laundrize.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tricerionservices.laundrize.utils.Constants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ADMIN on 11-08-2015.
 */
public class FetchZipcodesAsyncTask extends AsyncTask<String,Void,Boolean> {

    Context context;
    FetchZipcodesCallback callback;
    String areaName,zipCode,cityName,type;

    public interface FetchZipcodesCallback{

        public void onStart(boolean status);
        public void onResult(boolean result);
    }

    public FetchZipcodesAsyncTask(Context context, FetchZipcodesCallback callback) {
        this.context = context;
        this.callback = callback;
        Constants.zipcodes = new ArrayList<String>();
        Constants.zipcodesMap = new HashMap<String,String>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.onStart(true);
    }

    @Override
    protected Boolean doInBackground(String... url) {

        Log.d(Constants.LOG_TAG,Constants.FetchZipcodesAsyncTask);
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
                    zipCode = jsonObject.getString("zip_code");

                    Constants.zipcodes.add(zipCode);
                    Constants.zipcodesMap.put(zipCode,id);
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
