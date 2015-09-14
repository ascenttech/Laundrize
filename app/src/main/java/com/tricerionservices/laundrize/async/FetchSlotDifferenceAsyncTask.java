package com.tricerionservices.laundrize.async;

import android.os.AsyncTask;
import android.util.Log;

import com.tricerionservices.laundrize.utils.Constants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ADMIN on 30-07-2015.
 */
public class FetchSlotDifferenceAsyncTask extends AsyncTask<String,Void,Boolean> {

    public FetchSlotDifferenceCallback callback;
    public interface FetchSlotDifferenceCallback{

        public void onStart(boolean status);
        public void onResult(boolean result);
    }

    public FetchSlotDifferenceAsyncTask(FetchSlotDifferenceCallback callback) {
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.onStart(true);
    }

    @Override
    protected Boolean doInBackground(String... url) {

        Log.d(Constants.LOG_TAG,Constants.FetchSlotDifferenceAsyncTask);
        Log.d(Constants.LOG_TAG," Url to be fetched "+url[0]);

        try{

            HttpPost httpPost = new HttpPost(url[0]);
            httpPost.addHeader("Authorization:Bearer", Constants.token);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpPost);

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            Log.d(Constants.LOG_TAG, " status code " + statusCode);
            if(statusCode == 200){

                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);

                Log.d(Constants.LOG_TAG," JSON RESPONSE "+ response);
                JSONArray jsonArray = new JSONArray(response);
                for (int i=0;i<jsonArray.length();i++){

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String serviceCode = jsonObject.getString("service_code");
                    String slots = jsonObject.getString("slots");
                    sortTheSlots(serviceCode,slots);
                    Constants.slots.put(serviceCode,slots);

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

    private void sortTheSlots(String serviceCode,String slots) {

        char differenceCode = serviceCode.charAt(2);
        switch (differenceCode){

            case '1': Constants.ironingDeliveryCounter = Integer.parseInt(slots)+1;
                Log.d(Constants.LOG_TAG," The ironing delivery counter "+Constants.ironingDeliveryCounter);
                break;
            case '3': Constants.washingDeliveryCounter = Integer.parseInt(slots)+1;
                Log.d(Constants.LOG_TAG," The washing delivery counter "+Constants.washingDeliveryCounter);
                break;
            case '7': Constants.bagsDeliveryCounter = Integer.parseInt(slots)+1;
                Log.d(Constants.LOG_TAG," The bags delivery counter "+Constants.bagsDeliveryCounter);
                break;
            case '9': Constants.othersDeliveryCounter = Integer.parseInt(slots)+1;
                Log.d(Constants.LOG_TAG," The others delivery counter "+Constants.othersDeliveryCounter);
                break;
        }


    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        Log.d(Constants.LOG_TAG," Value returned "+result);
        if(result){
            Constants.slotDifferenceFetched = true;
        }
        callback.onResult(result);
    }
}
