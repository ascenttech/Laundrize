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
public class FetchAddressRelatedDataAsyncTask extends AsyncTask<String,Void,Boolean> {

    Context context;
    FetchAddressRelatedDataCallback callback;
    String areaName,zipCode,cityName,type;

    public interface FetchAddressRelatedDataCallback{

        public void onStart(boolean status);
        public void onResult(boolean result);
    }

    public FetchAddressRelatedDataAsyncTask(Context context, FetchAddressRelatedDataCallback callback) {
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

        Log.d(Constants.LOG_TAG,Constants.FetchAddressRelatedDataAsyncTask);
        Log.d(Constants.LOG_TAG," The url to  be fetched "+ url[0]);
        try{

            type = url[1];

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
                    if(type.equalsIgnoreCase("city")){

                        cityName = jsonObject.getString("city");
                        Constants.generalAddressRelatedData.add(new GeneralAddressRelatedData(id,cityName));
                    }
                    else if(type.equalsIgnoreCase("area")){

                        areaName = jsonObject.getString("area_name");
                        Constants.generalAddressRelatedData.add(new GeneralAddressRelatedData(id,cityName));

                    }
                    else if(type.equalsIgnoreCase("zipcode")){

                        zipCode = jsonObject.getString("zip_code");
                        Constants.generalAddressRelatedData.add(new GeneralAddressRelatedData(id,cityName));
                    }

                }

                categorizeData();

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

    private void categorizeData() {

        if(type.equalsIgnoreCase("city")){
            Constants.cities = Constants.generalAddressRelatedData;
            Constants.generalAddressRelatedData = new ArrayList<GeneralAddressRelatedData>();

        }
        else if(type.equalsIgnoreCase("area")){

            Constants.areas = Constants.generalAddressRelatedData;
            Constants.generalAddressRelatedData = new ArrayList<GeneralAddressRelatedData>();
        }
        else if(type.equalsIgnoreCase("zipcode")){

            Constants.zipcodes = Constants.generalAddressRelatedData;
            Constants.generalAddressRelatedData = new ArrayList<GeneralAddressRelatedData>();
        }

    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        Log.d(Constants.LOG_TAG," Value Returned "+result);
        callback.onResult(result);
    }
}
