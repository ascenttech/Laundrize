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

import java.util.Iterator;

/**
 * Created by ADMIN on 30-07-2015.
 */
public class FetchAllSlotsAsyncTask extends AsyncTask<String,Void,Boolean> {

    public FetchAllSlotsCallback callback;
    public String allSlots="";
    private boolean firstElement;
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
                    String day = jsonObject.getString("day");
                    Iterator<String> keys = jsonObject.keys();
                    while(keys.hasNext()){

                        String currentKey = keys.next();
                        if(currentKey.equalsIgnoreCase("day")){

                            // We are using this variable because when we want to know which is the first element after
                            // the day so that we can concatenate the others using "_"
                            firstElement = true;
                        }
                        else if(firstElement){

                            allSlots = allSlots.concat(jsonObject.getString(currentKey));
                            firstElement = false;
                        }
                        else{

                            allSlots = allSlots.concat("_");
                            allSlots = allSlots.concat(jsonObject.getString(currentKey));
                        }


                    }

                    Constants.slots.put(day,allSlots);
                    allSlots ="";


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
