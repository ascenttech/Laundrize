package com.tricerionservices.laundrize.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tricerionservices.laundrize.data.CompletedOrdersData;
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
 * Created by ADMIN on 27-07-2015.
 */
public class FetchCompletedOrdersAsyncTask extends AsyncTask<String,Void,Boolean> {

    Context context;
    FetchCompletedOrdersCallback callback;
    String typeOfService;
    public interface FetchCompletedOrdersCallback{

        public void onStart(boolean status);
        public void onResult(boolean result);

    }

    public FetchCompletedOrdersAsyncTask(Context context, FetchCompletedOrdersCallback callback) {
        this.context = context;
        this.callback = callback;
        if(Constants.completedOrdersData != null ){
            Constants.completedOrdersData.clear();
        }
        else{

            Constants.completedOrdersData = new ArrayList<CompletedOrdersData>();
        }

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.onStart(true);
    }

    @Override
    protected Boolean doInBackground(String... url) {

        Log.d(Constants.LOG_TAG, Constants.FetchCompletedOrdersAsyncTask);
        Log.d(Constants.LOG_TAG," URL to be fetched "+ url[0]);

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
                JSONObject jsonObject= new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("orders");
                for(int i = 0;i<jsonArray.length();i++){

                    JSONObject nestedJsonObject = jsonArray.getJSONObject(i);
                    String serviceId = nestedJsonObject.getString("service_id");
                    String orderId = nestedJsonObject.getString("order_id");
                    String serviceName = nestedJsonObject.getString("service_name");
                    String quantity = nestedJsonObject.getString("quantity");
                    String collectionSlot = nestedJsonObject.getString("collection_slot");
                    String actualCollected = nestedJsonObject.getString("actual_collected");
                    String actualOpsSubmissionTime = nestedJsonObject.getString("actual_ops_submission_time");
                    String actualOpsCollectionTime = nestedJsonObject.getString("actual_ops_collection_time");
                    String price = nestedJsonObject.getString("price");
                    String actualDelivery = nestedJsonObject.getString("actual_delivery");
                    String deliverySlot = nestedJsonObject.getString("delivery_slot");
                    String dateDetails = nestedJsonObject.getString("user_delivery_date");
                    String deliveryDate[] = dateDetails.split("\\s+");

                    switch(serviceId){

                        case "001" : typeOfService = "Ironing";
                            break;
                        case "002" : typeOfService = "Ironing";
                            break;
                        case "003" : typeOfService = "Washing";
                            break;
                        case "004" : typeOfService = "Washing";
                            break;
                        case "005" : typeOfService = "Washing";
                            break;
                        case "006" : typeOfService = "Washing";
                            break;
                        case "007" : typeOfService = "Bags / Shoes";
                            break;
                        case "008" : typeOfService = "Bags / Shoes";
                            break;
                        case "009" : typeOfService = "Others";
                            break;
                    }

                    Constants.completedOrdersData.add(new CompletedOrdersData(serviceId,orderId,serviceName,quantity,collectionSlot,actualCollected,actualOpsSubmissionTime,actualOpsCollectionTime,price,actualDelivery,deliverySlot,deliveryDate[0],typeOfService));


                }

                return true;
            }
            else{
                return false;
            }
        }
        catch (Exception e){

            e.printStackTrace();
            Constants.noCompletedOrders = true;
            return false;
        }

    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        Log.d(Constants.LOG_TAG," Value Returned "+result);
        if(result){
            Constants.completedOrdersFetched = true;
        }
        callback.onResult(result);
    }
}
