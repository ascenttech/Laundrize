package com.tricerionservices.laundrize.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tricerionservices.laundrize.R;
import com.tricerionservices.laundrize.custom.CustomTextView;
import com.tricerionservices.laundrize.data.NotificationsData;
import com.tricerionservices.laundrize.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 25-09-2015.
 */
public class NotificationsRecyclerAdapter extends RecyclerView.Adapter<NotificationsRecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<NotificationsData> notificationsData;
    CustomTextView notificationsTitle,notificationsDesctiption,notificationsTime,notificationsDate;

    public NotificationsRecyclerAdapter(Context context, ArrayList<NotificationsData> notificationsData) {
        this.context = context;
        this.notificationsData = notificationsData;
        Log.d(Constants.LOG_TAG,Constants.NotificationsRecyclerAdapter);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View v;
        public ViewHolder(View itemView) {
            super(itemView);
            v = itemView;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.row_notifications_fragment,parent,false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        findViews(holder);
        setViews(position);

    }

    public void findViews(ViewHolder holder){

        notificationsTitle = (CustomTextView) holder.v.findViewById(R.id.notifications_title_text_notifications_fragment);
        notificationsDesctiption = (CustomTextView) holder.v.findViewById(R.id.notifications_description_text_notifications_fragment);
        notificationsTime = (CustomTextView) holder.v.findViewById(R.id.time_included);
        notificationsDate = (CustomTextView) holder.v.findViewById(R.id.date_included);

    }

    public void setViews(int position){

        notificationsTitle.setText(notificationsData.get(position).getTitle());
        notificationsDesctiption.setText(notificationsData.get(position).getDescription());
        notificationsTime.setText(notificationsData.get(position).getTime());
        notificationsDate.setText(notificationsData.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return notificationsData.size();
    }


}
