package com.ascenttechnovation.laundrize.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.data.LandingFragmentData;
import com.ascenttechnovation.laundrize.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 02-07-2015.
 */
public class LandingFragmentRecyclerAdapter extends RecyclerView.Adapter<LandingFragmentRecyclerAdapter.ViewHolder>{


    Context context;
    ArrayList<LandingFragmentData> landingFragmentData;
    ImageView backgroundImage;
    LinearLayout titleDescriptionLayout;
    TextView title,description;

    public LandingFragmentRecyclerAdapter(Context context, ArrayList<LandingFragmentData> landingFragmentData) {
        this.context = context;
        this.landingFragmentData = landingFragmentData;

        Log.d(Constants.LOG_TAG,Constants.LandingFragmentRecyclerAdapter);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        public ViewHolder(View itemView) {
            super(itemView);

            view = itemView;

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_landing_fragment, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        findViews(viewHolder);
        setViews(position);

    }

    private void findViews(ViewHolder viewHolder){

        backgroundImage = (ImageView) viewHolder.view.findViewById(R.id.backgroundImage);
        titleDescriptionLayout = (LinearLayout) viewHolder.view.findViewById(R.id.title_description_include_landing_fragment);
        title = (TextView) titleDescriptionLayout.findViewById(R.id.title_text_included);
        description = (TextView) titleDescriptionLayout.findViewById(R.id.description_text_included);

    }

    private void setViews(int position){

        backgroundImage.setImageResource(R.drawable.profile);
        title.setText(landingFragmentData.get(position).getTitle());
        description.setText(landingFragmentData.get(position).getDescription());
    }


    @Override
    public int getItemCount() {
        return 5;
//        return landingFragmentData.size();
    }


}
