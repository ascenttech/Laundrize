package com.ascenttechnovation.laundrize.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ascenttechnovation.laundrize.data.AddressData;
import com.ascenttechnovation.laundrize.data.CompletedOrdersData;
import com.ascenttechnovation.laundrize.utils.Constants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ADMIN on 27-07-2015.
 */
public class TrackOrdersAsyncTask extends AsyncTask<String,Void,Boolean> {

    Context context;
    TrackOrdersCallback callback;
    public interface TrackOrdersCallback{

        public void onStart(boolean status);
        public void onResult(boolean result);

    }

    public TrackOrdersAsyncTask(Context context, TrackOrdersCallback callback) {
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

        Log.d(Constants.LOG_TAG, Constants.TrackOrdersAsyncTask);
        Log.d(Constants.LOG_TAG," URL to be fetched "+ url[0]);

        return false;
//        try{
//
//            HttpGet httpGet = new HttpGet(url[0]);
//            httpGet.addHeader("Authorization:Bearer",Constants.token);
//            HttpClient httpClient = new DefaultHttpClient();
//            HttpResponse httpResponse = httpClient.execute(httpGet);
//
//            int statusCode = httpResponse.getStatusLine().getStatusCode();
//            Log.d(Constants.LOG_TAG," status code "+ statusCode);
//            if(statusCode == 200){
//
//                HttpEntity httpEntity = httpResponse.getEntity();
//                String response = EntityUtils.toString(httpEntity);
//
//                Log.d(Constants.LOG_TAG," JSON RESPONSE "+ response);
//                JSONArray jsonArray = new JSONArray(response);
//                for(int i = 0;i<jsonArray.length();i++){
//
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    String id = jsonObject.getString("id");
//                    String seviceName = jsonObject.getString("service_name");
//                    String collectionSlot = jsonObject.getString("collection_slot");
//                    String actualCollected = jsonObject.getString("actual_collected");
//                    String actualOpsSubmissionTime = jsonObject.getString("actual_ops_submission_time");
//                    String actualOpsCollectionTime = jsonObject.getString("actual_ops_collection_time");
//                    String price = jsonObject.getString("price");
//                    String actualDelivery = jsonObject.getString("actual_delivery");
//                    String deliverySlot = jsonObject.getString("delivery_slot");
//                    String deliveryDate = jsonObject.getString("user_delivery_date");
////
//                      if(acutalDelivery.equalsIgnoreCase("null")){
//
//                          Constants.completedOrdersData.add(new CompletedOrdersData(id,serviceName,collectionSlot,actualCollected,actualOpsSubmissionTime,actualOpsCollectionTime,price,actualDelivery,deliverySlot,deliveryDate));
//                      }
//                      else if(actualOpsCollectionTime.equalsIgnoreCase("null")){
//
//                          Constants.orderProgress = 3;
//                          Constants.completedOrdersData.add(new CompletedOrdersData(id,serviceName,collectionSlot,actualCollected,actualOpsSubmissionTime,actualOpsCollectionTime,price,actualDelivery,deliverySlot,deliveryDate,Constants.orderProgress));
//                      }
//                      else if(actualOpsSubmissionTime.equalsIgnoreCase("null")){
//                          Constants.orderProgress = 2;
//                          Constants.completedOrdersData.add(new CompletedOrdersData((id,serviceName,collectionSlot,actualCollected,actualOpsSubmissionTime,actualOpsCollectionTime,price,actualDelivery,deliverySlot,deliveryDate,Constants.orderProgress)));
//                      }
//
////
//                }
//
//                return true;
//            }
//            else{
//                return false;
//            }
//        }
//        catch (Exception e){
//
//            e.printStackTrace();
//            return false;
//        }

    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        Log.d(Constants.LOG_TAG," Value Returned "+result);
        if(result){
            Constants.completedOrdersFetched = true;
            Constants.ordersTracked = true;
        }
        callback.onResult(result);
    }
}
