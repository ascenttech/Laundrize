package com.tricerionservices.laundrize.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tricerionservices.laundrize.R;
import com.tricerionservices.laundrize.adapters.NotificationsRecyclerAdapter;
import com.tricerionservices.laundrize.async.FetchNotificationsAsyncTask;
import com.tricerionservices.laundrize.utils.Constants;

/**
 * Created by ADMIN on 25-09-2015.
 */
public class NotificationsFragment extends Fragment {

    private RecyclerView notificationsRecyclerView;
    private RecyclerView.Adapter notificationsAdapter;
    private RecyclerView.LayoutManager notificationsLayoutManager;

    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_notifications,container,false);

        findViews(v);
        settingTheAdapter();
        fetchNotifications();
        return v;
    }

    public void findViews(View v){

        notificationsRecyclerView = (RecyclerView) v.findViewById(R.id.notification_recycler_view_notification_fragment);

    }

    public void settingTheAdapter(){


        notificationsRecyclerView.setHasFixedSize(true);

        notificationsLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        notificationsRecyclerView.setLayoutManager(notificationsLayoutManager);


    }

    public void fetchNotifications(){

        Constants.reintializeTheValues(getActivity().getApplicationContext());
        String finalUrl = Constants.notificationsUrl+Constants.userId;

        new FetchNotificationsAsyncTask(getActivity().getApplicationContext(), new FetchNotificationsAsyncTask.FetchNotificationsCallback() {
            @Override
            public void onStart(boolean status) {

                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle(Constants.APP_NAME);
                progressDialog.setMessage("Getting your notifications... Please wait");
                progressDialog.show();

            }
            @Override
            public void onResult(boolean result) {

                progressDialog.dismiss();
                if(result){

                    notificationsAdapter = new NotificationsRecyclerAdapter(getActivity().getApplicationContext(),Constants.notificationsData);
                    notificationsRecyclerView.setAdapter(notificationsAdapter);

                }
                else{

                    Toast.makeText(getActivity().getApplicationContext(),"Sorry couldn't fetch notifications",5000).show();
                }

            }
        }).execute(finalUrl);


    }

}
