package com.tricerionservices.laundrize.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tricerionservices.laundrize.data.ProfileData;
import com.tricerionservices.laundrize.utils.Constants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;

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
        Constants.profileData = new ArrayList<ProfileData>();
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
                JSONObject jsonObject = new JSONObject(response);

                JSONObject nestedJsonObject = jsonObject.getJSONObject("user");

                String id = nestedJsonObject.getString("id");
                String username = nestedJsonObject.getString("username");
                String password = nestedJsonObject.getString("password");
                String firstName = nestedJsonObject.getString("first_name");
                Constants.profileName = firstName;
                String lastName = nestedJsonObject.getString("last_name");
                String email = nestedJsonObject.getString("email");
                String confirmed = nestedJsonObject.getString("confirmed");
                String confirmationCode = nestedJsonObject.getString("confirmation_code");
                String token = nestedJsonObject.getString("remember_token");
                String facebookId = nestedJsonObject.getString("facebook_id");
                String googleId = nestedJsonObject.getString("google_id");
                String status = nestedJsonObject.getString("status");
                String createdAt = nestedJsonObject.getString("created_at");
                String updatedAt = nestedJsonObject.getString("updated_at");

                Constants.profileData.add(new ProfileData(id,username,password,firstName,lastName,email,confirmed,confirmationCode,token,facebookId,googleId,status,createdAt,updatedAt));
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
