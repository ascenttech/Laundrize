package com.tricerionservices.laundrize.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tricerionservices.laundrize.R;
import com.tricerionservices.laundrize.data.NagivationDrawerItemCopy;

import java.util.Collections;
import java.util.List;

/**
 * Created by ADMIN on 06-08-2015.
 */
public class NavigationDrawerAdapterCopy extends RecyclerView.Adapter<NavigationDrawerAdapterCopy.MyViewHolder> {
    List<NagivationDrawerItemCopy> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public NavigationDrawerAdapterCopy(Context context, List<NagivationDrawerItemCopy> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_nav_drawer, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NagivationDrawerItemCopy current = data.get(position);
        holder.title.setText(current.getTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}