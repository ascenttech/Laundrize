package com.ascenttechnovation.laundrize.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ascenttechnovation.laundrize.utils.Constants;

/**
 * Created by ADMIN on 27-07-2015.
 */
public class FetchUserProfileAsyncTask extends AsyncTask<String,Void,Boolean> {

    Context context;
    FetchUserProfileCallback callback;
    public interface FetchUserProfileCallback {

        public void onStart(boolean status);
        public void onResult(boolean result);

    }

    public FetchUserProfileAsyncTask(Context context, FetchUserProfileCallback callback) {
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

        Log.d(Constants.LOG_TAG, Constants.FetchUserProfileAsyncTask);
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
//                    String address = jsonObject.getString("username");
//                    String city = jsonObject.getString("password");
//                    String zipCode = jsonObject.getString("first_name");
//                    String areaName = jsonObject.getString("last_name");
//                    String mobileNumber = jsonObject.getString("email");
//                    String mobileNumber = jsonObject.getString("confirmed");
//                    String mobileNumber = jsonObject.getString("confirmation_code");
//                    String mobileNumber = jsonObject.getString("remember_token");
//                    String mobileNumber = jsonObject.getString("facebook_id");
//                    String mobileNumber = jsonObject.getString("google_id");
//                    String mobileNumber = jsonObject.getString("status");
//                    String mobileNumber = jsonObject.getString("created_at");
//                    String mobileNumber = jsonObject.getString("updated_at");
//                    String fullAddress = address+","+areaName+","+city+","+zipCode;
//
//                    Log.d(Constants.LOG_TAG,"FUll address "+ fullAddress);
//                    Constants.addressData.add(new AddressData(id,address,city,zipCode,areaName,mobileNumber,fullAddress));
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
        callback.onResult(result);
    }
}
