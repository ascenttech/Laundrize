package com.ascenttechnovation.laundrize.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ascenttechnovation.laundrize.data.IroningOrderData;
import com.ascenttechnovation.laundrize.utils.Constants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ADMIN on 23-07-2015.
 */
public class PlaceOrderAsyncTask extends AsyncTask<JSONObject,Void,Boolean> {

    Context context;
    PlaceWeeklyOrderCallback callback;
    public interface PlaceWeeklyOrderCallback{

        public void onStart(boolean status);
        public void onResult(boolean result);
    }

    public PlaceOrderAsyncTask(Context context, PlaceWeeklyOrderCallback callback) {
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


        Log.d(Constants.LOG_TAG,Constants.PlaceOrderAsyncTask);

        JSONObject json = jsonObject[0];
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext httpContext = new BasicHttpContext();



//        HttpPost httpPost = new HttpPost(Constants.postOrderUrl+Constants.userId);
        HttpPost httpPost = new HttpPost(Constants.postOrderUrl);
//        HttpPost httpPost = new HttpPost(Constants.postOrderUrl);

        try {




            StringEntity postOrderData = new StringEntity(json.toString());


            httpPost.setEntity(postOrderData);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "x-www-form-urlencoded");
            httpPost.addHeader("Authorization:Bearer",Constants.token);

            HttpResponse response = httpClient.execute(httpPost, httpContext); //execute your request and parse response

            HttpEntity entity = response.getEntity();
            String se = EntityUtils.toString(entity); //if response in JSON format

            Log.d(Constants.LOG_TAG,"Response of our updated data " + se);
            JSONObject object = new JSONObject(se);

            return true;


        } catch (Exception e) {
            e.printStackTrace();
        }


        return false;

    }


    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        Log.d(Constants.LOG_TAG," Value Returned "+result);
        if(result){

            Constants.ironingOrderData.clear();
            Constants.washingOrderData.clear();
            Constants.bagOrderData.clear();

            // needs to be reset both of them
            Constants.totalAmountToBeCollected = 0;
            Constants.totalQuantityToBeCollected = 0;


        }
        callback.onResult(result);
    }
}
