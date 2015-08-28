package com.tricerionservices.laundrize.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tricerionservices.laundrize.data.AddressData;
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

/**
 * Created by ADMIN on 22-07-2015.
 */
public class FetchAddressAsyncTask extends AsyncTask<String,Void,Boolean> {

    Context context;
    FetchAddressCallback callback;

    public interface FetchAddressCallback{

        public void onStart(boolean status);
        public void onResult(boolean result);

    }

    public FetchAddressAsyncTask(Context context, FetchAddressCallback callback) {
        this.context = context;
        this.callback = callback;
        Constants.addressData = new ArrayList<AddressData>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.onStart(true);
    }

    @Override
    protected Boolean doInBackground(String... url) {
        Log.d(Constants.LOG_TAG,Constants.FetchAddressAsyncTask);
        Log.d(Constants.LOG_TAG," Url to be fetched "+url[0]);

        try{

            HttpGet httpGet = new HttpGet(url[0]);
            httpGet.addHeader("Authorization:Bearer",Constants.token);
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
                    String address = jsonObject.getString("address");
                    String city = jsonObject.getString("city_name");
                    String zipCode = jsonObject.getString("zip_code");
                    String areaName = jsonObject.getString("area_name");
                    String mobileNumber = jsonObject.getString("mobile");
                    String fullAddress = address+","+areaName+","+city+","+zipCode;

                    Log.d(Constants.LOG_TAG,"FUll address "+ fullAddress);
                    Constants.addressData.add(new AddressData(id,address,city,zipCode,areaName,mobileNumber,fullAddress));
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
        if(result){
            Constants.addressFetched = true;
        }
        callback.onResult(result);
    }
}
