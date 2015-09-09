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
    private String day;

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

        Log.d(Constants.LOG_TAG, Constants.FetchAllSlotsAsyncTask);
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
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Iterator<String> keys = jsonObject.keys();
                    while (keys.hasNext()) {

                        String currentKey = keys.next();
                        if(!currentKey.equalsIgnoreCase("day")){

                            findLowestKey(currentKey);
                        }
                        if (currentKey.equalsIgnoreCase("day")) {
                            // We are using this variable because when we want to know which is the first element after
                            // the day so that we can concatenate the others using "_"
                            day = jsonObject.getString("day");
                        } else {

                            if (allSlots.isEmpty()) {

                                allSlots = jsonObject.getString(currentKey);

                            } else {


                                allSlots = allSlots.concat("_");
                                allSlots = allSlots.concat(jsonObject.getString(currentKey));
                            }

                        }

                    }
                    allSlots = sortAllSlots();
                    Constants.slots.put(day, allSlots);
                    allSlots = "";
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

    private void findLowestKey(String currentKey) {

        int key = Integer.parseInt(currentKey);
        if(key < Constants.jCounter){

            Constants.jCounter = 2;
        }


    }

    // This method is used to sort the slots values from allSlots
    private String sortAllSlots() {

        String sortedSlots = new String();
        String slots[] = allSlots.split("_");
        // NOW we have the values as slots[0] = "10:00AM - 12:00PM"
        //  slots[1] = "08:00AM - 10:00PM"
        //  slots[2] = "14:00PM - 16:00PM"

        for(int i=0;i<slots.length;i++){

            int pivot = Integer.parseInt(slots[i].substring(0,2));
            for(int j = i+1;j<slots.length;j++){

                int checkElement = Integer.parseInt(slots[j].substring(0, 2));
                if(pivot > checkElement){

                    String temp = slots[i];
                    slots[i] = slots[j];
                    slots[j] = temp;
                }
            }

        }

        for(int i =0;i<slots.length;i++){

            if(i==0){
                sortedSlots = slots[i];
            }
            else{
                sortedSlots = sortedSlots.concat("_");
                sortedSlots = sortedSlots.concat(slots[i]);
            }

        }

        return sortedSlots;


    }

    @Override
    protected void onPostExecute (Boolean result){
        super.onPostExecute(result);
        Log.d(Constants.LOG_TAG, " Value returned " + result);
        callback.onResult(result);
    }
}