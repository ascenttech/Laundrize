package com.ascenttechnovation.laundrize.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ascenttechnovation.laundrize.data.AddressData;
import com.ascenttechnovation.laundrize.data.LaundryServicesSubCategoryData;
import com.ascenttechnovation.laundrize.utils.Constants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by ADMIN on 27-07-2015.
 */
public class FetchLaundryServicesAsyncTask extends AsyncTask<String,Void,Boolean> {

    Context context;
    FetchLaundryServicesCallback callback;
    public interface FetchLaundryServicesCallback{

        public void onStart(boolean status);
        public void onResult(boolean result);

    }

    public FetchLaundryServicesAsyncTask(Context context, FetchLaundryServicesCallback callback) {
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

        Log.d(Constants.LOG_TAG,Constants.FetchLaundryServicesAsyncTask);
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
                JSONArray jsonArray = new JSONArray(response);
                for(int i = 0;i<jsonArray.length();i++){

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    String id = jsonObject.getString("id");
//                    String id = jsonObject.getString("name");
//                    String id = jsonObject.getString("des_short");
//                    String id = jsonObject.getString("des_long");
//                    String id = jsonObject.getString("imgsmall");
//                    String id = jsonObject.getString("imglarge");

                    JSONArray nestedJsonArray = jsonObject.getJSONArray("subitems");
                    for(int j=0;j<nestedJsonArray.length();i++){

                    JSONObject nestedJsonObject = nestedJsonArray.getJSONObject(i);
                     String code = nestedJsonObject.getString("code");
                     String description = nestedJsonObject.getString("desc");
                     String title = nestedJsonObject.getString("name");
                     String largeImage = nestedJsonObject.getString("imglarge");
                     String smallImage = nestedJsonObject.getString("imgsmall");
                     String regular = nestedJsonObject.getString("regular");
                     String regularCost = nestedJsonObject.getString("regularcost");
                     String extraCare = nestedJsonObject.getString("extracare");
                     String extraCareCost = nestedJsonObject.getString("extracarecost");

                     Constants.laundryServicesSubCategory.add(new LaundryServicesSubCategoryData(code,title,description,smallImage,largeImage,regular,regularCost,extraCare,extraCareCost));

                    }

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
