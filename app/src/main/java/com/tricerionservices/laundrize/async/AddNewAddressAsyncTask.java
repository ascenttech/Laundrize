package com.tricerionservices.laundrize.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tricerionservices.laundrize.utils.Constants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * Created by ADMIN on 25-07-2015.
 */
public class AddNewAddressAsyncTask extends AsyncTask<String,Void,Boolean> {

    Context context;
    private AddNewAddressCallback callback;
    public interface AddNewAddressCallback{

        public void onStart(boolean status);
        public void onResult(boolean result);

    }

    public AddNewAddressAsyncTask(Context context, AddNewAddressCallback callback) {
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

        Log.d(Constants.LOG_TAG,Constants.AddNewAddressAsyncTask);
        Log.d(Constants.LOG_TAG,"URL to be fetched "+url[0]);
        try{

            HttpPost httpPost = new HttpPost(url[0]);
            httpPost.addHeader("Authorization:Bearer",Constants.token);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpPost);

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            Log.d(Constants.LOG_TAG," status code "+statusCode);
            if (statusCode == 200){

                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);

                Log.d(Constants.LOG_TAG," JSON Response "+ response);
                JSONObject jsonObject = new JSONObject(response);
                Constants.addressId = jsonObject.getString("addr_id");
                return true;
            }
            else{

                return false;
            }

        }
        catch(Exception e){
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
