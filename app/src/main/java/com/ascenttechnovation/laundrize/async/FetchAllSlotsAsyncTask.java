package com.ascenttechnovation.laundrize.async;

import android.os.AsyncTask;
import android.util.Log;

import com.ascenttechnovation.laundrize.utils.Constants;

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
public class FetchAllSlotsAsyncTask extends AsyncTask<String,Void,Boolean> {

    public FetchAllSlotsCallback callback;
    public interface FetchAllSlotsCallback{

        public void onStart(boolean status);
        public void onResult(boolean result);
    }

    public FetchAllSlotsAsyncTask(FetchAllSlotsCallback callback) {
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.onStart(true);
    }

    @Override
    protected Boolean doInBackground(String... url) {

        Log.d(Constants.LOG_TAG,Constants.FetchAllSlotsAsyncTask);
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
//                    String slot1 = jsonObject.getString("1")
                    String slot2 = jsonObject.getString("2");
                    String slot3 = jsonObject.getString("3");
                    String slot4 = jsonObject.getString("4");
                    String slot5 = jsonObject.getString("5");
                    String slot6 = jsonObject.getString("6");
                    String slot7 = jsonObject.getString("7");
                    String day = jsonObject.getString("day");

//                    String allSlots = slot1+"_"+slot2+"_"+slot3+"_"+slot4+"_"+slot5+"_"+slot6+"_"+slot7
                    String allSlots = slot2+"_"+slot3+"_"+slot4+"_"+slot5+"_"+slot6+"_"+slot7;
                    Constants.slots.put(day,allSlots);
                    Constants.weekdays.add(day);

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
        Log.d(Constants.LOG_TAG," Value returned "+result);
        callback.onResult(result);
    }
}
