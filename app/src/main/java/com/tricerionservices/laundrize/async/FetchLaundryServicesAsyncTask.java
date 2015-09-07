package com.tricerionservices.laundrize.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tricerionservices.laundrize.data.GeneralData;
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
                    String id = jsonObject.getString("id");


//                    String id = jsonObject.getString("name");
//                    String id = jsonObject.getString("des_short");
//                    String id = jsonObject.getString("des_long");
//                    String id = jsonObject.getString("imgsmall");
//                    String id = jsonObject.getString("imglarge");

                    JSONArray nestedJsonArray = jsonObject.getJSONArray("subitems");
                    for(int j=0;j<nestedJsonArray.length();j++){

                        JSONObject nestedJsonObject = nestedJsonArray.getJSONObject(j);
                        String code = nestedJsonObject.getString("code");
                        String title = nestedJsonObject.getString("name");
                        title = title.replace("&amp;","&");
                        String description = nestedJsonObject.getString("desc");
                        description = description.replace("&amp;","&");
                        String largeImage = nestedJsonObject.getString("imglarge");
                        String smallImage = nestedJsonObject.getString("imgsmall");
                        String regular = nestedJsonObject.getString("regular");
                        String regularCost = nestedJsonObject.getString("regularcost");
                        String extraCare = nestedJsonObject.getString("extracare");
                        String extraCareCost = nestedJsonObject.getString("extracarecost");

                        Constants.subCategory.add(new GeneralData(code,title,description,smallImage,largeImage,regular,regularCost,extraCare,extraCareCost,"0"));
//                     Constants.subCategory.add(new GeneralData(code,title,description,smallImage,largeImage,regular,regularCost,extraCare,extraCareCost));
                        Constants.servicesName.put(code,title);
                    } // end of nested for loop

                    sortTheCategories(id);


                }// end of the for loop

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

    public void sortTheCategories(String id) {

        switch(id){

            case "001000000":
                Constants.ironingWearablesData = Constants.subCategory;
                Constants.subCategory = new ArrayList<GeneralData>();
                break;
            case "002000000":
                Constants.ironingHouseholdsData = Constants.subCategory;
                Constants.subCategory = new ArrayList<GeneralData>();
                break;
            case "003000000":
                Constants.washAndIronWearablesData = Constants.subCategory;
                Constants.subCategory = new ArrayList<GeneralData>();
                break;
            case "004000000":
                Constants.washAndIronHouseholdsData = Constants.subCategory;
                Constants.subCategory = new ArrayList<GeneralData>();
                break;
            case "005000000":
                Constants.dryCleanWearablesData = Constants.subCategory;
                Constants.subCategory = new ArrayList<GeneralData>();
                break;
            case "006000000":
                Constants.dryCleanHouseholdsData = Constants.subCategory;
                Constants.subCategory = new ArrayList<GeneralData>();
                break;
            case "007000000":
                Constants.shoeLaundryData = Constants.subCategory;
                Constants.subCategory = new ArrayList<GeneralData>();
                break;
            case "008000000":
                Constants.bagLaundryData = Constants.subCategory;
                Constants.subCategory = new ArrayList<GeneralData>();
                break;
            case "009000000":
                Constants.othersData = Constants.subCategory;
                Constants.subCategory = new ArrayList<GeneralData>();
                break;

        }

    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        Log.d(Constants.LOG_TAG," Value Returned "+result);
        callback.onResult(result);
    }
}
