package com.ascenttechnovation.laundrize.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.data.GeneralData;
import com.ascenttechnovation.laundrize.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 13-07-2015.
 */
public class DataHolderFragmentRecyclerAdapter extends RecyclerView.Adapter<DataHolderFragmentRecyclerAdapter.ViewHolder> {

    Context context;
    private ArrayList<GeneralData> data;
    ImageView backgroundImage;
    private TextView title,description,quantity,price;
    private ImageView add,subtract;
    private LinearLayout footer;

    public DataHolderFragmentRecyclerAdapter(Context context) {
        this.context = context;
    }

    public DataHolderFragmentRecyclerAdapter(Context context, ArrayList<GeneralData> data) {
        this.context = context;
        this.data = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View v;
        public ViewHolder(View view) {
            super(view);

            v = view;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_data_holder, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        findViews(holder);
        setViews(position);

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public void findViews(ViewHolder holder){

        backgroundImage = (ImageView) holder.v.findViewById(R.id.background_image_row_order);
        footer = (LinearLayout) holder.v.findViewById(R.id.card_footer_included_data_holder_fragment);
        add = (ImageView) footer.findViewById(R.id.add_image_included);
        subtract = (ImageView) footer.findViewById(R.id.subtract_image_included);
        price = (TextView) footer.findViewById(R.id.price_text_included);
        quantity = (TextView) footer.findViewById(R.id.quantity_text_included);


    }

    public void setViews(int position){


        add.setTag("add_"+position);
        add.setOnClickListener(listener);

        subtract.setTag("subtract_"+position);
        subtract.setOnClickListener(listener);

        backgroundImage.setTag("background_"+position);
        backgroundImage.setOnClickListener(listener);

    }

    public Bitmap getCircleBitmap(Bitmap bitmap){


        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;

    }

    private void addQuantity(String position) {

        Toast.makeText(context," Position is "+position,5000).show();
        int pos = Integer.parseInt(position);
        quantity.setText(Integer.parseInt(quantity.getText().toString())+1);
    }

    private void subtractQuantity(String position) {

        Toast.makeText(context," Position is "+position,5000).show();
        int pos = Integer.parseInt(position);
        if(Integer.parseInt(quantity.getText().toString())!=0){
            quantity.setText(Integer.parseInt(quantity.getText().toString())-1);
        }

    }
//    private void changeBackground(String position) {
//
//        int position = Integer.parseInt(position);
//        backgroundImage.setImageResource(R.);
//    }


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {



            String tag = view.getTag().toString();
            String position[] = tag.split("_");
            Log.d(Constants.LOG_TAG," tag "+ position[0]+" second element "+ position[1]);
            switch (position[0]){


                case "add": addQuantity(position[1]);
                    break;
                case "subtract":subtractQuantity(position[1]);
                    break;
//                case "background_": changeBackground(position[1]);
//                    break;

            }

        }
    };



}
